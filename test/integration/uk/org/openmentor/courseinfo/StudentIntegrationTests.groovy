package uk.org.openmentor.courseinfo

import grails.test.*

class StudentIntegrationTests extends GroovyTestCase {
	
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
    void testFirstStudent() {
		def student09000231 = courseInfoService.findStudent("09000231")
		assertNotNull(student09000231)
    }

	void testStudentProperties() {
		def student09000231 = courseInfoService.findStudent("09000231")
		
		assertEquals "Gwenda", student09000231.givenName
		assertEquals "Blane", student09000231.familyName
	}
}
