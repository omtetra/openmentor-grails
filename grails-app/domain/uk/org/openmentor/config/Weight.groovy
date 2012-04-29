package uk.org.openmentor.config

class Weight {
	
	String grade
	String band
	Float weight

    static constraints = {
		grade(nullable: false, unique: false, blank: false, maxSize: 2)
		band(nullable: false, unique: false, blank: false, maxSize: 2)
		weight(nullable: false, unique: false)
    }

	static mapping = {
		grade index: 'band_grade_index'
		band index: 'band_grade_index'
	}
}
