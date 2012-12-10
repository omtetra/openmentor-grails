package uk.org.openmentor.courseinfo

import grails.test.*

class TutorTests extends GrailsUnitTestCase {
	
	def tutor
	
    protected void setUp() {
        super.setUp()
		
		mockDomain(Tutor)
		tutor = new Tutor()

		tutor.id = '0000000'
		tutor.givenName = 'Morag'
		tutor.familyName = 'Mungo'

    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Checks that the untouched tutor object passes validation
	 */
    void testValidation() {
		def result = tutor.validate()
		tutor.errors.allErrors.each { log.info it.toString() }
		assertTrue result
		assertFalse tutor.hasErrors()
    }

	/**
	 * Checks that invalid sample types fail validation
	 */
	void testId() {
		def old = tutor.id
		
		tutor.id = ''
		assertFalse tutor.validate()
		assertTrue tutor.hasErrors()
		
		tutor.id = old
	}
	
	/**
	 * Must always be zero when comparing with self
	 */
	void testCompareSelf() {
		assertEquals(0, tutor.compareTo(tutor))
	}

	/**
	 * Checks that the untouched tutor object passes validation
	 */
    void testCompareOrdering() {
		def other = new Tutor()
		other.id = '00000001'
		other.givenName = 'Morag'
		other.familyName = 'Nunavut'
		
		assertEquals(-1, tutor.compareTo(other))
		assertEquals(1, other.compareTo(tutor))
    }

	/**
	 *  Compare with invalid/empty fields, always after
	 */
	void testCompareBlank() {
		def other = new Tutor()
		other.id = '00000001'
		
		assertEquals(-1, tutor.compareTo(other))
		assertEquals(1, other.compareTo(tutor))
	}

	/**
	 *  Compare with invalid/empty fields, always after
	 */
	void testCompareTwoBlank() {
		def other = new Tutor()
		other.id = '00000001'
		
		assertEquals(0, other.compareTo(other))
	}
}
