package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Tutor;

@Secured(['ROLE_OPENMENTOR-ADMIN'])
class TutorController {

    def index = { 
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'id'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Tutor.createCriteria()
		
		def tutorList = criteria.list {
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
		
		def tutorCount = Tutor.count()
		
		[tutorInstanceList: tutorList, tutorInstanceTotal: tutorCount]
	}

	def save = {
		def tutorInstance = new Tutor(params)
		
		if (tutorInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'tutor.label', default: 'Tutor'), tutorInstance.id])}"
			redirect(action: "list", id: tutorInstance.id)
		}
		else {
			log.info("Failed to create new tutor: returning to dialog")
			render(view: "create", model: [tutorInstance: tutorInstance])
		}
	}
	
	def show = {
		def tutorInstance = Tutor.findById(params.id)
        if (!tutorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tutorInstance: tutorInstance]
        }
	}
	
	def edit = {
		def tutorInstance = Tutor.findById(params.id)
        if (!tutorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tutor.label', default: 'Tutor'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tutorInstance: tutorInstance]
        }
	}
	
	def create = { }
	
	def update = {
    	Tutor.withSession { session ->
	        def tutorInstance = Tutor.findById(params.id)
		
	        if (tutorInstance) {
	        	log.info("Updating tutor: id: " + tutorInstance.id)
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
				
				tutorInstance.givenName = params.givenName
	        	tutorInstance.familyName = params.familyName
	        	tutorInstance.id = params.id
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
		def tutorList = Tutor.findAllByIdIlike("%" + params.term + "%")
		tutorList.sort { it.id }
		
		render(contentType: "text/json") {
			tutorList.collect { [id: it.id] };
		}
	}
}
