package uk.org.openmentor.service

import grails.test.GrailsUnitTestCase

class CategorizationInfoServiceTests extends GroovyTestCase {
	
	CategorizationInfoService categorizationInfoService

	/**
	 * Checks that the list of categories matches the valid configuration
	 */
    void testCategories() {
        List<String> value = categorizationInfoService.getCategories()
		
		def expected = ['A1', 'A2', 'A3', 'B1', 'B2', 'B3', 'C1', 'C2', 'C3', 'D1', 'D2', 'D3']
		assertEquals expected, value
    }

	/**
	 * Checks that the list of categories matches the valid configuration
	 */
    void testBands() {
        List<String> value = categorizationInfoService.getBands()
		
		def expected = ['A', 'B', 'C', 'D']
		assertEquals expected, value
    }
	
	/**
	 * Check the association between categories and bands, but don't bother being
	 * too careful about it.
	 */
	void testCategoryBandMap() {
		Map<String, String> value = categorizationInfoService.getCategoryBandMap()
		
		assertEquals 'A', value.getAt('A1')
		assertEquals 'B', value.getAt('B2')
		assertEquals 'C', value.getAt('C3')
		assertEquals 'D', value.getAt('D1')
	}

	/**
	 * Check the default band labels, but don't bother being
	 * too careful about it.
	 */
	void testBandLabels() {
		Map<String, String> value = categorizationInfoService.getBandLabels()
		
		assertEquals 'Positive comments', value.getAt('A')
		assertEquals 'Teaching points', value.getAt('B')
		assertEquals 'Questions', value.getAt('C')
		assertEquals 'Negative comments', value.getAt('D')
	}

	/**
	 * Check the association between categories and bands, but don't bother being
	 * too careful about it.
	 */
	void testGetBandCategories() {
		
		def expected = ['C1', 'C2', 'C3']
		assertEquals expected, categorizationInfoService.getBandCategories("C")
	}

	/**
	 * Checks that the list of grades matches the valid configuration
	 */
    void testGrades() {
        List<String> value = categorizationInfoService.getGrades()
		
		def expected = ["Pass 1", "Pass 2", "Pass 3", "Pass 4", "Bare fail", "Fail"]
		assertEquals expected, value
    }
}
