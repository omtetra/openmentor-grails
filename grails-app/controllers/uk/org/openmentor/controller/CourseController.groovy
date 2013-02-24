package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course

@Secured(['ROLE_OPENMENTOR-USER'])
class CourseController {
	
	def courseInfoService

    def index = {
        redirect(action: "list", params: params)
    }
	
	def list = {
		def courseList = courseInfoService.getCourses(params)
		def courseCount = courseInfoService.getCourseCount()
		def allowDeletion = courseInfoService.getAllowDeletion()	
		[courseInstanceList: courseList, courseInstanceTotal: courseCount, allowDeletion: allowDeletion]
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def save = {
    	def courseInstance = new Course(params)
		
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
    	def courseInstance = courseInfoService.findCourse(params.courseId)
    	if (!courseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
        else {
            [courseInstance: courseInstance]
        }
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def edit = {
    	def courseInstance = courseInfoService.findCourse(params.courseId)
    	if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			[courseInstance: courseInstance]
		}
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def delete = {
    	def courseInstance = courseInfoService.findCourse(params.id)
    	if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			def courseId = courseInstance.courseId
			courseInfoService.deleteCourse(courseInstance)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'course.label', default: 'Course'), courseId])}"
			redirect(action: "list")
		}
	}
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def create = { }
	
	@Secured(["hasRole('MANAGE_COURSEINFO_ROLE')"])
    def update = {
    	def courseInstance = courseInfoService.findCourse(params.courseId)
		
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
			def course = courseInfoService.findCourse(params.courseId)
			if (course) {
				session.current_course = course.courseId
			}
		}
		
		def courseList = courseInfoService.getCourses([:])
		[courseList: courseList]
	}
	
	def query = {
		def courseList = courseInfoService.findCoursesLike("%" + params.term + "%")
		courseList.sort { it.courseId }
		
		render(contentType: "text/json") {
			courseList.collect { [id: it.courseId] };
		}
	}
}
