package uk.org.openmentor.data

class Comment {
	
	String text
	
	static belongsTo = [ submission: Submission ]

	static hasMany = [ classes: String ]

    static constraints = {
		text(nullable: false)
    }
}
