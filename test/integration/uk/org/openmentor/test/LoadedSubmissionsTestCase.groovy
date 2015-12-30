package uk.org.openmentor.test

import org.apache.commons.io.IOUtils;

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.data.Submission;

import grails.test.spock.IntegrationSpec

class LoadedSubmissionsTestCase extends IntegrationSpec {

	def analyzerService
	def courseInfoService

	Submission sub1
	Submission sub2
	Submission sub3
	Submission sub4
	Submission sub5

	String userName = "admin"

	void initializeSubmissions() {

		sub1 = addSubmission("CM2006", "TMA01", '09000231', 'M4000061', "Pass 1", userName, "test/resources/test1a.doc")
		sub2 = addSubmission("CM2006", "TMA01", '09000232', 'M4000062', "Pass 2", userName, "test/resources/test2a.doc")
		sub3 = addSubmission("CM2006", "TMA02", '09000231', 'M4000061', "Pass 3", userName, "test/resources/test3a.doc")
		sub4 = addSubmission("CM2006", "TMA02", '09000232', 'M4000062', "Pass 2", userName, "test/resources/test4a.doc")
		sub5 = addSubmission("AA1003", "TMA01", '09000231', 'M4000061', "Pass 3", userName, "test/resources/test5a.doc")
	}

	protected void setup() {
		initializeSubmissions()
	}

	/**
	 * Helper method to add a new submission to the database for a test. We only
	 * do this at test time, so it will be rolled back by an enclosing
	 * transaction at the end of the test.
	 * @param courseCode
	 * @param assignmentCode
	 * @param studentId
	 * @param tutorId
	 * @param grade
	 * @param inputFile
	 * @return
	 */
	private def Submission addSubmission(String courseCode, String assignmentCode,
			String studentId, String tutorId,
			String grade, String userName,
			String inputFile) {

		Course course = courseInfoService.findCourse(courseCode)
		assertTrue course != null

		Assignment assignment = courseInfoService.findAssignment(courseCode, assignmentCode)
		assertTrue assignment != null

		Submission sub = analyzerService.newSubmission(
			assignment,
			[studentId] as Set<String>,
			[tutorId] as Set<String>,
			grade,
			userName,
			inputFile,
			IOUtils.toByteArray(new FileInputStream(inputFile))
		)

		assertTrue sub != null

		sub.save(flush: true, validate: true)
		if (sub.hasErrors()) {
			sub.errors.allErrors.each { log.error it }
		}
		assertTrue sub.id != null
		return sub
	}
}
