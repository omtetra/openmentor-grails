package uk.org.openmentor.auth

class User {

	String username
	String password
	String confirm
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username(nullable: false, blank: false, unique: true, validator: { val, obj ->
			if (val == null || val.length() < 3) {
				return ['user.username.blankortooshort']
			}
		})
		password(nullable: false, blank: false, validator: { val, obj ->
			if (val == null || val.length() < 8) {
				return ['user.password.blankortooshort']
			}
		})
		confirm(nullable: false, validator: { val, obj ->
			if (val == null || obj.properties['password'] == null || obj.properties['password'] != val) {
				return ['user.password.confirm.mismatch']
			}
		})
	}
		
	static mapping = { password column: '`password`' }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	void removeRole(Role role) {
		UserRole.findByUserAndRole(this, role).delete()
	}
	
	void addRole(Role role) {
		UserRole.create(this,  role)
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
