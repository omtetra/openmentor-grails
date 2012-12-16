package uk.org.openmentor.controller

import org.springframework.security.access.annotation.Secured;

import uk.org.openmentor.courseinfo.Student;

@Secured(['ROLE_OPENMENTOR-ADMIN'])
class StudentController {

    def index = { 
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'id'
		params.order = params.order ?: 'asc'
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

	def save = {
		def studentInstance = new Student(params)
		
		if (studentInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'student.label', default: 'Student'), studentInstance.id])}"
			redirect(action: "list", id: studentInstance.id)
		}
		else {
			log.info("Failed to create new sample: returning to dialog")
			render(view: "create", model: [studentInstance: studentInstance])
		}
	}
	
	def show = {
		def studentInstance = Student.findById(params.id)
        if (!studentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studentInstance: studentInstance]
        }
	}
	
	def edit = {
		def studentInstance = Student.findById(params.id)
		if (!studentInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.id])}"
			redirect(action: "list")
		}
		else {
			[studentInstance: studentInstance]
		}
	}
	
	def create = { }
	
	def update = {
		Student.withSession { session ->
	        def studentInstance = Student.findById(params.id)
			
			if (studentInstance) {
				log.info("Updating student: id: " + studentInstance.id)
	            if (params.version) {
	                def version = params.version.toLong()
	                if (studentInstance.version > version) {
	                    
	                    studentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'student.label', default: 'Student')] as Object[], "Another user has updated this student while you were editing")
	                    render(view: "edit", model: [studentInstance: studentInstance])
	                    return
	                }
	            }
				
				// When handling an update, we need to deal with the issues for the
				// associated courses and other components. For that reason, we block
				// changes to the identifier.
				
				studentInstance.properties = params
				studentInstance.save()
				
	            if (!studentInstance.hasErrors() && studentInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'student.label', default: 'Student'), studentInstance.id])}"
	                redirect(action: "list")
	            }
	            else {
	                render(view: "edit", model: [studentInstance: studentInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	def query = {
		def studentList = Student.findAllByIdIlike("%" + params.term + "%")
		studentList.sort { it.id }
		
		render(contentType: "text/json") {
			studentList.collect { [id: it.id] };
		}
	}
}
