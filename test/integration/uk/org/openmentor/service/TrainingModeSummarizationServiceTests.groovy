package uk.org.openmentor.service

import grails.test.mixin.*
import groovy.lang.Closure;

import org.junit.*;
import org.apache.commons.io.IOUtils;
import grails.plugin.springsecurity.SpringSecurityUtils;

import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary;
import uk.org.openmentor.test.LoadedSubmissionsTestCase;

class TrainingModeSummarizationServiceTests extends LoadedSubmissionsTestCase {

	def summarizationService
	
	def withAuthenticatedTrainingMode(String user, Closure c) {
		SpringSecurityUtils.doWithAuth(user) {
			def oldTrainingMode = courseInfoService.trainingMode
			courseInfoService.trainingMode = true
			try {
				c()
			} finally {
				courseInfoService.trainingMode = false
			}
		}
	}

	/**
	 * Slightly modified training data, as we are in a training mode. 
	 */
	void initializeSubmissions() {

		withAuthenticatedTrainingMode "user", {
			sub1 = addSubmission("CMM511", "TMA01", '09000238', 'M4000064', "Pass 1", "user", "test/resources/test1a.doc")
			sub2 = addSubmission("CMM511", "TMA02", '09000238', 'M4000064', "Pass 2", "user", "test/resources/test2a.doc")
		}
		withAuthenticatedTrainingMode "other", {
			sub3 = addSubmission("CMM511", "TMA01", '09000238', 'M4000064', "Pass 3", "other", "test/resources/test3a.doc")
			sub4 = addSubmission("CMM511", "TMA02", '09000238', 'M4000064', "Pass 2", "other", "test/resources/test4a.doc")
		}
	}

    protected void setUp() {
		super.setUp()
	}

    void testSummaryUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseSummary("CMM511")
	        assertTrue result != null
			assertTrue result.data != null
			assertEquals 8, result.data.getAt("A")?.actual
			assertEquals 12, result.data.getAt("B")?.actual
			assertEquals 9, result.data.getAt("C")?.actual
			assertEquals 1, result.data.getAt("D")?.actual
		}
    }

    void testSummaryOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseSummary("CMM511")
	        assertTrue result != null
			assertTrue result.data != null
			assertEquals 14, result.data.getAt("A")?.actual
			assertEquals 52, result.data.getAt("B")?.actual
			assertEquals 24, result.data.getAt("C")?.actual
			assertEquals 9, result.data.getAt("D")?.actual
		}
    }

    void testSummaryByStudentUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseSummaryByStudent("CMM511")
	        assertTrue result != null
			assertTrue result.data != null
			assertTrue result.data.getAt("09000238") != null
			assertEquals 8, result.data.getAt("09000238").getAt("A")?.actual
			assertEquals 12, result.data.getAt("09000238").getAt("B")?.actual
			assertEquals 9, result.data.getAt("09000238").getAt("C")?.actual
			assertEquals 1, result.data.getAt("09000238").getAt("D")?.actual
		}
    }

    void testSummaryByStudentOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseSummaryByStudent("CMM511")
	        assertTrue result != null
			assertTrue result.data != null
			assertTrue result.data.getAt("09000238") != null
			assertEquals 14, result.data.getAt("09000238").getAt("A")?.actual
			assertEquals 52, result.data.getAt("09000238").getAt("B")?.actual
			assertEquals 24, result.data.getAt("09000238").getAt("C")?.actual
			assertEquals 9, result.data.getAt("09000238").getAt("D")?.actual
		}
    }
	
	void testSummaryByTutorUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseSummaryByTutor("CMM511")
			assertTrue result != null
			assertTrue result.data != null
			assertTrue result.data.getAt("M4000064") != null
			assertEquals 8, result.data.getAt("M4000064").getAt("A")?.actual
			assertEquals 12, result.data.getAt("M4000064").getAt("B")?.actual
			assertEquals 9, result.data.getAt("M4000064").getAt("C")?.actual
			assertEquals 1, result.data.getAt("M4000064").getAt("D")?.actual
		}
	}

	void testSummaryByTutorOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseSummaryByTutor("CMM511")
			assertTrue result != null
			assertTrue result.data != null
			assertTrue result.data.getAt("M4000064") != null
			assertEquals 14, result.data.getAt("M4000064").getAt("A")?.actual
			assertEquals 52, result.data.getAt("M4000064").getAt("B")?.actual
			assertEquals 24, result.data.getAt("M4000064").getAt("C")?.actual
			assertEquals 9, result.data.getAt("M4000064").getAt("D")?.actual
		}
	}

	void testCourseAndAssignmentSummaryUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseAndAssignmentSummary("CMM511", "TMA01")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 2, result.data.getAt("A")?.actual
			assertEquals 4, result.data.getAt("B")?.actual
			assertEquals 5, result.data.getAt("C")?.actual
			assertEquals 1, result.data.getAt("D")?.actual
		}
	}

	void testCourseAndAssignmentSummaryOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseAndAssignmentSummary("CMM511", "TMA01")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 3, result.data.getAt("A")?.actual
			assertEquals 12, result.data.getAt("B")?.actual
			assertEquals 7, result.data.getAt("C")?.actual
			assertEquals 2, result.data.getAt("D")?.actual
		}
	}

	void testCourseAndStudentSummaryUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseAndStudentSummary("CMM511", "09000238")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 8, result.data.getAt("A")?.actual
			assertEquals 12, result.data.getAt("B")?.actual
			assertEquals 9, result.data.getAt("C")?.actual
			assertEquals 1, result.data.getAt("D")?.actual
		}
	}

	void testCourseAndStudentSummaryOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseAndStudentSummary("CMM511", "09000238")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 14, result.data.getAt("A")?.actual
			assertEquals 52, result.data.getAt("B")?.actual
			assertEquals 24, result.data.getAt("C")?.actual
			assertEquals 9, result.data.getAt("D")?.actual
		}
	}

	void testCourseAndTutorSummaryUser() {
		
		withAuthenticatedTrainingMode "user", {
			Summary result = summarizationService.getCourseAndTutorSummary("CMM511", "M4000064")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 8, result.data.getAt("A")?.actual
			assertEquals 12, result.data.getAt("B")?.actual
			assertEquals 9, result.data.getAt("C")?.actual
			assertEquals 1, result.data.getAt("D")?.actual
		}
	}

	void testCourseAndTutorSummaryOther() {
		
		withAuthenticatedTrainingMode "other", {
			Summary result = summarizationService.getCourseAndTutorSummary("CMM511", "M4000064")
			assertTrue result != null
			assertTrue result.data != null
			assertEquals 14, result.data.getAt("A")?.actual
			assertEquals 52, result.data.getAt("B")?.actual
			assertEquals 24, result.data.getAt("C")?.actual
			assertEquals 9, result.data.getAt("D")?.actual
		}
	}
}
