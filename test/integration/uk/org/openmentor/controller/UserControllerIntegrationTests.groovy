package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*

import grails.plugin.springsecurity.SpringSecurityUtils;
import org.gmock.WithGMock
import uk.org.openmentor.auth.User
import uk.org.openmentor.courseinfo.Tutor

@WithGMock
class UserControllerIntegrationTests extends GroovyTestCase {
	
	UserController controller
	
	Map savedMetaClasses = [:]
	Map renderMap
	Map redirectMap

    protected void setUp() {
        super.setUp()

		registerMetaClass(UserController.class)
		UserController.metaClass.render = {Map m ->
			renderMap = m
		}
		UserController.metaClass.redirect = {Map m ->
			redirectMap = m
		}

		controller = new UserController()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	/**
	 * Test the list action
	 */
	void testListAction() {
		def model = controller.list()
		
		assertEquals 3, model.userInstanceTotal
		assertTrue model.userInstanceList.every { it instanceof User }
	}

	/**
	 * Test the create action
	 */
	void testCreateAction() {
		SpringSecurityUtils.doWithAuth("admin") {
			controller.create()
		
			assertNull(renderMap)
			assertNull(redirectMap)
		}
	}
	
	/**
	 * Test the set password action
	 */
	void testSetPasswordAction() {
		SpringSecurityUtils.doWithAuth("admin") {
			controller.params.id = "user"
			controller.set_password()
		
			assertNull(renderMap)
			assertNull(redirectMap)
		}
	}
	
	/**
	 * Test the edit action
	 */
	void testEditAction() {
		SpringSecurityUtils.doWithAuth("admin") {
			controller.params.id = 'user'
			def model = controller.edit()
		
			assertEquals 'user', model.userInstance?.username
		}
	}

	/**
	 * Test the show action
	 */
	void testShowAction() {
		SpringSecurityUtils.doWithAuth("admin") {
			controller.params.id = 'user'
			def model = controller.show()
		
			assertEquals 'user', model.userInstance?.username
		}
	}

	/**
	 * Test the update action
	 */
	void testUpdateAction() {
		SpringSecurityUtils.doWithAuth("admin") {
			controller.params.id = User.findByUsername('user')?.id
			controller.params.username = 'morag'
			controller.update()
		
			if (renderMap?.view == "edit") {
				def errors = renderMap.model.userInstance.errors
				errors.allErrors.each { System.err.println(it.toString()) }
			}
		
			assertNull(renderMap)
			assertNotNull(redirectMap)
			
			assertEquals 'list', redirectMap.action
			
			// And check the saved data
			User found = User.findByUsername('morag')
			assertNotNull found
		}
	}

	// Stolen from GrailsUnitTestCase
	/**
	 * Use this method when you plan to perform some meta-programming
	 * on a class. It ensures that any modifications you make will be
	 * cleared at the end of the test.
	 * @param clazz The class to register.
	 */
	protected void registerMetaClass(Class clazz) {
		// If the class has already been registered, then there's
		// nothing to do.
		if (savedMetaClasses.containsKey(clazz)) return

		// Save the class's current meta class.
		savedMetaClasses[clazz] = clazz.metaClass

		// Create a new EMC for the class and attach it.
		def emc = new ExpandoMetaClass(clazz, true, true)
		emc.initialize()
		GroovySystem.metaClassRegistry.setMetaClass(clazz, emc)
	}

}
