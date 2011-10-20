package uk.org.openmentor.web

import grails.test.*

class UrlMappingTests extends GrailsUrlMappingsTestCase {
	
    void testStudentIdWithLeadingZeros() {
		assertUrlMapping("/student/show/00320000", controller: "student", action: "show") {
			id = "00320000"
		}
    }
}
