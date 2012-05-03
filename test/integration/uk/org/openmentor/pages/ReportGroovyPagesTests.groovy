package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.filters.TagNameFilter
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.data.Submission
import uk.org.openmentor.data.Assignment
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.controller.HistoryController;
import uk.org.openmentor.controller.ReportController;
import uk.org.openmentor.controller.SubmissionController
import org.apache.commons.io.IOUtils

/**
 * Integration tests for course GPS pages. These take a little setting up and getting used
 * to, but when complete provide very good coverage of the application as a whole.
 *
 * @author morungos
 */
class ReportGroovyPagesTests extends GroovyPagesTestCase {
	
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
	 * Test the report controller's ability to show a report. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testReportCourse() {
		def file = new File("grails-app/views/report/course.gsp")

		Submission sub = addSubmission("CM2006", "TMA03", '09000231', 'M4000061', 'A', "test/resources/test1a.doc")
		assertTrue sub != null

		sub.save(flush: true, validate: true)
		assertTrue sub.id != null

		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.course()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}
}