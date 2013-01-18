package uk.org.openmentor.controller

import grails.plugins.springsecurity.Secured;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;
import org.springframework.web.multipart.MultipartFile;

import uk.org.openmentor.auth.Role;
import uk.org.openmentor.auth.User;
import uk.org.openmentor.auth.UserRole;

@Secured(['ROLE_OPENMENTOR-ADMIN'])
class UserController {
	
	def springSecurityService

	@Secured(['ROLE_OPENMENTOR-ADMIN'])
    def index = { 
		redirect(action: "list", params: params)
	}

	@Secured(['ROLE_OPENMENTOR-ADMIN'])
    def list = { 
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'username'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = User.createCriteria()
		
		def userList = criteria.list {
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
		
		def userCount = User.count()
		
		[userInstanceList: userList, userInstanceTotal: userCount]
	}

	@Secured(['ROLE_OPENMENTOR-ADMIN'])
	def create = { 
		def allPossibleRoles = Role.getAll().collect { it.authority }.sort() as List<String>		
		[availableRoles: allPossibleRoles]
	}
	
	@Secured(['ROLE_OPENMENTOR-ADMIN'])
	def set_password = { 
		def userInstance = User.findByUsername(params.id)
		[userInstance: userInstance]
	}
	
	@Secured(['ROLE_OPENMENTOR-ADMIN'])
	def save = { 
		
		def allPossibleRoles = Role.getAll().collect { it.authority }.sort() as List<String>		

		log.error("Ready to save and start processing")
		
		User user = new User()
		user.username = params.username
		user.password = params.password
		user.confirm = params.confirm
		if (params.password == null || params.password.length() < 8) {
			user.errors.rejectValue('password', 'user.password.blankortooshort')
		} else {
			user.password = springSecurityService.encodePassword(params.password)
		}
		if (params.confirm == null || params.confirm.length() < 8) {
			user.errors.rejectValue('confirm', 'user.password.blankortooshort')
		} else {
			user.confirm = springSecurityService.encodePassword(params.confirm)
		}
		
		user.enabled = true
		user.validate()
		
		// Validation can't be delegated to the domain object, as the confirm is a transient
		// and we can't do it in a command, because roles are open-ended. Also, the value
		// stored is actually encoded. Fortunately, we can signal errors on transient fields,
		// even if we can't validate them.
		
		def model = [userInstance: user, availableRoles: allPossibleRoles]

		if (params.confirm == null) {
			user.errors.rejectValue('confirm', 'user.password.blankortooshort')
		} else if (params.password != params.confirm) {
			user.errors.rejectValue('confirm', 'user.password.confirm.mismatch')
		}
		
		if (user.hasErrors()) {
			render(view: "create", model: model)
			return
		}
		
		// Roles need to be handled here. They are harder, as we don't actually have them
		// wired into the code anywhere. 
		
		if (user.validate() && user.save(flush: true)) {
			
			Set<Role> newRoles = [] as List<Role>
			for(String propertyKey in params.keySet().toList()) {
				if (propertyKey.startsWith("role_")) {
					def roleName = params.get(propertyKey)
					def role = Role.findByAuthority(roleName)
					assert role != null
					user.addRole(role)
				}
			}
			
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User')])}"
			redirect(action: "list")
		} else {
			user.errors.each {
				log.error(it)
			}
			render(view: "create", model: model)
		}
	}
	
	private boolean isAdministrator() {
		def authorities = springSecurityService.getAuthentication().getAuthorities()
		def roles = SpringSecurityUtils.authoritiesToRoles(authorities)
		return roles.contains('ROLE_OPENMENTOR-ADMIN')

	}

	@Secured(['ROLE_OPENMENTOR-USER'])
	def show = {
		def allPossibleRoles = Role.getAll().collect { it.authority }.sort() as List<String>		
		def username = params.id
		def currentUser = springSecurityService.currentUser		
		if (! isAdministrator() && username != currentUser.username) {
			flash.message = "${message(code: 'user.invalid', default: 'Invalid user access for {0}', args: [username])}"
            redirect(action: "list")
		}
		
		def userInstance = User.findByUsername(username)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
        else {
			def allRoles = userInstance ? userInstance.getAuthorities().collect { it.authority } : [] as Set<String>
            [userInstance: userInstance, userRoles: allRoles, availableRoles: allPossibleRoles]
        }
	}
	
	@Secured(['ROLE_OPENMENTOR-USER'])
	def edit = {
		
		log.trace("Request user/edit: " + params.id)
		
		def username = params.id
		def currentUser = springSecurityService.currentUser
		if (! isAdministrator() && username != currentUser.username) {
			flash.message = "${message(code: 'user.invalid', default: 'Invalid user access for {0}', args: [username])}"
			redirect(action: "show", id: currentUser.username)
		}

		def userInstance = User.findByUsername(username)
		def allRoles = userInstance ? userInstance.getAuthorities().collect { it.authority } : [] as Set<String>
		def allPossibleRoles = Role.getAll().collect { it.authority }.sort() as List<String>
		
		log.trace("Found user: " + userInstance)
		log.trace("Found roles: " + allRoles)
		log.trace("Found possible roles: " + allPossibleRoles)
		
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance, userRoles: allRoles, availableRoles: allPossibleRoles]
		}
	}

	@Secured(['ROLE_OPENMENTOR-USER'])
	def update = { 
		
		def username = params.username
		def currentUser = springSecurityService.currentUser
		if (! isAdministrator() && username != currentUser.username) {
			flash.message = "${message(code: 'user.invalid', default: 'Invalid user access for {0}', args: [username])}"
            redirect(action: "show", id: currentUser.username)
		}
		
		def userInstance = User.get(params.id)
		
		def allRoles = userInstance ? userInstance.getAuthorities().collect { it.authority } : [] as Set<String>
		def allPossibleRoles = Role.getAll().collect { it.authority }.sort() as List<String>

		def model = [userInstance: userInstance, userRoles: allRoles, availableRoles: allPossibleRoles]
		
		userInstance.username = params.username
		
		// If the confirmation and the password are both set, and match, then we can write
		// both into the user instance. 

		if (params.password || params.confirm) {
			if (! params.confirm) {
				userInstance.errors.rejectValue('confirm', 'user.password.blankortooshort')
			} else if (params.password != params.confirm) {
				userInstance.errors.rejectValue('confirm', 'user.password.confirm.mismatch')
			} else {
				userInstance.password = springSecurityService.encodePassword(params.password)
				userInstance.confirm = springSecurityService.encodePassword(params.confirm)
			}
		}
		
		if (userInstance.hasErrors()) {
			render(view: "edit", model: model)
			return
		}

		//		userInstance.password = springSecurityService.encodePassword(cmd.password)

		if (userInstance.validate() && userInstance.save(flush: true)) {
			
			// Only change roles if we're an administrator
			if (isAdministrator()) {
				Set<Role> newRoles = [] as List<Role>
				for(String propertyKey in params.keySet().toList()) {
					if (propertyKey.startsWith("role_")) {
						def roleName = params.get(propertyKey)
						def role = Role.findByAuthority(roleName)
						assert role != null
						newRoles.add(role)
					}
				}
				
				Set<Role> rolesToAdd = newRoles.minus(userInstance.getAuthorities())
				Set<Role> rolesToRemove = userInstance.getAuthorities().minus(newRoles)
				for(Role role in rolesToRemove) {
					userInstance.removeRole(role)
				}
				for(Role role in rolesToAdd) {
					userInstance.addRole(role)
				}
				

				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User')])}"
				redirect(action: "list")
			} else {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User')])}"
				redirect(action: "show", id: userInstance.username)
			}
				
		} else {
			userInstance.errors.each {
				log.error(it)
			}
			render(view: "edit", model: model)
		}
	}
}

final class UserCommand {
	String username
	String password
	String confirm 
	
	static constraints = {
		username(nullable: false, validator: { val, obj ->
			if (val == null || val.length() < 3) {
				return ['user.username.blankortooshort']
			}
		})
		password(nullable: false, validator: { val, obj ->
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
}