package uk.org.openmentor.data

import uk.org.openmentor.config.Grade

class Submission {
	
	/**
	 * The filename associated with this submission
	 */
	String filename
	
	/** 
	 * The grade associated with this submission
	 */
	Grade grade
	
	/**
	 * Optionally, and by default, stores the file binary contents
	 */
	byte[] fileContents
	
	/**
	 * The date of submission to OpenMentor, defaulting to now
	 */
	Date dateSubmitted = new Date()
	
	/**
	 * The username of the person who uploaded this submission
	 */
	String username
	
	static belongsTo = [ assignment: Assignment ]
	
	static hasMany = [ 
		comments: Comment, 
		studentIds: String,
		tutorIds: String
	]

    static constraints = {
		filename(nullable: false, blank: false)
		grade(nullable: false, blank: false)
		fileContents(nullable: true, maxSize: 8*1024*1024) // Allow up to 8Mb
		dateSubmitted(nullable: false)
		username(nullable: false, blank: false)
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

