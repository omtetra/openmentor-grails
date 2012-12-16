package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(CourseController)
class CourseControllerTests {

	void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/course/list'
	}
}
