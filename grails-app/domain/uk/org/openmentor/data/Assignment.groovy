package uk.org.openmentor.data

class Assignment {
	
	String courseId
	String code
	String title

	static hasMany = [ submission: Submission ]
	
    static constraints = {
		courseId(nullable: false, blank: false)
		code(nullable: false, blank: false)
		title(nullable: true)
    }
}
