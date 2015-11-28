package uk.org.openmentor.controller

import grails.plugin.springsecurity.annotation.Secured;
import uk.org.openmentor.courseinfo.Student;

@Secured(['ROLE_OPENMENTOR-USER'])
class StudentController {
	
	def courseInfoService

    def index() { 
		redirect(action: "list", params: params)
	}

	def list() {
		def studentList = courseInfoService.getStudents(params)
		def studentCount = courseInfoService.getStudentCount()		
		[studentInstanceList: studentList, studentInstanceTotal: studentCount]
	}

	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def save() {
		def studentInstance = new Student(params)
		courseInfoService.initializeStudent(studentInstance)
		
		if (studentInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'student.label', default: 'Student'), studentInstance.studentId])}"
			redirect(action: "list", id: studentInstance.id)
		}
		else {
			log.warn("Failed to create new sample: returning to dialog")
			render(view: "create", model: [studentInstance: studentInstance])
		}
	}
	
	def show() {
		def studentInstance = courseInfoService.findStudent(params.studentId)
        if (!studentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.studentId])}"
            redirect(action: "list")
        }
        else {
			def courseList = courseInfoService.getCourses([:])
            [studentInstance: studentInstance, courseList: courseList]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def edit() {
		def studentInstance = courseInfoService.findStudent(params.studentId)
		if (!studentInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.studentId])}"
			redirect(action: "list")
		}
		else {
			def courseList = courseInfoService.getCourses([:])
			[studentInstance: studentInstance, courseList: courseList]
		}
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def delete() {
    	def studentInstance = courseInfoService.findStudent(params.id)
    	if (!studentInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.id])}"
			redirect(action: "list")
		}
		else {
			def studentId = studentInstance.studentId
			courseInfoService.deleteStudent(studentInstance)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'student.label', default: 'Student'), studentId])}"
			redirect(action: "list")
		}
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def create() {
		def courseList = courseInfoService.getCourses([:])
		[courseList: courseList]
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def update() {
		Student.withSession { session ->
	        def studentInstance = courseInfoService.findStudent(params.studentId)
			
			if (studentInstance) {
				log.debug("Updating student: studentId: " + studentInstance.studentId)
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
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'student.label', default: 'Student'), studentInstance.studentId])}"
	                redirect(action: "list")
	            }
	            else {
	                render(view: "edit", model: [studentInstance: studentInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'student.label', default: 'Student'), params.studentId])}"
	            redirect(action: "list")
	        }
		}
    }

	def query() {
		def studentList = courseInfoService.findStudentsLike("%" + params.term + "%")
		
		render(contentType: "text/json") {
			studentList.collect { [id: it.studentId] };
		}
	}
}
