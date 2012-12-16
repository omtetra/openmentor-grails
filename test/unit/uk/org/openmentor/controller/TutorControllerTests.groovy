package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(TutorController)
class TutorControllerTests {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/tutor/list'
    }
}
