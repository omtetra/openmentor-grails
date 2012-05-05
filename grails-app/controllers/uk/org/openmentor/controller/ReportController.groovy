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
		
		return model
	}

    def assignments = { 
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseSummaryByAssignment(model.course.courseId)
		model.summary = summary

		model
	}
	
	def assignment = {
		assert params.id != null
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseAndAssignmentSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

    def students = { 
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseSummaryByStudent(model.course.courseId)
		model.summary = summary

		model
	}

    def student = { 
		assert params.id != null
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseAndStudentSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

	def tutors = { 
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseSummaryByTutor(model.course.courseId)
		model.summary = summary

		model
	}

    def tutor = { 
		assert params.id != null
		def model = getUploadModel()
		
		Summary summary = summarizationService.getCourseAndTutorSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

}
