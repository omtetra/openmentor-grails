package uk.org.openmentor.domain

import static org.junit.Assert.*

import grails.test.mixin.support.*

/**
 * Tests the Categorization class - this provides methods that enable categorization of
 * a set of comments into the different categories. These are the basis for the assessments
 * provided by OpenMentor. 
 * 
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CategorizationTests {

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
	 * Tests the constructor for the Categorization class
	 */
    void testConstructor() {
        Categorization value = new Categorization()
		List<String> categories = Category.getCategories()
		
		for(category in categories) {
			assertEquals 0, value.getCommentCount(category)
		}
    }
}
