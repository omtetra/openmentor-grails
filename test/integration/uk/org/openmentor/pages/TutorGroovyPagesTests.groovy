package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.courseinfo.Tutor
import uk.org.openmentor.controller.TutorController


/**
 * Integration tests for tutor GPS pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 * 
 * @author morungos
 */
class TutorGroovyPagesTests extends GroovyPagesTestCase {
	
	def controller

	protected void setUp() {
		super.setUp()
		
		controller = new TutorController()
	}

	protected void tearDown() {
		super.tearDown()
	}

	/**
	 * Test the tutor controller's ability to create a form for a new
	 * course.
	 */
	void testTutorCreate() {
		def file = new File("grails-app/views/tutor/create.gsp")

		def model = controller.create()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
	}

	/**
	 * Test the tutor controller's ability to list courses.
	 */
	void testTutorList() {
		def file = new File("grails-app/views/tutor/list.gsp")

		def model = controller.list()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("M4000061"))
	}

	/**
	 * Test the tutor controller's ability to edit a submission.
	 */
	void testTutorShow() {
		def file = new File("grails-app/views/tutor/show.gsp")

		def tutor = Tutor.findByTutorId("M4000061")
		assertTrue tutor != null

		controller.params.tutorId = tutor.tutorId
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
	}


	/**
	 * Test the tutor controller's ability to edit a submission.
	 */
	void testTutorEdit() {
		def file = new File("grails-app/views/tutor/edit.gsp")

		def tutor = Tutor.findByTutorId("M4000061")
		assertTrue tutor != null

		controller.params.tutorId = tutor.tutorId
		def model = controller.edit()

		def htmlString = applyTemplate(file.text, model)
		
		// Throw this into an HTML parser for testing
		def parser = new Parser(htmlString)
		def nodes = parser.parse(null)
		
		// Now check we have some inputs
		def inputs = nodes.extractAllNodesThatMatch(new TagNameFilter("input"), true)
		assertTrue(inputs.size > 1)
		
		// Check the appropriate two courses are specified
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
		assertTrue(textString.contains("AA1003"))
		assertTrue(textString.contains("CM2006"))
	}
}

