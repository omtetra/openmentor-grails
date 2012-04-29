package uk.org.openmentor.config

class Category {
	
	String band
	String category

    static constraints = {
		band(nullable: false, unique: false, blank: false, maxSize: 2)
		category(nullable: false, unique: true, blank: false, maxSize: 8)
    }
	
	static mapping = {
		band index: 'band_index'
		category index: 'category_index'
	}
}
