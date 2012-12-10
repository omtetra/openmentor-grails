package uk.org.openmentor.courseinfo

class Tutor implements Comparable<Tutor>{
    
    String id
    String givenName
    String familyName

    static hasMany = [ courses: Course ]
    static belongsTo = Course

    static constraints = {
        id(nullable: false, blank: false, unique: true)
        givenName(blank: true)
        familyName(blank: true)
    }

    static mapping = {
        id generator:'assigned', column:'tutor_id'
    }

    static transients = ['name', 'idAndName']
    
    String getName() {
        return givenName + " " + familyName
    }

    String getIdAndName() {
        return id + " - " + givenName + " " + familyName
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
