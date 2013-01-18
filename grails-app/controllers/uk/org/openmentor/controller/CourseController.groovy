package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.data.Assignment;

@Secured(['ROLE_OPENMENTOR-USER'])
class CourseController {

    def index = {
        redirect(action: "list", params: params)
    }
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'id'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Course.createCriteria()
		
		def courseList = criteria.list {
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
		
		def courseCount = Course.count()
		
		[courseInstanceList: courseList, courseInstanceTotal: courseCount]
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def save = {
		def courseInstance = new Course(params)
		courseInstance.id = params.id
		
		if (courseInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.id])}"
			redirect(action: "list", id: courseInstance.id)
		}
		else {
			log.info("Failed to create new sample: returning to dialog")
			render(view: "create", model: [courseInstance: courseInstance])
		}
	}
	
	def show = {
		def courseInstance = Course.get(params.id)
        if (!courseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
        else {
            [courseInstance: courseInstance]
        }
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def edit = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			[courseInstance: courseInstance]
		}
	}
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def create = { }
	
	@Secured(['ROLE_OPENMENTOR-POWERUSER'])
    def update = {
        def courseInstance = Course.get(params.id)
		
		if (courseInstance) {
			log.info("Updating course: id: " + courseInstance.id)
            if (params.version) {
                def version = params.version.toLong()
                if (courseInstance.version > version) {
                    
                    courseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'course.label', default: 'Course')] as Object[], "Another user has updated this course while you were editing")
                    render(view: "edit", model: [courseInstance: courseInstance])
                    return
                }
            }
			
			courseInstance.properties = params

            if (!courseInstance.hasErrors() && courseInstance.save(flush: true)) {
				
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.id])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [courseInstance: courseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
    }

	def select = {
		if (request.method == 'POST') {
			def course = Course.findById(params.id)
			if (course) {
				session.current_course = course.id
			}
		}
	}
	
	def query = {
		def courseList = Course.findAllByIdIlike("%" + params.term + "%")
		courseList.sort { it.id }
		
		render(contentType: "text/json") {
			courseList.collect { [id: it.id] };
		}
	}
}
