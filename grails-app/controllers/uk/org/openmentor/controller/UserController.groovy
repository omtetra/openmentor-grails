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
	def create = { }
	
	@Secured(['ROLE_OPENMENTOR-ADMIN'])
	def save = { UserCommand cmd ->
		
		def model = [:]
		model.cmd = cmd

		if (cmd.hasErrors()) {
			render(view: "create", model: model)
			return
		}
		
		log.error("Ready to save and start processing")
		User user = new User()
		user.username = cmd.username
		user.password = springSecurityService.encodePassword(cmd.password)
		user.enabled = true
		
		if (user.validate() && user.save(flush: true)) {
			
			def userRole = Role.findByAuthority('ROLE_OPENMENTOR-USER')
			assert userRole
			UserRole.create user, userRole
			
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
            [userInstance: userInstance]
        }
	}
	
	@Secured(['ROLE_OPENMENTOR-ADMIN'])
	def edit = {
		
		log.trace("Request user/edit: " + params.id)
		
		def username = params.id
		
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
	def password = { 
		def username = params.id
		def currentUser = springSecurityService.currentUser		
		if (! isAdministrator() && username != currentUser.username) {
			flash.message = "${message(code: 'user.invalid', default: 'Invalid user access for {0}', args: [username])}"
            redirect(action: "show", id: currentUser.username)
		}
		
		def userInstance = User.findByUsername(username)
		[userInstance: userInstance]
	}
	
	@Secured(['ROLE_OPENMENTOR-USER'])
	def update = { UserCommand cmd ->
		def username = cmd.username
		def currentUser = springSecurityService.currentUser
		if (! isAdministrator() && username != currentUser.username) {
			flash.message = "${message(code: 'user.invalid', default: 'Invalid user access for {0}', args: [username])}"
            redirect(action: "show", id: currentUser.username)
		}
		
		def userInstance = User.findByUsername(cmd.username)
		def model = [userInstance: userInstance, cmd: cmd]

		if (cmd.hasErrors()) {
			render(view: "password", model: model)
			return
		}
		
		userInstance.password = springSecurityService.encodePassword(cmd.password)
		if (userInstance.validate() && userInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User')])}"
			redirect(action: "show", id: username)
		} else {
			user.errors.each {
				log.error(it)
			}
			render(view: "password", model: model)
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