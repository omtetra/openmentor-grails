package uk.org.openmentor.service

import grails.test.mixin.*
import org.junit.*
import org.apache.commons.io.IOUtils

import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Assignment;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.test.LoadedSubmissionsTestCase

class SummarizationServiceTests extends LoadedSubmissionsTestCase {

	def summarizationService
	
    protected void setUp() {
		super.setUp()
	}

    void testSummary() {
		
		Summary result = summarizationService.getSummary()
        assertTrue result != null
		assertTrue result.data != null
    }
	
    void testCourseSummary() {
		
		Summary result = summarizationService.getCourseSummary("CM2006")
        assertTrue result != null
		assertTrue result.data != null
    }
	
    void testCourseSummaryByAssignment() {

		Summary result = summarizationService.getCourseSummaryByAssignment("CM2006")
        assertTrue result != null
		assertTrue result.data != null
    }
	
    void testCourseSummaryByTutor() {

		Summary result = summarizationService.getCourseSummaryByTutor("CM2006")
        assertTrue result != null
		assertTrue result.data != null
    }
	
    void testCourseSummaryByStudent() {

		Summary result = summarizationService.getCourseSummaryByStudent("CM2006")
        assertTrue result != null
		assertTrue result.data != null
    }
	
    void testSubmissionSummary() {

		Summary result = summarizationService.getSubmissionSummary(sub2)
        assertTrue result != null
		assertTrue result.data != null
    }

    void testCourseAndAssignmentSummary() {

		Summary result = summarizationService.getCourseAndAssignmentSummary("CM2006", "TMA01")
        assertTrue result != null
		assertTrue result.data != null
    }

    void testCourseAndTutorSummary() {

		Summary result = summarizationService.getCourseAndTutorSummary("CM2006", "M4000061")
        assertTrue result != null
		assertTrue result.data != null
    }

    void testCourseAndStudentSummary() {

		Summary result = summarizationService.getCourseAndStudentSummary("CM2006", "09000231")
        assertTrue result != null
		assertTrue result.data != null
    }
}
