package uk.org.openmentor.auth

class User {

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = { password column: '`password`' }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	Boolean hasRole(String role) {
		def criteria = UserRole.createCriteria()
		def found = criteria.get {
			role {
				eq('authority', role)
			}
		}
		return found != null
	}
}
