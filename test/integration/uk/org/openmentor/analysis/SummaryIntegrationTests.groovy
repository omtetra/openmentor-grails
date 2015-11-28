package uk.org.openmentor.analysis

import static org.junit.Assert.*
import groovy.util.GroovyTestCase;

import org.junit.*

import uk.org.openmentor.controller.SubmissionCommand;
import uk.org.openmentor.controller.SubmissionController;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary;
import uk.org.openmentor.service.CurrentUserService;
import uk.org.openmentor.service.SummarizationService;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import grails.plugin.springsecurity.SpringSecurityUtils;

import org.gmock.WithGMock;

@WithGMock
class SummaryIntegrationTests extends GroovyTestCase {

	def summarizationService
	def analyzerService
	def courseInfoService
	def currentUserService

    protected void setUp() {
        super.setUp()
		summarizationService = new SummarizationService()
		summarizationService.courseInfoService = courseInfoService
		summarizationService.currentUserService = currentUserService
    }

    protected void tearDown() {
        super.tearDown()
    }

	private def submitAssignment(String course, String assignment, String grade, String student, String tutor, String fileName) {
		def controller = new SubmissionController()	
		
		Submission sub = analyzerService.newSubmission(
			courseInfoService.findAssignment(course, assignment),
			[student] as Set<String>,
			[tutor] as Set<String>,
			grade,
			"admin",
			fileName,
			IOUtils.toByteArray(new FileInputStream(fileName))
		)

		assertTrue(sub.validate() && sub.save(flush: true))
	}

    void testCourseSummary() {
		submitAssignment("CM2006", "TMA03", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000062', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000062', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseSummary("CM2006", true)
		assertNotNull(summary)
		
		assertEquals(4, summary.submissionCount)
		assertEquals(85, summary.commentCount)
		assertEquals(["A", "B", "C", "D"].toList(), summary.data.keySet().toList())
    }

    void testCourseSummaryByTutor() {
		submitAssignment("CM2006", "TMA03", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000062', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000062', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseSummaryByTutor("CM2006", true)
		assertNotNull(summary)
		
		assertEquals(4, summary.submissionCount)
		assertEquals(85, summary.commentCount)
		assertEquals(["M4000061", "M4000062"].toList(), summary.data.keySet().toList())
    }

    void testCourseSummaryByStudent() {
		submitAssignment("CM2006", "TMA03", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000062', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000062', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseSummaryByStudent("CM2006", true)
		assertNotNull(summary)
		
		assertEquals(4, summary.submissionCount)
		assertEquals(85, summary.commentCount)
		assertEquals(["09000231", "09000232", "09000233", "09000234"].toList(), summary.data.keySet().toList())
    }

    void testCourseSummaryByAssignment() {
		submitAssignment("CM2006", "TMA02", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA02", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000061', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000061', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseSummaryByAssignment("CM2006", true)
		assertNotNull(summary)
		
		assertEquals(4, summary.submissionCount)
		assertEquals(85, summary.commentCount)
		assertEquals(["TMA02", "TMA03"].toList(), summary.data.keySet().toList())
    }

    void testCourseAndTutorSummary() {
		submitAssignment("CM2006", "TMA03", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000062', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000062', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseAndTutorSummary("CM2006", "M4000061", true)
		assertNotNull(summary)
		
		assertEquals(2, summary.submissionCount)
		assertEquals(23, summary.commentCount)
		assertEquals(["A", "B", "C", "D"].toList(), summary.data.keySet().toList())
    }

    void testCourseAndStudentSummary() {
		submitAssignment("CM2006", "TMA03", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000062', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000062', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseAndStudentSummary("CM2006", "09000231", true)
		assertNotNull(summary)
		
		assertEquals(1, summary.submissionCount)
		assertEquals(9, summary.commentCount)
		assertEquals(["A", "B", "C", "D"].toList(), summary.data.keySet().toList())
    }

    void testCourseAndAssignmentSummary() {
		submitAssignment("CM2006", "TMA02", "Pass 1", '09000231', 'M4000061', "test/resources/test1a.doc");
		submitAssignment("CM2006", "TMA02", "Pass 3", '09000232', 'M4000061', "test/resources/test2a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 2", '09000233', 'M4000061', "test/resources/test3a.doc");
		submitAssignment("CM2006", "TMA03", "Pass 4", '09000234', 'M4000061', "test/resources/test4a.doc");
		
		Summary summary = summarizationService.getCourseAndAssignmentSummary("CM2006", "TMA03", true)
		assertNotNull(summary)
		
		assertEquals(2, summary.submissionCount)
		assertEquals(62, summary.commentCount)
		assertEquals(["A", "B", "C", "D"].toList(), summary.data.keySet().toList())
    }

}
