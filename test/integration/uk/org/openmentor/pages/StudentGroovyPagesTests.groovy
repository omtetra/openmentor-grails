package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.controller.StudentController


/**
 * Integration tests for student GPS pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 * 
 * @author morungos
 */
class StudentGroovyPagesTests extends GroovyPagesTestCase {
	
	def controller

	protected void setUp() {
		super.setUp()
		
		controller = new StudentController()
	}

	protected void tearDown() {
		super.tearDown()
	}

	/**
	 * Test the student controller's ability to create a form for a new
	 * course.
	 */
	void testStudentCreate() {
		def file = new File("grails-app/views/student/create.gsp")

		def model = controller.create()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")
	}

	/**
	 * Test the student controller's ability to list courses.
	 */
	void testStudentList() {
		def file = new File("grails-app/views/student/list.gsp")

		def model = controller.list()

		def htmlString = applyTemplate(file.text, model)
		def textString = (htmlString =~ /<[^>]+>/).replaceAll("")

		assertTrue(htmlString.contains("09000233"))
	}

	/**
	 * Test the student controller's ability to edit a submission.
	 */
	void testStudentShow() {
		def file = new File("grails-app/views/student/show.gsp")

		def student = Student.findByStudentId("09000233")
		assertTrue student != null

		controller.params.studentId = student.studentId
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
	}


	/**
	 * Test the student controller's ability to edit a submission.
	 */
	void testStudentEdit() {
		def file = new File("grails-app/views/student/edit.gsp")

		def student = Student.findByStudentId("09000233")
		assertTrue student != null

		controller.params.studentId = student.studentId
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
		assertTrue(textString.contains("CM2006"))
		assertTrue(textString.contains("CM3010"))
		assertTrue(textString.contains("AA1003"))
	}
}

