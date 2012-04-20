package uk.org.openmentor.data

class Assignment {
	
	String courseId
	String code
	String title

	static hasMany = [ submissions: Submission ]
	
    static constraints = {
		courseId(nullable: false, blank: false)
		code(nullable: false, blank: false, unique: 'courseId')
		title(nullable: true, blank: true)
    }
}
