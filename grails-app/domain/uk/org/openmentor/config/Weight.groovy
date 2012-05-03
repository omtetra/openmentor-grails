package uk.org.openmentor.config

class Weight {
	
	Grade grade
	String band
	Float weight

    static constraints = {
		grade(nullable: false)
		band(nullable: false, maxSize: 2)
		weight(nullable: false)
    }

	static mapping = {
		grade column: 'grade', index: 'band_grade_index'
		band index: 'band_grade_index'
	}
}
