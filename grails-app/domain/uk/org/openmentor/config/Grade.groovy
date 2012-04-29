package uk.org.openmentor.config

class Grade {
	
	String grade

    static constraints = {
		grade(nullable: false, blank: false, unique: true, maxSize: 2)
    }
}
