package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*
import grails.test.mixin.TestMixin;
import grails.test.mixin.integration.Integration;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.data.Submission
import uk.org.openmentor.service.CurrentUserService;

import org.gmock.WithGMock

@WithGMock
@TestMixin(Integration)
class ReportControllerIntegrationTests {
	
	private def controller
	
	Map savedMetaClasses = [:]
	Map renderMap
	Map redirectMap

    protected void setUp() {
        super.setUp()
		
		registerMetaClass(ReportController.class)
		ReportController.metaClass.render = {Map m ->
			renderMap = m
		}
		ReportController.metaClass.redirect = {Map m ->
			redirectMap = m
		}

		controller = new ReportController()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	/**
	 * Test that the index page redirects when we don't have a selected
	 * course
	 */
	void testIndexRedirect() {
		def result = controller.index()
		
		assertNull(renderMap)
		assertNotNull(redirectMap)
		assertEquals "select", redirectMap.action
		assertEquals "course", redirectMap.controller
	}

	/**
	 * Test that the index page redirects when we don't have a selected
	 * course
	 */
	void testIndexNoRedirect() {
		controller.session.putAt('current_course', 'CM2006')
		def result = controller.index()

		assertNull(redirectMap)
		assertNotNull(result?.course)
	}
	
	/**
	 * Test the report when nothing has been uploaded. Regression test for #18
	 */
	void testReportEmptyCourse() {
		controller.session.putAt('current_course', 'AA1003')
		def model = controller.course()
		
		assertTrue model != null
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
