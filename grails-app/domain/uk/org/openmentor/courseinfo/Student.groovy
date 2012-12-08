package uk.org.openmentor.courseinfo

class Student implements Comparable<Student>{
    
    String studentId
    String givenName
    String familyName
    
    static hasMany = [ courses: Course ]
    static belongsTo = Course
    
    static constraints = {
        studentId(nullable: false, blank:false, unique: true)
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
		int result = 0
		
		if (familyName != other.familyName) {
			if (familyName == null) {
				result = 1
			} else if (other.familyName == null) {
				result = -1
			} else {
				result = familyName.compareTo(other.familyName)
			}
		}
		
		if (result == 0 && givenName != other.givenName) {
			if (givenName == null) {
				result = 1
			} else if (other.givenName == null) {
				result = -1
			} else {
				result = givenName.compareTo(other.givenName)
			}
		}
			
		return result
    }
}
