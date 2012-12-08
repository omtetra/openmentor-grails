package uk.org.openmentor.courseinfo

import grails.test.*

class StudentTests extends GrailsUnitTestCase {
	
	def student
	
    protected void setUp() {
        super.setUp()
		
		mockDomain(Student)
		student = new Student()

		student.studentId = '0000000'
		student.givenName = 'Morag'
		student.familyName = 'Mungo'
		
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Checks that the untouched student object passes validation
	 */
    void testValidation() {
		def result = student.validate()
		student.errors.allErrors.each { log.info it.toString() }
		assertTrue result
		assertFalse student.hasErrors()
    }

	/**
	 * Checks that invalid sample types fail validation
	 */
	void testStudentId() {
		def old = student.studentId
		
		student.studentId = ''
		assertFalse student.validate()
		assertTrue student.hasErrors()
		
		student.studentId = old
	}
	
	/**
	 * Must always be zero when comparing with self
	 */
	void testCompareSelf() {
		assertEquals(0, student.compareTo(student))
	}

	/**
	 * Checks that the untouched student object passes validation
	 */
    void testCompareOrdering() {
		def other = new Student()
		other.studentId = '00000001'
		other.givenName = 'Morag'
		other.familyName = 'Nunavut'
		
		assertEquals(-1, student.compareTo(other))
		assertEquals(1, other.compareTo(student))
    }

	/**
	 *  Compare with invalid/empty fields, always after
	 */
	void testCompareBlank() {
		def other = new Student()
		other.studentId = '00000001'
		
		assertEquals(-1, student.compareTo(other))
		assertEquals(1, other.compareTo(student))
	}

	/**
	 *  Compare with invalid/empty fields, always after
	 */
	void testCompareTwoBlank() {
		def other = new Student()
		other.studentId = '00000001'
		
		assertEquals(0, other.compareTo(other))
	}
}
