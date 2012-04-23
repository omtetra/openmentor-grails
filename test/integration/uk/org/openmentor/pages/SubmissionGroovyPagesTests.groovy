package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.data.Submission
import uk.org.openmentor.data.Assignment
import uk.org.openmentor.controller.SubmissionController
import org.apache.commons.io.IOUtils

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

	/**
	 * Test the submission controller's ability to show a submission. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testSubmissionShow() {
		def file = new File("grails-app/views/submission/show.gsp")
		def inputFile = "test/resources/test1a.doc"

		def course = Course.findByCourseId("CM2006")
		assertTrue course != null
		
		Assignment assignment = Assignment.findByCode("TMA03")
		Submission sub = analyzerService.newSubmission(
			assignment,
			['09000231'] as Set<String>,
			['M4000061'] as Set<String>,
			'A',
			"admin",
			inputFile,
			IOUtils.toByteArray(new FileInputStream(inputFile))
		)
		assertTrue sub != null
		sub.save(flush: true, validate: true)
		assertTrue sub.id != null

		def controller = new SubmissionController()
		controller.params.putAt('id', sub.id)
		
		def model = controller.show()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("Not a word wasted here!"))
	}
}