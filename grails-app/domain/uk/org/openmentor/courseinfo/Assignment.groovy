package uk.org.openmentor.courseinfo

import uk.org.openmentor.data.Submission;

class Assignment {
	
	String owner
	String code
	String title

	static hasMany = [ submissions: Submission ]
	static belongsTo = [ course: Course ]
	
    static constraints = {
		owner(nullable: true)
		code(nullable: false, blank: false)
		title(nullable: true, blank: true)
    }
}
