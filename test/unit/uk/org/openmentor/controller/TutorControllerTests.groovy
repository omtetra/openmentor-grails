package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(TutorController)
class TutorControllerTests {

    void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/tutor/list'
    }
}
