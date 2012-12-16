package uk.org.openmentor.courseinfo

class Course implements Comparable<Course> {
	
	String id
	String courseTitle
	
	SortedSet students
	SortedSet tutors
	
	static belongsTo = [ Student, Tutor ]
	static hasMany = [ students: Student, tutors: Tutor ]

    static constraints = {
		id(nullable: false, blank: false, unique: true)
		courseTitle(nullable: false)
    }
	
	static mapping = {
		id generator:'assigned', column:'course_id'
	}
	
	static transients = ['idAndTitle']
	
	String getIdAndTitle() {
		return id + " - " + courseTitle
	}

	int compareTo(Course other) {
		return id.compareTo(other.id)
	}
}
