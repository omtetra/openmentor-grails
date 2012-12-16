package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(StudentController)
class StudentControllerTests {

    void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/student/list'
    }
}
