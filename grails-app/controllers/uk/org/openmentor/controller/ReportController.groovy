package uk.org.openmentor.controller

import java.util.Map;

import grails.plugins.springsecurity.Secured

import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Grade;
import uk.org.openmentor.domain.Summary;

@Secured(['ROLE_OPENMENTOR-USER'])
class ReportController {
	
	def summarizationService

	private Map getUploadModel() {
		
		def model = [
			grades: Grade.getGrades(),
			course: Course.findByCourseId(session.current_course)
		]
		
		return model
	}

    def index = { 
		def model = getUploadModel()
		
		model
	}

    def course = { 
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseSummary(model.course.courseId)
		model.summary = summary
		
		Summary assignmentSummary = summarizationService.getCourseSummaryByAssignment(model.course.courseId)
		model.assignmentSummary = assignmentSummary

		return model
	}

    def assignment = { 
		def model = getUploadModel()
		
		model
	}

    def student = { 
		def model = getUploadModel()
		
		Summary studentSummary = summarizationService.getCourseSummaryByStudent(model.course.courseId)
		model.studentSummary = studentSummary

		model
	}

	def tutor = { 
		def model = getUploadModel()
		
		Summary tutorSummary = summarizationService.getCourseSummaryByTutor(model.course.courseId)
		model.tutorSummary = tutorSummary

		model
	}
}
