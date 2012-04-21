package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.controller.CourseController

/**
 * Integration tests for course GPS pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 * 
 * @author morungos
 */
class CourseGroovyPagesTests extends GroovyPagesTestCase {

	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	/**
	 * Test the course controller's ability to create a form for a new
	 * course.
	 */
	void testCourseCreate() {
		def file = new File("grails-app/views/course/create.gsp")

		def controller = new CourseController()
		def model = controller.create()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
	}

	/**
	 * Test the course controller's ability to list courses.
	 */
	void testCourseList() {
		def file = new File("grails-app/views/course/list.gsp")

		def controller = new CourseController()
		def model = controller.list()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the course controller's ability to offer a selection of courses.
	 */
	void testCourseSelect() {
		def file = new File("grails-app/views/course/select.gsp")

		def controller = new CourseController()
		def model = controller.select()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the submission controller's ability to edit a submission.
	 */
	void testCourseShow() {
		def file = new File("grails-app/views/course/show.gsp")

		def course = Course.findByCourseId("CM2006")
		assertTrue course != null

		def controller = new CourseController()
		controller.params.id = course.courseId
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
	}


	/**
	 * Test the submission controller's ability to edit a submission.
	 */
	void testCourseEdit() {
		def file = new File("grails-app/views/course/edit.gsp")

		def course = Course.findByCourseId("CM2006")
		assertTrue course != null

		def controller = new CourseController()
		controller.params.id = course.courseId
		def model = controller.edit()

		def htmlString = applyTemplate(file.text, model)
		
		// Throw this into an HTML parser for testing
		def parser = new Parser(htmlString)
		def nodes = parser.parse(null)
		
		// Now check we have some inputs
		def inputs = nodes.extractAllNodesThatMatch(new TagNameFilter("input"), true)
		assertTrue(inputs.size > 1)
	}
}

