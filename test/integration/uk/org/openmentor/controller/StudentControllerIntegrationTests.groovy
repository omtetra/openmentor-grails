package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*

import uk.org.openmentor.courseinfo.Student

import grails.test.spock.IntegrationSpec

class StudentControllerIntegrationTests extends IntegrationSpec {
	
	static transactional = true
	
	/**
	 * Test the create action
	 */
	void testCreateAction() {
		given: 'StudentController'
        def controller = new StudentController()
		
		when: 'create is called'
		controller.create()
		
		then: 'check redirect url is null'
        controller.response.redirectUrl == null
	}
	
	/**
	 * Test the edit action
	 */
	void testEditAction() {
		given: 'StudentController'
        def controller = new StudentController()

        and: 'with studentId set to 09000231'
		controller.params.studentId = '09000231'

		when: 'edit is called'
		def model = controller.edit()
		
		then: 'model is correct'
		model.studentInstance?.studentId == '09000231'
	}

	/**
	 * Test the show action
	 */
	void testShowAction() {
		given: 'StudentController'
        def controller = new StudentController()

        and: 'with studentId set to 09000231'
		controller.params.studentId = '09000231'

		when: 'show is called'
		def model = controller.show()

		then: 'model is correct'
		model.studentInstance?.studentId == '09000231'
	}

	/**
	 * Test the list action
	 */
	void testListAction() {
		given: 'StudentController'
        def controller = new StudentController()

		when: 'list is called'
		def model = controller.list()

		then: 'model is correct'
		model.studentInstanceTotal == 9
		model.studentInstanceList.every { it instanceof Student }
	}
	
	/**
	 * Test the update action
	 */
	void testUpdateAction() {
		given: 'StudentController'
        def controller = new StudentController()

		and: 'with studentId set to 09000231'
		controller.params.studentId = '09000231'
		
		and: 'with givenName set to Given'
		controller.params.givenName = 'Given'

		and: 'with familyName set to Family'
		controller.params.familyName = 'Family'
		
		when: 'update is called'
		controller.update()
		
		then: 'redirect to list and the data is updated'
        controller.response.redirectUrl == "/student/list"
		
		def found = Student.findByStudentId('09000231')
		found != null
		found.givenName == 'Given'
		found.familyName == 'Family'
	}

	/**
	 * Test the delete action
	 */
	void testDeleteAction() {
		given: 'StudentController'
        def controller = new StudentController()

		and: 'with id set to 09000237'
		controller.params.id = '09000237'

		when: 'update is called'
		controller.delete()

		then: 'redirect to list and the data is updated'
        controller.response.redirectUrl == "/student/list"
        controller.flash.message == "Student 09000237 deleted"
        
        def found = Student.findByStudentId('09000237')
		found == null
	}
}
