package uk.org.openmentor.domain

import java.util.List;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

class Category {
	static List<String> getCategories() {
		return ConfigurationHolder.config.openmentor.categories as List<String>
	}

	static List<String> getBands() {
		return ConfigurationHolder.config.openmentor.bands as List<String>
	}

	static Map<String, String> getCategoryBandMap() {
		return ConfigurationHolder.config.openmentor.categoryBands as Map<String, String>
	}
	
	static List<String> getBandCategories(String band) {
		Map<String, String> map = getCategoryBandMap()
		List<String> categories = getCategories()
		return categories.findAll { map.getAt(it) == band }
	}
}
