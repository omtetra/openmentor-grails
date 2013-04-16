package uk.org.openmentor.data

import grails.test.*
import groovy.util.GroovyTestCase;
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Assignment;

class SubmissionIntegrationTests extends GroovyTestCase {
	
	def courseInfoService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Simple check we can write a submission record the test data
	 */
    void testStoreSubmission() {
		def assignment = courseInfoService.findAssignment("CM2006", "TMA03")
		assertTrue assignment != null
		
		def testFileName = 'test/resources/test1a.doc'
		def testFile = new File(testFileName)
		
		// Write the submission
		def grade = Grade.get("Pass 1")
		def submission = new Submission(filename: "foo.doc", longFilename: "foo.doc", grade: grade, username: "admin", fileContents: testFile.getBytes())
		assignment.addToSubmissions(submission)
		assignment.save(validate: true, flush: true, failOnError: true)
		
		// And now look for it - confirming it really was written
		def found = Submission.findByFilename("foo.doc")
		assertNotNull found
    }
}
