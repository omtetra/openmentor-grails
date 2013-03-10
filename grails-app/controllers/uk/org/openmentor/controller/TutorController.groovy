package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Tutor;

@Secured(['ROLE_OPENMENTOR-USER'])
class TutorController {

	def courseInfoService

    def index = { 
		redirect(action: "list", params: params)
	}

	def list = {
		def tutorList = courseInfoService.getTutors(params)
		def tutorCount = courseInfoService.getTutorCount()		
		[tutorInstanceList: tutorList, tutorInstanceTotal: tutorCount]
	}

	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def save = {
		def tutorInstance = new Tutor(params)		
		courseInfoService.initializeTutor(tutorInstance)
		if (tutorInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'tutor.label', default: 'Tutor'), tutorInstance.id])}"
			redirect(action: "list", id: tutorInstance.id)
		}
		else {
			log.warn("Failed to create new tutor: returning to dialog")
			render(view: "create", model: [tutorInstance: tutorInstance])
		}
	}
	
	def show = {
		def tutorInstance = courseInfoService.findTutor(params.tutorId)
        if (!tutorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
            redirect(action: "list")
        }
        else {
			def courseList = courseInfoService.getCourses([:])
            [tutorInstance: tutorInstance, courseList: courseList]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def edit = {
		def tutorInstance = courseInfoService.findTutor(params.tutorId)
        if (!tutorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
            redirect(action: "list")
        }
        else {
			def courseList = courseInfoService.getCourses([:])
            [tutorInstance: tutorInstance, courseList: courseList]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def delete = {
    	def tutorInstance = courseInfoService.findTutor(params.id)
    	if (!tutorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
			redirect(action: "list")
		}
		else {
			def tutorId = tutorInstance.tutorId
			courseInfoService.deleteTutor(tutorInstance)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tutor.label', default: 'Tutor'), tutorId])}"
			redirect(action: "list")
		}
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def create = { 
		def courseList = courseInfoService.getCourses([:])
		[courseList: courseList]
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def update = {
    	Tutor.withSession { session ->
	        def tutorInstance = courseInfoService.findTutor(params.tutorId)
		
	        if (tutorInstance) {
	        	log.debug("Updating tutor: tutorId: " + tutorInstance.tutorId)
	        	if (params.version) {
	        		def version = params.version.toLong()
	        		if (tutorInstance.version > version) {
                    
	        			tutorInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tutor.label', default: 'Tutor')] as Object[], "Another user has updated this tutor while you were editing")
	        			render(view: "edit", model: [tutorInstance: tutorInstance])
	        			return
	        		}
	        	}
			
	        	// When handling an update, we need to deal with the issues for the
				// associated courses and other components. For that reason, we block
				// changes to the identifier.
				
	        	tutorInstance.properties = params
	    		tutorInstance.save()

	        	if (!tutorInstance.hasErrors() && tutorInstance.save(flush: true)) {
	        		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tutor.label', default: 'Tutor'), tutorInstance.id])}"
	        		redirect(action: "list")
	        	}
	        	else {
	        		render(view: "edit", model: [tutorInstance: tutorInstance])
	        	}
	        }
	        else {
	        	flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
	        	redirect(action: "list")
	        }
    	}
    }

	def query = {
		def tutorList = courseInfoService.findTutorsLike("%" + params.term + "%")
		
		render(contentType: "text/json") {
			tutorList.collect { [id: it.tutorId] };
		}
	}
}
