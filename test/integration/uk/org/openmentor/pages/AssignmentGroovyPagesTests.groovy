package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.controller.AssignmentController

/**
 * Integration tests for assignment pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 * 
 * @author morungos
 */
class AssignmentGroovyPagesTests extends GroovyPagesTestCase {

	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	/**
	 * Test the assignment controller's ability to list assignments.
	 */
	void testAssignmentList() {
		def file = new File("grails-app/views/assignment/list.gsp")

		def controller = new AssignmentController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.list()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("TMA01"))
	}
	
	/**
	 * Test the assignment controller's ability to create an assignment.
	 */
	void testAssignmentCreate() {
		def file = new File("grails-app/views/assignment/create.gsp")

		def controller = new AssignmentController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.create()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("CM2006"))
	}
	
	/**
	 * Test the assignment controller's ability to show an assignment.
	 */
	void testAssignmentShow() {
		def file = new File("grails-app/views/assignment/show.gsp")
		
		def controller = new AssignmentController()
		controller.session.putAt('current_course', 'CM2006')
		controller.params.id = "TMA01"
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
		
		assertTrue(htmlString.contains("TMA01"))
	}

	/**
	 * Test the assignment controller's ability to edit an assignment.
	 */
	void testAssignmentEdit() {
		def file = new File("grails-app/views/assignment/edit.gsp")
		
		def controller = new AssignmentController()
		controller.session.putAt('current_course', 'CM2006')
		controller.params.id = "TMA01"
		def model = controller.edit()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
		
		assertTrue(htmlString.contains("TMA01"))
	}
}

