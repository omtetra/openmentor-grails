package uk.org.openmentor.service

import grails.test.mixin.*
import org.junit.*
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;
import uk.org.openmentor.courseinfo.Assignment
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.courseinfo.Tutor

class CurrentUserServiceTests extends GroovyTestCase {
	
	def currentUserService = new CurrentUserService()
	
	/**
	 * Check the current user name returned from the service
	 */
	void testCurrentUserName() {
		
		SpringSecurityUtils.doWithAuth("user") {
			assertEquals("user", currentUserService.currentUserName())
		}

		SpringSecurityUtils.doWithAuth("admin") {
			assertEquals("admin", currentUserService.currentUserName())
		}
	}

	/**
	 * Check the current user name returned from the service
	 */
	void testIsAdministrator() {
		
		SpringSecurityUtils.doWithAuth("user") {
			assertEquals(false, currentUserService.isAdministrator())
		}

		SpringSecurityUtils.doWithAuth("admin") {
			assertEquals(true, currentUserService.isAdministrator())
		}
	}

}