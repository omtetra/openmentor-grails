package uk.org.openmentor.controller

import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Assignment;
import uk.org.openmentor.data.Submission;

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.multipart.MultipartFile;

class AssignmentController {
	
	def analyzerService
	
    def index = { }
	
	private Map getUploadModel() {
		return [
			grades: ConfigurationHolder.config.openmentor.grades,
			course: Course.findByCourseId(session.current_course)
		]
	}

    def upload = { 
		log.info(Course.findByCourseId(session.current_course).tutors)
		getUploadModel()
	}
	
	def show = {
		log.error(Submission.findAll())
	}
	
	def save = { SubmissionCommand sc ->
		log.error("Ready to save and start processing")
		log.error(sc)
		
		// First of all, we may have the same submission already. This is not hugely
		// well-specified in the previous implementation.
		
		Assignment assignment = Assignment.get(sc.assignmentId)
		Submission sub = analyzerService.newSubmission(
			assignment,
			[sc.studentIds] as Set<String>,
			[sc.tutorIds] as Set<String>,
			sc.grade,
			sc.dataFile.getOriginalFilename(),
			sc.dataFile.getBytes()
		)
		
		def model = getUploadModel()
		model.submission = sub

		if (sub.validate() && sub.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'submission.label', default: 'Submission')])}"
			render(view: "show", model: model)
		} else {
			sub.errors.each {
				log.error(it)
			}
			render(view: "upload", model: model)
		}
	}
	
	def query = {
		def assignmentList = Assignment.findAllByCodeIlike("%" + params.term + "%")
		assignmentList.sort { it.code }
		
		render(contentType: "text/json") {
			assignmentList.collect { [code: it.code] };
		}
	}
}

final class SubmissionCommand {
	String courseId
	Integer assignmentId
	String grade
	String studentIds
	String tutorIds
	MultipartFile dataFile
	
	static constraints = {}
}