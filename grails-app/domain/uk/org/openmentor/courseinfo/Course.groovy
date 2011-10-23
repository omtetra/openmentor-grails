package uk.org.openmentor.courseinfo

class Course implements Comparable<Course> {
	
	String courseId
	String courseTitle
	
	SortedSet students
	SortedSet tutors
	
	static hasMany = [ students: Student, tutors: Tutor ]

    static constraints = {
		courseId(nullable: false, unique: true)
		courseTitle(nullable: false)
    }
	
	static mapping = {
		id generator:'assigned', name:'courseId'
	}
	
	static transients = ['idAndTitle']
	
	String getIdAndTitle() {
		return courseId + " - " + courseTitle
	}

	int compareTo(Course other) {
		return courseId.compareTo(other.courseId)
	}
}
