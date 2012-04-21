package uk.org.openmentor.domain

import static org.junit.Assert.*

import grails.test.mixin.support.*

/**
 * Tests the Category class - this provides static methods that expose the configuration of
 * the set of categories available in the current deployment. 
 * 
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CategoryTests {

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
	 * Checks that the list of categories matches the valid configuration
	 */
    void testCategories() {
        List<String> value = Category.getCategories()
		
		def expected = ['A1', 'A2', 'A3', 'B1', 'B2', 'B3', 'C1', 'C2', 'C3', 'D1', 'D2', 'D3']
		assertEquals expected, value
    }

	/**
	 * Checks that the list of categories matches the valid configuration
	 */
    void testBands() {
        List<String> value = Category.getBands()
		
		def expected = ['A', 'B', 'C', 'D']
		assertEquals expected, value
    }
	
	/**
	 * Check the association between categories and bands, but don't bother being
	 * too careful about it.
	 */
	void testCategoryBandMap() {
		Map<String, String> value = Category.getCategoryBandMap()
		
		assertEquals 'A', value.getAt('A1')
		assertEquals 'B', value.getAt('B2')
		assertEquals 'C', value.getAt('C3')
		assertEquals 'D', value.getAt('D1')
	}

	/**
	 * Check the association between categories and bands, but don't bother being
	 * too careful about it.
	 */
	void testGetBandCategories() {
		
		def expected = ['C1', 'C2', 'C3']
		assertEquals expected, Category.getBandCategories("C")
	}
}
