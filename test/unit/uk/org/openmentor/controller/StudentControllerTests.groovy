package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(StudentController)
class StudentControllerTests {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/student/list'
    }
}
