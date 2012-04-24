package uk.org.openmentor.controller

import java.util.Map;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import grails.plugins.springsecurity.Secured

import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.DataBook;
import uk.org.openmentor.domain.Grade;

@Secured(['ROLE_OPENMENTOR-USER'])
class ReportController {
	
	def assessmentService

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
		
		Set<Submission> submissions = Submission.findAllCourseSubmissions(model.course.courseId)
		DataBook book = assessmentService.buildDataBook(submissions)
		model.book = book
		
		return model
	}

    def assignment = { 
		def model = getUploadModel()
		
		model
	}

    def student = { 
		def model = getUploadModel()
		
		model
	}

	def tutor = { 
		def model = getUploadModel()
		
		model
	}
}
