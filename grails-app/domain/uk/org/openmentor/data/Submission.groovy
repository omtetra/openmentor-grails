package uk.org.openmentor.data

class Submission {
	
	/**
	 * The filename associated with this submission
	 */
	String filename
	
	/** 
	 * The grade associated with this submission
	 */
	String grade
	
	/**
	 * Optionally, and by default, stores the file binary contents
	 */
	byte[] fileContents
	
	/**
	 * The date of submission to OpenMentor, defaulting to now
	 */
	Date dateSubmitted = new Date()
	
	static belongsTo = [ assignment: Assignment ]
	
	static hasMany = [ 
		comments: Comment, 
		studentIds: String,
		tutorIds: String
	]

    static constraints = {
		filename(nullable: false)
		grade(nullable: false)
		fileContents(nullable: true, maxSize: 8*1024*1024) // Allow up to 8Mb
		dateSubmitted(nullable: false)
    }
	
	static Set<Submission> findAllCourseSubmissions(String courseId) {
		def submissionList = Submission.withCriteria {
			assignment {
				eq('courseId', courseId)
			}
		}
		return submissionList
	}
}

