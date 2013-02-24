package uk.org.openmentor.courseinfo

import uk.org.openmentor.courseinfo.Assignment;
import grails.test.*

class AssignmentTests extends GrailsUnitTestCase {
	
	def assignment
	
    protected void setUp() {
        super.setUp()

		mockDomain(Assignment)
		mockDomain(Course)
		assignment = new Assignment()
		assignment.course = new Course(courseId: "CM2006")
		assignment.code = "TMA01"
		assignment.title = "Stupid Course Title"
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Checks that the untouched course object passes validation
	 */
    void testValidation() {
		def result = assignment.validate()
		assignment.errors.allErrors.each { log.info it.toString() }
		assertTrue result
		assertFalse assignment.hasErrors()
    }

	/**
	 * Checks that invalid course identifiers fail validation
	 */
	void testCourse() {
		def old = assignment.course
		shouldFail {
			assignment.course = new Assignment()
		}
		assignment.course = old
	}
	
	/**
	 * Checks that invalid codes fail validation
	 */
	void testCode() {
		def old = assignment.code
		
		assignment.code = ''
		assertFalse assignment.validate()
		assertTrue assignment.hasErrors()
		
		assignment.code = old
	}
}
