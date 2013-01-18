package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Assignment;

@Secured(['ROLE_OPENMENTOR-USER'])
class AssignmentController {
	
	private Course getSelectedCourse() {
		def courseId = session.current_course
		def courseInstance = Course.get(courseId)

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

		params.sort = params.sort ?: 'code'
		params.order = params.order ?: 'asc'
		
		def criteria = Assignment.createCriteria()
		
		def assignmentList = criteria.list {
			eq('courseId', courseInstance.id)
			order(params.sort, params.order)
		}
		
		def assignmentCount = assignmentList.size()
		
		[assignmentInstanceList: assignmentList, assignmentInstanceTotal: assignmentCount, courseInstance: courseInstance]
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def create = { 
		def courseInstance = getSelectedCourse()
		
		[courseInstance: courseInstance]
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def save = {
		
		def courseInstance = getSelectedCourse()
		def assignmentInstance = new Assignment(params)
		
		if (assignmentInstance.save(flush: true)) {
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
		def assignmentInstance = Assignment.get(params.id)
		
        if (!assignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [assignmentInstance: assignmentInstance, courseInstance: courseInstance]
        }
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def edit = {
		
		def courseInstance = getSelectedCourse()
		def assignmentInstance = Assignment.get(params.id)
		
        if (!assignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [assignmentInstance: assignmentInstance, courseInstance: courseInstance]
        }
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def update = {
        
		def courseInstance = getSelectedCourse()
		def assignmentInstance = Assignment.get(params.id)
		
		if (assignmentInstance) {
			log.info("Updating assignment: id: " + assignmentInstance.id)
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

	def query = {
		def assignmentList = Assignment.findAllByCodeIlike("%" + params.term + "%")
		assignmentList.sort { it.code }
		
		render(contentType: "text/json") {
			assignmentList.collect { [code: it.code] };
		}
	}
	
	def courseAssignments = {
		log.info("Requested course id: " + params.courseId);
		log.error("Requested course id (error): " + params.courseId);
		def assignmentList = Assignment.findAllByCourseId(params.courseId)
		assignmentList.sort { it.code }
		log.error(assignmentList)
		
		render(contentType: "text/json") {
			assignmentList.collect { [code: it.code] };
		}
	}
}
