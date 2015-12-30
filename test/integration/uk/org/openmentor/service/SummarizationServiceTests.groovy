package uk.org.openmentor.service

import grails.test.mixin.*
import org.junit.*
import org.apache.commons.io.IOUtils

import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.test.LoadedSubmissionsTestCase;

class SummarizationServiceTests extends LoadedSubmissionsTestCase {

	static transactional = true

	def summarizationService
	
    void testCourseSummary() {
		
		Summary result = summarizationService.getCourseSummary("CM2006")
        assertTrue result != null
		assertTrue result.data != null
		assertEquals 22, result.data.getAt("A")?.actual
		assertEquals 64, result.data.getAt("B")?.actual
		assertEquals 33, result.data.getAt("C")?.actual
		assertEquals 10, result.data.getAt("D")?.actual
    }
	
    void testCourseSummaryByAssignment() {

		Summary result = summarizationService.getCourseSummaryByAssignment("CM2006")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("TMA01") != null
		assertTrue result.data.getAt("TMA02") != null
		assertEquals 8, result.data.getAt("TMA01").getAt("A")?.actual
		assertEquals 12, result.data.getAt("TMA01").getAt("B")?.actual
		assertEquals 9, result.data.getAt("TMA01").getAt("C")?.actual
		assertEquals 1, result.data.getAt("TMA01").getAt("D")?.actual
    }
	
    void testCourseSummaryByTutor() {

		Summary result = summarizationService.getCourseSummaryByTutor("CM2006")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("M4000061") != null
		assertEquals 5, result.data.getAt("M4000061").getAt("A")?.actual
		assertEquals 16, result.data.getAt("M4000061").getAt("B")?.actual
		assertEquals 12, result.data.getAt("M4000061").getAt("C")?.actual
		assertEquals 3, result.data.getAt("M4000061").getAt("D")?.actual
    }
	
    void testCourseSummaryByStudent() {

		Summary result = summarizationService.getCourseSummaryByStudent("CM2006")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("09000231") != null
		assertEquals 5, result.data.getAt("09000231").getAt("A")?.actual
		assertEquals 16, result.data.getAt("09000231").getAt("B")?.actual
		assertEquals 12, result.data.getAt("09000231").getAt("C")?.actual
		assertEquals 3, result.data.getAt("09000231").getAt("D")?.actual
    }
	
    void testSubmissionSummary() {

		Summary result = summarizationService.getSubmissionSummary(sub2)
        assertTrue result != null
		assertTrue result.data != null
		assertEquals 6, result.data.getAt("A")?.actual
		assertEquals 8, result.data.getAt("B")?.actual
		assertEquals 4, result.data.getAt("C")?.actual
		assertEquals 0, result.data.getAt("D")?.actual
    }

    void testCourseAndAssignmentSummary() {

		Summary result = summarizationService.getCourseAndAssignmentSummary("CM2006", "TMA01")
        assertTrue result != null
		assertTrue result.data != null
		assertEquals 8, result.data.getAt("A")?.actual
		assertEquals 12, result.data.getAt("B")?.actual
		assertEquals 9, result.data.getAt("C")?.actual
		assertEquals 1, result.data.getAt("D")?.actual
    }

    void testCourseAndAssignmentSubmissions() {

		Summary result = summarizationService.getCourseAndAssignmentSubmissions("CM2006", "TMA01")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("test1a.doc") != null
		assertEquals 2, result.data.getAt("test1a.doc").getAt("A")?.actual
		assertEquals 4, result.data.getAt("test1a.doc").getAt("B")?.actual
		assertEquals 5, result.data.getAt("test1a.doc").getAt("C")?.actual
		assertEquals 1, result.data.getAt("test1a.doc").getAt("D")?.actual
    }

    void testCourseAndTutorSummary() {

		Summary result = summarizationService.getCourseAndTutorSummary("CM2006", "M4000061")
        assertTrue result != null
		assertTrue result.data != null
		assertEquals 5, result.data.getAt("A")?.actual
		assertEquals 16, result.data.getAt("B")?.actual
		assertEquals 12, result.data.getAt("C")?.actual
		assertEquals 3, result.data.getAt("D")?.actual
    }

    void testCourseAndTutorSubmissions() {

		Summary result = summarizationService.getCourseAndTutorSubmissions("CM2006", "M4000061")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("test1a.doc") != null
		assertEquals 2, result.data.getAt("test1a.doc").getAt("A")?.actual
		assertEquals 4, result.data.getAt("test1a.doc").getAt("B")?.actual
		assertEquals 5, result.data.getAt("test1a.doc").getAt("C")?.actual
		assertEquals 1, result.data.getAt("test1a.doc").getAt("D")?.actual
    }

    void testCourseAndStudentSummary() {

		Summary result = summarizationService.getCourseAndStudentSummary("CM2006", "09000231")
        assertTrue result != null
		assertTrue result.data != null
		assertEquals 5, result.data.getAt("A")?.actual
		assertEquals 16, result.data.getAt("B")?.actual
		assertEquals 12, result.data.getAt("C")?.actual
		assertEquals 3, result.data.getAt("D")?.actual
    }

    void testCourseAndStudentSubmissions() {

		Summary result = summarizationService.getCourseAndStudentSubmissions("CM2006", "09000231")
        assertTrue result != null
		assertTrue result.data != null
		assertTrue result.data.getAt("test1a.doc") != null
		assertEquals 2, result.data.getAt("test1a.doc").getAt("A")?.actual
		assertEquals 4, result.data.getAt("test1a.doc").getAt("B")?.actual
		assertEquals 5, result.data.getAt("test1a.doc").getAt("C")?.actual
		assertEquals 1, result.data.getAt("test1a.doc").getAt("D")?.actual
    }
}
