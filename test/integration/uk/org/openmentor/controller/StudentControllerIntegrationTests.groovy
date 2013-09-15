package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*

import org.gmock.WithGMock
import uk.org.openmentor.courseinfo.Student

@WithGMock
class StudentControllerIntegrationTests extends GroovyTestCase {
	
	StudentController controller
	
	Map savedMetaClasses = [:]
	Map renderMap
	Map redirectMap

    protected void setUp() {
        super.setUp()

		registerMetaClass(StudentController.class)
		StudentController.metaClass.render = {Map m ->
			renderMap = m
		}
		StudentController.metaClass.redirect = {Map m ->
			redirectMap = m
		}

		controller = new StudentController()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	/**
	 * Test the create action
	 */
	void testCreateAction() {
		controller.create()
		
		assertNull(renderMap)
		assertNull(redirectMap)
	}
	
	/**
	 * Test the edit action
	 */
	void testEditAction() {
		controller.params.studentId = '09000231'
		def model = controller.edit()
		
		assertEquals '09000231', model.studentInstance?.studentId
	}

	/**
	 * Test the show action
	 */
	void testShowAction() {
		controller.params.studentId = '09000231'
		def model = controller.show()
		
		assertEquals '09000231', model.studentInstance?.studentId
	}

	/**
	 * Test the list action
	 */
	void testListAction() {
		def model = controller.list()
		
		assertEquals 9, model.studentInstanceTotal
		assertTrue model.studentInstanceList.every { it instanceof Student }
	}
	
	/**
	 * Test the update action
	 */
	void testUpdateAction() {
		controller.params.studentId = '09000231'
		controller.params.givenName = 'Given'
		controller.params.familyName = 'Family'
		controller.update()
		
		if (renderMap?.view == "edit") {
			def errors = renderMap.model.studentInstance.errors
			errors.allErrors.each { System.err.println(it.toString()) }
		}
		
		assertNull(renderMap)
		assertNotNull(redirectMap)
		
		assertEquals 'list', redirectMap.action
		
		// And check the saved data
		Student found = Student.findByStudentId('09000231')
		assertNotNull found
		assertEquals 'Given', found.givenName
		assertEquals 'Family', found.familyName
	}

	/**
	 * Test the delete action
	 */
	void testDeleteAction() {
		controller.params.id = '09000237'
		def model = controller.delete()
		
		assertNotNull(redirectMap)
		assertEquals 'list', redirectMap.action
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
