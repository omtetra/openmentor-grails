package uk.org.openmentor.courseinfo

import grails.test.*

class TutorIntegrationTests extends GroovyTestCase {
	
	def courseInfoService

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

	/**
	 * Simple check we can read a student from the test data
	 */
    void testFirstTutor() {
		def tutorM4000062 = courseInfoService.findTutor("M4000062")
		assertNotNull(tutorM4000062)
    }

	void testTutorProperties() {
		def tutorM4000062 = courseInfoService.findTutor("M4000062")
		
		assertEquals "Levi", tutorM4000062.givenName
		assertEquals "Evert", tutorM4000062.familyName
	}
}
