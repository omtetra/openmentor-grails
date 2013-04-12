package uk.org.openmentor.service

import java.util.List;
import java.util.Map;

class CategorizationInfoService {
	
	/**
	 * Injects the application to access configuration
	 */
	def grailsApplication

	/**
	 * Not transactional, as no DB access
	 */
	static transactional = false
	
	/**
	 * Returns a list of grades, as strings
	 * @return a list of strings
	 */
    List<String> getGrades() {
		return grailsApplication.getConfig().openmentor.grades as List<String>
	}
	
	/**
	 * Returns the list of categories, as strings
	 * @return a list of strings
	 */
	List<String> getCategories() {
		return grailsApplication.getConfig().openmentor.categories as List<String>
	}

	/**
	 * Returns the list of bands, as strings
	 * @return a list of strings
	 */
	List<String> getBands() {
		return grailsApplication.getConfig().openmentor.bands as List<String>
	}

	/**
	 * Returns the map associating bands with more detailed labels
	 * @return a mapping from strings to strings
	 */
	Map<String, String> getBandLabels() {
		return grailsApplication.getConfig().openmentor.bandLabels as Map<String, String>
	}

	/**
	 * Returns the map associating categories with bands
	 * @return a mapping from strings to strings
	 */
	Map<String, String> getCategoryBandMap() {
		return grailsApplication.getConfig().openmentor.categoryBands as Map<String, String>
	}

	/**
	 * For a given band, returns a list of the categories it contains	
	 * @param band
	 * @return a list of categories
	 */
	List<String> getBandCategories(String band) {
		Map<String, String> map = getCategoryBandMap()
		List<String> categories = getCategories()
		return categories.findAll { map.getAt(it) == band }
	}
}
