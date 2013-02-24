package uk.org.openmentor.courseinfo

import grails.test.*

class CourseTests extends GrailsUnitTestCase {

	def course

    protected void setUp() {
        super.setUp()
		
		mockDomain(Course)
		course = new Course()

		course.courseId = 'DD303'
		course.courseTitle = "Stupid Course Title"
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Checks that the untouched course object passes validation
	 */
    void testValidation() {
		def result = course.validate()
		course.errors.allErrors.each { log.info it.toString() }
		assertTrue result
		assertFalse course.hasErrors()
    }

	/**
	 * Checks that invalid course types fail validation
	 */
	void testCourseId() {
		def old = course.courseId
		
		course.courseId = ''
		assertFalse course.validate()
		assertTrue course.hasErrors()
		
		course.courseId = old
	}
	
	/**
	 * Must always be zero when comparing with self
	 */
	void testCompareSelf() {
		assertEquals(0, Integer.signum(course.compareTo(course)))
	}

	/**
	 * Checks that the untouched student object passes validation
	 */
    void testCompareOrdering() {
		def other = new Course()
		other.courseId = 'M206'
		other.courseTitle = 'Another Course'
		
		assertEquals(-1, Integer.signum(course.compareTo(other)))
		assertEquals(1, Integer.signum(other.compareTo(course)))
    }
}
