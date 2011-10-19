package uk.org.openmentor.controller

import uk.org.openmentor.courseinfo.Course

class CourseController {

    def index = {
        redirect(action: "list", params: params)
    }
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'courseId'
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
	
	def save = {
		def courseInstance = new Course(params)
		
		if (courseInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.courseId])}"
			redirect(action: "list", id: courseInstance.courseId)
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
	
	def create = { }
	
	def update = {
        def courseInstance = Course.get(params.id)
		
		if (courseInstance) {
			log.info("Updating course: courseId: " + courseInstance.courseId)
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
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.courseId])}"
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
			def course = Course.findByCourseId(params.courseId)
			if (course) {
				session.current_course = course.courseId
			}
		}
	}
	
	def query = {
		def courseList = Course.findAllByCourseIdIlike("%" + params.term + "%")
		courseList.sort { it.courseId }
		
		render(contentType: "text/json") {
			courseList.collect { [courseId: it.courseId] };
		}
	}
}
