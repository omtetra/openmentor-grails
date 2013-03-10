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
	
	List<String> getCategories() {
		return grailsApplication.getConfig().openmentor.categories as List<String>
	}

	List<String> getBands() {
		return grailsApplication.getConfig().openmentor.bands as List<String>
	}

	Map<String, String> getCategoryBandMap() {
		return grailsApplication.getConfig().openmentor.categoryBands as Map<String, String>
	}
	
	List<String> getBandCategories(String band) {
		Map<String, String> map = getCategoryBandMap()
		List<String> categories = getCategories()
		return categories.findAll { map.getAt(it) == band }
	}
}
