package uk.org.openmentor.config

import java.util.List;

class Category {
	
	String id
	String band

    static constraints = {
		id(nullable: false, unique: true, blank: false, maxSize: 8)
		band(nullable: false, unique: false, blank: false, maxSize: 2)
    }
	
	static mapping = {
		id column: 'category', generator: 'assigned'
		band index: 'band_index'
	}

	static List<Category> getCategories() {
		return Category.findAll()
	}
	
	static List<String> getBands() {
		return getCategories().collect { it.band }.sort().unique()
	}
}
