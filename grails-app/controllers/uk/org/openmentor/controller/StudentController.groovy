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

	def show = {
		
		log.error(request.g)
		
		def studentInstance = Student.get(params.id)
        if (!studentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studentInstance: studentInstance]
        }
	}
}
