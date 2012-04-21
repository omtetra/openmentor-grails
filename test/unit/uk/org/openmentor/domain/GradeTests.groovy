package uk.org.openmentor.domain

import static org.junit.Assert.*

import grails.test.mixin.support.*

/**
 * Tests the Grade class - this provides static methods that expose the configuration of
 * the set of grades available in the current deployment. 
 * 
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class GradeTests {

	/**
	 * Sets up the tests
	 */
    void setUp() {
        // Setup logic here
    }

	/**
	 * Tears down the tests
	 */
    void tearDown() {
        // Tear down logic here
    }

	/**
	 * Checks that the list of grades matches the valid configuration
	 */
    void testGrades() {
        List<String> value = Grade.getGrades()
		
		def expected = ['A', 'B', 'C', 'D', 'E', 'F']
		assertEquals expected, value
    }
}
