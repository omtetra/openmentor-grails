package uk.org.openmentor.controller

import uk.org.openmentor.courseinfo.Course

class CourseController {

    def index = {
        redirect(action: "list", params: params)
    }
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'id'
		params.order = params.order ?: 'desc'
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
			log.error("XXX: " + params.id)
			[courseInstance: courseInstance]
		}
	}
	
	def create = { }
	
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
