package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*

import grails.plugin.springsecurity.SpringSecurityUtils;
import uk.org.openmentor.auth.User
import uk.org.openmentor.courseinfo.Tutor

import grails.test.spock.IntegrationSpec

class UserControllerIntegrationTests extends IntegrationSpec {
	
	static transactional = true
	
	/**
	 * Test the list action
	 */
	void testListAction() {
		given: 'UserController'	
		def controller = new UserController()
	
		when: 'list is called'
		def model = controller.list()

		then: 'model is correct'
		model.userInstanceTotal == 3
		model.userInstanceList.every { it instanceof User }
	}

	/**
	 * Test the create action
	 */
	void testCreateAction() {
		given: 'UserController'	
		def controller = new UserController()
		
		when: 'create is called'
		def model = null
		SpringSecurityUtils.doWithAuth("admin") {
			model = controller.create()
		}
			
		then: 'model is correct'
		model != null
	}
	
	/**
	 * Test the set password action
	 */
	void testSetPasswordAction() {
		given: 'UserController'	
		def controller = new UserController()
		
		and: 'the user is set'
		controller.params.id = "user"
		
		when: 'set_password is called'
		def model = null
		SpringSecurityUtils.doWithAuth("admin") {
			model = controller.set_password()
		}
		
		then: 'model is correct'
		model != null
	}
	
	/**
	 * Test the edit action
	 */
	void testEditAction() {
		given: 'UserController'	
		def controller = new UserController()
		
		and: 'the user is set'
		controller.params.id = "user"

		when: 'edit is called'
		def model = null
		SpringSecurityUtils.doWithAuth("admin") {
			model = controller.edit()
		}
		
		then: 'model is correct'
		model.userInstance?.username == 'user'
	}

	/**
	 * Test the show action
	 */
	void testShowAction() {
		given: 'UserController'	
		def controller = new UserController()
		
		and: 'the user is set'
		controller.params.id = "user"

		when: 'show is called'
		def model = null
		SpringSecurityUtils.doWithAuth("admin") {
			model = controller.show()
		}
		
		then: 'model is correct'
		model.userInstance?.username == 'user'
	}

	/**
	 * Test the update action
	 */
	void testUpdateAction() {
		given: 'UserController'	
		def controller = new UserController()

		and: 'the user is set'
		controller.params.id = User.findByUsername('user')?.id
		controller.params.username = 'morag'

		when: 'update is called'
		SpringSecurityUtils.doWithAuth("admin") {
			controller.update()
		}	
		
		then: 'redirect to list and the user has been renamed'
		controller.response.redirectUrl == "/user/list"
		
		def found = User.findByUsername('morag')
		found != null
	}
}
