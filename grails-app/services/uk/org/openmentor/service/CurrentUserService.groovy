package uk.org.openmentor.service

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.core.context.SecurityContextHolder

/**
 * A small service, primarily used to retrieve the current user name and other details as
 * needed. As a service, this can be wired into other services and controllers as required.
 * 
 * @author morungos
 */

class CurrentUserService {
	
	def springSecurityService

    static transactional = false

    /**
     * Returns the current user name, or null if none
     * @return the user name
     */
    def currentUserName() {
        def context = SecurityContextHolder.getContext()
        def authentication = context.getAuthentication()
        return authentication?.principal?.getUsername()
    }
	
	boolean isAdministrator() {
		def authorities = springSecurityService.getAuthentication()?.getAuthorities()
		def roles = SpringSecurityUtils.authoritiesToRoles(authorities)
		return roles.contains('ROLE_OPENMENTOR-ADMIN')
	}
}
