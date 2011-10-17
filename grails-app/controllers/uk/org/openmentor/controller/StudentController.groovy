package uk.org.openmentor.controller

import uk.org.openmentor.courseinfo.Student;

class StudentController {

    def index = { 
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'id'
		params.order = params.order ?: 'desc'
		params.offset = params.offset ?: '0'
				
		def criteria = Student.createCriteria()
		
		def studentList = criteria.list {
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
		
		def studentCount = Student.count()
		
		[studentInstanceList: studentList, studentInstanceTotal: studentCount]
	}
}
