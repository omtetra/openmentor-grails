package uk.org.openmentor.controller

import grails.test.mixin.*
import org.junit.*

@TestFor(CourseController)
class CourseControllerTests {
	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testIndex() {
		controller.index()
		
		assert response.redirectedUrl == '/course/list'
	}
}
