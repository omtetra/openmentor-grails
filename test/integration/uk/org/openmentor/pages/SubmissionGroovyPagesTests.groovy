package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.data.Submission
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.controller.HistoryController;
import uk.org.openmentor.controller.SubmissionController
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;

/**
 * Integration tests for course GPS pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 *
 * @author morungos
 */
class SubmissionGroovyPagesTests extends GroovyPagesTestCase {
	
	def analyzerService

	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}
	
	private def Submission addSubmission(String courseCode, String assignmentCode, 
		                                 String studentId, String tutorId, 
										 String grade, String inputFile) {
		
		def course = Course.findByCourseId(courseCode)
		assertTrue course != null
		
		Assignment assignment = Assignment.findByCode(assignmentCode)
		Submission sub = analyzerService.newSubmission(
			assignment,
			[studentId] as Set<String>,
			[tutorId] as Set<String>,
			grade,
			"admin",
			inputFile,
			IOUtils.toByteArray(new FileInputStream(inputFile))
		)
		
		return sub
	}
	
	/**
	 * Test the submission controller's upload page.
	 */
	void testSubmissionUpload() {
		def file = new File("grails-app/views/submission/upload.gsp")

		def controller = new SubmissionController()
		controller.session.putAt('current_course', 'CM2006')
		
		def model = controller.upload()
		
		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the submission controller's ability to show a submission. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testSubmissionShow() {
		def file = new File("grails-app/views/submission/show.gsp")

		Submission sub = addSubmission("CM2006", "TMA03", '09000231', 'M4000061', "Pass 1", "test/resources/test1a.doc")
		assertTrue sub != null

		sub.save(flush: true, validate: true)
		assertTrue sub.id != null

		def controller = new SubmissionController()
		controller.params.putAt('id', sub.id)
		
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("Submission for test1a.doc"))
		assertTrue(htmlString.contains("Its good to see you found this resource useful."))
	}

	/**
	 * Test the submission controller's ability to show a submission. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testHistoryList() {
		def file = new File("grails-app/views/history/list.gsp")

		Submission sub = addSubmission("CM2006", "TMA03", '09000231', 'M4000061', "Pass 1", "test/resources/test1a.doc")
		assertTrue sub != null

		sub.save(flush: true, validate: true)
		assertTrue sub.id != null

		def controller = new HistoryController()
		
		SpringSecurityUtils.doWithAuth("admin") { 
			def model = controller.list()

			def htmlString = applyTemplate(file.text, model)
			assertTrue(htmlString.contains(sub.filename))
			assertTrue(htmlString.contains("admin"))
		}
	}
}