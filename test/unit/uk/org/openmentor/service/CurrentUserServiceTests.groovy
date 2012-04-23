package uk.org.openmentor.service

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CurrentUserService)
class CurrentUserServiceTests {
	
    void testCurrentUserName() {
        def currentUserService = new CurrentUserService()
		
		def value = currentUserService.currentUserName()
		assertEquals null, value
    }
}
