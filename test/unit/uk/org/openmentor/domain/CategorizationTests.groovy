package uk.org.openmentor.domain

import uk.org.openmentor.config.Grade
import uk.org.openmentor.config.Category

import grails.test.GrailsUnitTestCase;

/**
 * Tests the Categorization class - this provides methods that enable categorization of
 * a set of comments into the different categories. These are the basis for the assessments
 * provided by OpenMentor. 
 */

class CategorizationTests extends GrailsUnitTestCase {

	/**
	 * Sets up the tests
	 */
    void setUp() {
		super.setUp()
        mockDomain(Grade)
		mockDomain(Category)
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
		List<Category> categories = Category.getCategories()
		
		for(category in categories) {
			assertEquals 0, value.getCommentCount(category)
		}
    }
}
