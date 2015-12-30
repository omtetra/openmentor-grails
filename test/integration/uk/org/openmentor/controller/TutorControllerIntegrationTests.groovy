package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*

import uk.org.openmentor.courseinfo.Tutor

import grails.test.spock.IntegrationSpec

class TutorControllerIntegrationTests extends IntegrationSpec {
	
	static transactional = true
	
	/**
	 * Test the create action
	 */
	void testCreateAction() {
		given: 'TutorController'
        def controller = new TutorController()
		
		when: 'create is called'
		controller.create()
		
		then: 'check redirect url is null'
        controller.response.redirectUrl == null
	}
	
	/**
	 * Test the edit action
	 */
	void testEditAction() {
		given: 'TutorController'
        def controller = new TutorController()

        and: 'with tutorId set to M4000061'
		controller.params.tutorId = 'M4000061'

		when: 'edit is called'
		def model = controller.edit()
		
		then: 'model is correct'
		model.tutorInstance?.tutorId == 'M4000061'
	}

	/**
	 * Test the show action
	 */
	void testShowAction() {
		given: 'TutorController'
        def controller = new TutorController()

        and: 'with tutorId set to M4000061'
		controller.params.tutorId = 'M4000061'

		when: 'show is called'
		def model = controller.show()

		then: 'model is correct'
		model.tutorInstance?.tutorId == 'M4000061'
	}

	/**
	 * Test the list action
	 */
	void testListAction() {
		given: 'TutorController'
        def controller = new TutorController()

		when: 'list is called'
		def model = controller.list()

		then: 'model is correct'
		model.tutorInstanceTotal == 5
		model.tutorInstanceList.every { it instanceof Tutor }
	}
	
	/**
	 * Test the update action
	 */
	void testUpdateAction() {
		given: 'TutorController'
        def controller = new TutorController()

		and: 'with tutorId set to M4000061'
		controller.params.tutorId = 'M4000061'
		
		and: 'with givenName set to Given'
		controller.params.givenName = 'Given'

		and: 'with familyName set to Family'
		controller.params.familyName = 'Family'
		
		when: 'update is called'
		controller.update()
		
		then: 'redirect to list and the data is updated'
        controller.response.redirectUrl == "/tutor/list"
		
		def found = Tutor.findByTutorId('M4000061')
		found != null
		found.givenName == 'Given'
		found.familyName == 'Family'
	}

	/**
	 * Test the delete action
	 */
	void testDeleteAction() {
		given: 'TutorController'
        def controller = new TutorController()

		and: 'with id set to M4000063'
		controller.params.id = 'M4000063'

		when: 'update is called'
		controller.delete()

		then: 'redirect to list and the data is updated'
        controller.response.redirectUrl == "/tutor/list"
        controller.flash.message == "Tutor M4000063 deleted"
        
        def found = Tutor.findByTutorId('M4000063')
		found == null
	}
}
