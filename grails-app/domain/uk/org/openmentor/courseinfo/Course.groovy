package uk.org.openmentor.courseinfo

class Course implements Comparable<Course> {
	
	String owner
	String courseId
	String courseTitle
	
	SortedSet students
	SortedSet tutors
	
	static belongsTo = [ Student, Tutor ]
	static hasMany = [ students: Student, tutors: Tutor, assignments: Assignment ]

    static constraints = {
		owner(nullable: true)
		courseId(nullable: false, blank: false, unique: "owner")
		courseTitle(nullable: false)
    }
	
	static transients = ['idAndTitle']
	
	String getIdAndTitle() {
		return courseId + " - " + courseTitle
	}

	int compareTo(Course other) {
		return courseId.compareTo(other.courseId)
	}
}
