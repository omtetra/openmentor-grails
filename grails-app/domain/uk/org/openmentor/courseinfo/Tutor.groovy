package uk.org.openmentor.courseinfo

class Tutor implements Comparable<Tutor>{
    
	String owner
    String tutorId
    String givenName
    String familyName

    static hasMany = [ courses: Course ]

    static constraints = {
		owner(nullable: true)
        tutorId(nullable: false, blank: false, unique: "owner")
        givenName(nullable: true)
        familyName(nullable: true)
    }

    static transients = ['name', 'idAndName']
    
    String getName() {
        return givenName + " " + familyName
    }

    String getIdAndName() {
        return tutorId + " - " + givenName + " " + familyName
    }

    int compareTo(Tutor other) {
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
