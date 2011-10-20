package uk.org.openmentor.courseinfo

class Student implements Comparable<Student>{
	
	String studentId
	String givenName
	String familyName
	
	static hasMany = [ courses: Course ]
	static belongsTo = Course
	
    static constraints = {
		studentId(nullable: false)
		givenName(blank: true)
		familyName(blank: true)
    }

	static mapping = {
		id generator:'assigned', name:'studentId'
	}
	
	static transients = ['name', 'idAndName']
	
	String getName() {
		return givenName + " " + familyName
	}

	String getIdAndName() {
		return studentId + " - " + givenName + " " + familyName
	}

	int compareTo(Student other) {
		int familyCompared = familyName.compareTo(other.familyName)
		return (familyCompared != 0) ? familyCompared : givenName.compareTo(other.givenName)
	}
}
