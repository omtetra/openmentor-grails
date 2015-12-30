package uk.org.openmentor.pages;

import grails.test.*
import org.htmlparser.Parser
import org.htmlparser.util.NodeList
import org.htmlparser.util.SimpleNodeIterator;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.data.Submission
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
										 
	private void addOneSubmission() {
		Submission sub = addSubmission("CM2006", "TMA03", '09000231', 'M4000061', "Pass 1", "test/resources/test1a.doc")
		assertTrue sub != null

		sub.save(flush: true, validate: true)
		assertTrue sub.id != null
	}
	
	/**
	 * Test the report controller's ability to show a report. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testReportCourse() {
		def file = new File("grails-app/views/report/course.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.course()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the report controller's ability to show a report. To do this, 
	 * of course, we actually need a submission that we can use. 
	 */
	void testReportCourseDetails() {
		def file = new File("grails-app/views/report/course_details.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.course_details()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
		
		// See #54 - comments are duplicated. Under category C, for example, we
		// find a comment "If the exam structure..." more than once. 
		
		// Throw this into an HTML parser for testing
		def parser = new Parser(htmlString)
		NodeList nodes = parser.parse(null)
		
		// Now check we have some inputs
		def nodeFilter = new AndFilter(new TagNameFilter("td"), new CssSelectorNodeFilter(".comments"))
		def paragraphFilter = new TagNameFilter("p")
		NodeList commentCells = nodes.extractAllNodesThatMatch(nodeFilter, true)
		assertTrue(commentCells.size == 4)

		for(int i = 0; i < commentCells.size; i++) {
			NodeList commentCell = new NodeList(commentCells.elementAt(i))
			NodeList paragraphs = commentCell.extractAllNodesThatMatch(paragraphFilter, true)
			SimpleNodeIterator iterator = paragraphs.elements()
			Set table = [] as Set
			while(iterator.hasMoreNodes()) {
				String comment = iterator.nextNode().toHtml()
				assertFalse table.contains(comment)
				table.add(comment)
			}
		}
	}
	
	/**
	 * Test the report controller for a students report.
	 */
	void testReportStudents() {
		def file = new File("grails-app/views/report/students.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.students()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the report controller for a single student report.
	 */
	void testReportStudent() {
		def file = new File("grails-app/views/report/student.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		controller.params.id = '09000231'
		def model = controller.student()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("09000231"))
	}

	/**
	 * Test the report controller for a tutors report.
	 */
	void testReportTutors() {
		def file = new File("grails-app/views/report/tutors.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.tutors()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the report controller for a single tutor report.
	 */
	void testReportTutor() {
		def file = new File("grails-app/views/report/tutor.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		controller.params.id = 'M4000061'
		def model = controller.tutor()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("M4000061"))
	}

	/**
	 * Test the report controller for an assignments report.
	 */
	void testReportAssignments() {
		def file = new File("grails-app/views/report/assignments.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		def model = controller.assignments()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("CM2006"))
	}

	/**
	 * Test the report controller for a single assignment report.
	 */
	void testReportAssignment() {
		def file = new File("grails-app/views/report/assignment.gsp")
		addOneSubmission()
		
		def controller = new ReportController()
		controller.session.putAt('current_course', 'CM2006')
		controller.params.id = "TMA03"
		def model = controller.assignment()

		def htmlString = applyTemplate(file.text, model)
		assertTrue(htmlString.contains("TMA03"))
	}
}