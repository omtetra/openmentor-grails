package uk.org.openmentor.courseinfo

class Tutor implements Comparable<Tutor>{
	
	String tutorId
	String givenName
	String familyName

    static constraints = {
		tutorId(nullable: false)
		givenName(blank: true)
		familyName(blank: true)
    }

	static mapping = {
		id generator:'assigned', name:'tutorId'
	}

	static transients = ['name', 'idAndName']
	
	String getName() {
		return givenName + " " + familyName
	}

	String getIdAndName() {
		return tutorId + " - " + givenName + " " + familyName
	}

	int compareTo(Tutor other) {
		int familyCompared = familyName.compareTo(other.familyName)
		return (familyCompared != 0) ? familyCompared : givenName.compareTo(other.givenName)
	}
}
