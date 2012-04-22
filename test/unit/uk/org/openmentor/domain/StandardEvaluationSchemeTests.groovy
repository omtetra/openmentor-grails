package uk.org.openmentor.domain

import static org.junit.Assert.*

import grails.test.mixin.support.*

import uk.org.openmentor.evaluator.EvaluationScheme;

/**
 * Tests the standard evaluation scheme class - this provides values that are used to 
 * calculate ideal values from actual values broken down by grade. 
 * 
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class StandardEvaluationSchemeTests {
	
	/**
	 * The EvaluationScheme under test.
	 */
	EvaluationScheme evaluationScheme
	
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
	 * Checks that the evaluation scheme returns the expected value for 
	 * band "A" and grade "B". 
	 */
    void testEvaluationScheme() {
        evaluationScheme = new StandardEvaluationScheme()
		
		def value = evaluationScheme.getIdealProportion("A", "B")
		assertEquals 0.4, value, 0.001
    }
}
