package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course;

@Secured(['ROLE_OPENMENTOR-USER'])
class AssignmentController {
	
	def courseInfoService
	
	private Course getSelectedCourse() {
		def courseId = session.current_course
		def courseInstance = courseInfoService.findCourse(courseId)

		if (! courseInstance) {
			redirect(action: "select", controller: "course")
			return
		}
		
		return courseInstance
	}
	
    def index = { 
		redirect(action: "list", params: params)
	}
	
	def list = {
		def courseInstance = getSelectedCourse()
		if (! courseInstance) {
			return
		}

		def assignmentList = courseInfoService.getAssignments(courseInstance, params)
		def assignmentCount = courseInfoService.getAssignmentCount(courseInstance)
		[assignmentInstanceList: assignmentList, assignmentInstanceTotal: assignmentCount, courseInstance: courseInstance]
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
	def create = { 
		def courseInstance = getSelectedCourse()
		[courseInstance: courseInstance]
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
	def save = {		
		def courseInstance = getSelectedCourse()
		def assignmentInstance = new Assignment(params)
		courseInfoService.initializeAssignment(assignmentInstance)
		courseInstance.addToAssignments(assignmentInstance)
		
		if (courseInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'assignment.label', default: 'Assignment'), assignmentInstance.code])}"
			redirect(action: "list", id: assignmentInstance.id)
		}
		else {
			log.info("Failed to create new sample: returning to dialog")
			assignmentInstance.errors.allErrors.each {
				log.error(it)
			}
			render(view: "create", model: [assignmentInstance: assignmentInstance, courseInstance: courseInstance])
		}
	}
	
	def show = {
		def courseInstance = getSelectedCourse()
		def assignmentInstance = courseInfoService.findAssignment(courseInstance, params.id)
		
        if (!assignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [assignmentInstance: assignmentInstance, courseInstance: courseInstance]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
	def edit = {		
		def courseInstance = getSelectedCourse()
		def assignmentInstance = courseInfoService.findAssignment(courseInstance, params.id)
		
        if (!assignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [assignmentInstance: assignmentInstance, courseInstance: courseInstance]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
	def update = {       
		def courseInstance = getSelectedCourse()
		def assignmentInstance = courseInfoService.findAssignment(courseInstance, params.code)
		
		if (assignmentInstance) {
			log.info("Updating assignment: code: " + assignmentInstance.code)
            if (params.version) {
                def version = params.version.toLong()
                if (assignmentInstance.version > version) {
                    
                    assignmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'assignment.label', default: 'Student')] as Object[], "Another user has updated this student while you were editing")
                    render(view: "edit", model: [assignmentInstance: assignmentInstance, courseInstance: courseInstance])
                    return
                }
            }
			
			assignmentInstance.properties = params

            if (!assignmentInstance.hasErrors() && assignmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'assignment.label', default: 'Assignment'), assignmentInstance.code])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [assignmentInstance: assignmentInstance, courseInstance: courseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
	def delete = {		
		def courseInstance = getSelectedCourse()
		def assignmentInstance = courseInfoService.findAssignment(courseInstance, params.id)
		if (!assignmentInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
			redirect(action: "list")
		}
		else {
			def code = assignmentInstance.code
			courseInfoService.deleteAssignment(assignmentInstance)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'assignment.label', default: 'Assignment'), code])}"
			redirect(action: "list")
		}
	}
	
	def query = {
		def courseInstance = getSelectedCourse()
		def assignmentList = courseInfoService.findAssignmentsLike(courseInstance, "%" + params.term + "%")
		
		render(contentType: "text/json") {
			assignmentList.collect { [code: it.code] };
		}
	}
	
	def courseAssignments = {
		log.info("Requested course id: " + params.courseId);
		log.error("Requested course id (error): " + params.courseId);
		
		def courseInstance = getSelectedCourse()
		def assignmentList = courseInfoService.findAssignmentsLike(courseInstance)
		log.error(assignmentList)
		
		render(contentType: "text/json") {
			assignmentList.collect { [code: it.code] };
		}
	}
}
