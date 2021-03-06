package uk.org.openmentor.controller

import grails.plugin.springsecurity.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary;

@Secured(['ROLE_OPENMENTOR-USER'])
class SubmissionController {

	def analyzerService
	def currentUserService
	def summarizationService
	def courseInfoService
	def categorizationInfoService
	
	private Course getSelectedCourse() {
		def courseId = session.current_course
		def courseInstance = courseInfoService.findCourse(courseId)

		if (courseInstance == null) {
			redirect(action: "select", controller: "course")
			return
		}
		
		return courseInstance
	}
	
    def index() { 
		redirect(action: "new", params: params)
	}

	def upload() { 
		def courseInstance = getSelectedCourse()
		if (! courseInstance) return
		def grades = categorizationInfoService.getGrades()
		def assignmentsList = courseInfoService.getAssignments(courseInstance, [:])
		[grades: grades, courseInstance: courseInstance, assignmentsList: assignmentsList]
	}
	
	/**
	 * The show action for the submission controller. This generates a Categorization
	 * of the comments, and then uses that to build a Summary. The Submission, along
	 * with the Summary, is passed into the view. The SummarizationService is used for
	 * the underlying work. 
	 */
	def show() {
		Submission sub = Submission.get(params.id)
		Summary summary = summarizationService.getSubmissionSummary(sub, true)
		return [summary: summary, submissionInstance: sub]
	}
	
	def save(SubmissionCommand cmd) {
		def courseInstance = getSelectedCourse()
		if (! courseInstance) {
			return
		}
				
		def grades = categorizationInfoService.getGrades()
		def model = [grades: grades, courseInstance: courseInstance]
		model.cmd = cmd
		
		if (cmd.hasErrors()) {
			render(view: "upload", model: model)
			return
		}
			
		log.debug("Ready to save and start processing")
		log.debug(cmd)
		
		// First of all, we may have the same submission already. This is not hugely
		// well-specified in the previous implementation.
		
		Assignment assignment = Assignment.get(cmd.assignmentId)
		String userName = currentUserService.currentUserName()
		Submission sub = analyzerService.newSubmission(
			assignment,
			[cmd.studentIds] as Set<String>,
			[cmd.tutorIds] as Set<String>,
			cmd.grade,
			userName,
			cmd.dataFile.getOriginalFilename(),
			cmd.dataFile.getBytes()
		)
		model.submission = sub
		
		if (sub.validate() && sub.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'submission.label', default: 'Submission')])}"
			redirect(action: "show", id: sub.id)
		} else {
			sub.errors.each {
				log.error(it)
			}
			render(view: "upload", model: model)
		}
	}
	
}

@grails.validation.Validateable
class SubmissionCommand {
	String courseId
	Integer assignmentId
	String grade
	String studentIds
	String tutorIds
	MultipartFile dataFile
	
	static constraints = {
		dataFile validator: { val, obj ->
			return (val.isEmpty()) ? ['empty.file'] : true
		}
		
		assignmentId(nullable: false)
		courseId(nullable: false)
		grade(nullable: false, blank: false)
		studentIds(nullable: false, blank: false)
		tutorIds(nullable: false, blank: false)
	}
}