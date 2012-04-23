package uk.org.openmentor.data

import grails.test.*
import groovy.util.GroovyTestCase;
import uk.org.openmentor.data.Assignment

class SubmissionIntegrationTests extends GroovyTestCase {
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
		def assignment = Assignment.findByCode("TMA03")
		assertTrue assignment != null
		
		def testFileName = 'test/resources/test1a.doc'
		def testFile = new File(testFileName)
		
		// Write the submission
		def submission = new Submission(filename: "foo.doc", grade: "A", username: "admin", fileContents: testFile.getBytes())
		assignment.addToSubmissions(submission)
		assignment.save(validate: true, flush: true)
		
		// And now look for it - confirming it really was written
		def found = Submission.findByFilename("foo.doc")
		assertTrue found != null
    }
}
