package uk.org.openmentor.data

class Comment {
	
	/**
	 * The comment text.
	 */
	String text
	
	/**
	 * A comment belongs to a single submission.
	 */
	static belongsTo = [ submission: Submission ]

	/**
	 * A comment may be associated with several strings, which are comment categories.
	 * We don't use comment numeric identifiers, as that is less obvious in the 
	 * database, and is harder to respond to a changing coding scheme. 
	 */
	static hasMany = [ classes: String ]

    static constraints = {
		text(nullable: false, maxSize: 4*1024)
    }
}
