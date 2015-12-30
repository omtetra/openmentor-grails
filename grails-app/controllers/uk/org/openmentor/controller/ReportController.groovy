package uk.org.openmentor.controller

import java.util.Map;

import grails.plugin.springsecurity.annotation.Secured

import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary;

@Secured(['ROLE_OPENMENTOR-USER'])
class ReportController {
	
	def summarizationService
	def courseInfoService
	def categorizationInfoService

	private Map getUploadModel() {
		
		def courseId = session.current_course
		def courseInstance = courseInfoService.findCourse(courseId)

		if (! courseInstance) {
			redirect(action: "select", controller: "course")
			return
		}

		def model = [
			grades: categorizationInfoService.getGrades(),
			bands: categorizationInfoService.getBands(),
			bandLabels: categorizationInfoService.getBandLabels(),
			course: courseInstance
		]
		
		return model
	}

    def index() { 
		def model = getUploadModel()
		if (! model) return
		
		model
	}

    def course() { 
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseSummary(model.course.courseId)
		model.summary = summary
		
		return model
	}

    def course_details() { 
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseSummary(model.course.courseId, true)
		model.summary = summary
		
		return model
	}

    def assignments() { 
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseSummaryByAssignment(model.course.courseId)
		model.summary = summary

		model
	}
	
	def assignment() {
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndAssignmentSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

    def assignment_details() { 
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndAssignmentSummary(model.course.courseId, params.id, true)
		model.summary = summary
		
		return model
	}

    def students() { 
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseSummaryByStudent(model.course.courseId)
		model.summary = summary

		model
	}

    def student() { 
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndStudentSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

    def student_details() { 
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndStudentSummary(model.course.courseId, params.id, true)
		model.summary = summary
		
		return model
	}

	def tutors() { 
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseSummaryByTutor(model.course.courseId)
		model.summary = summary

		model
	}

    def tutor() { 
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndTutorSummary(model.course.courseId, params.id)
		model.summary = summary

		model
	}

    def tutor_details() { 
		assert params.id != null
		def model = getUploadModel()
		if (! model) return
		
		Summary summary = summarizationService.getCourseAndTutorSummary(model.course.courseId, params.id, true)
		model.summary = summary
		
		return model
	}

}
