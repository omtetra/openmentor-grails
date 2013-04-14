package uk.org.openmentor.courseinfo

import grails.test.*

class CourseIntegrationTests extends GroovyTestCase {
	
	def courseInfoService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Simple check we can read a course from the test data
	 */
    void testFirstCourse() {
		def courseCM2006 = courseInfoService.findCourse("CM2006")
		assertNotNull(courseCM2006)
    }

	/**
	 * Simple check we can read a course's students from the test data
	 */
    void testFirstCourseTutors() {
		def courseCM2006 = courseInfoService.findCourse("CM2006")
		assertEquals(3, courseCM2006.tutors.size())
    }

	/**
	* Simple check we can read a course's tutors from the test data
	*/
    void testFirstCourseStudents() {
		def courseCM2006 = courseInfoService.findCourse("CM2006")
		assertEquals(6, courseCM2006.students.size())
    }
}
