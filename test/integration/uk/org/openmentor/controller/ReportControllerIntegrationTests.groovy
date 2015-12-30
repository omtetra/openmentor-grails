package uk.org.openmentor.controller

import java.util.Map;

import grails.test.*
import grails.test.mixin.TestMixin;
import grails.test.mixin.integration.Integration;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils

import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.data.Submission
import uk.org.openmentor.service.CurrentUserService;

import grails.test.spock.IntegrationSpec

class ReportControllerIntegrationTests extends IntegrationSpec {
	
	static transactional = true
	
	/* Test that the index page redirects when we don't have a selected
	 * course
	 */
	void testIndexRedirect() {
		given: 'ReportController'
        def controller = new ReportController()
		
		when: 'index is called'
        controller.index()
        
        then: 'check redirect url'
        controller.response.redirectUrl == "/course/select"
	}

	/**
	 * Test that the index page doesn't redirect when we do have a selected
	 * course
	 */
	void testIndexNoRedirect() {
		given: 'ReportController'
        def controller = new ReportController()
        
        and: 'with current_course set to CM2006'
        controller.session.putAt('current_course', 'CM2006')
		
		when: 'index is called'
        def result = controller.index()
        
        then: 'check redirect url is null and course exists'
        controller.response.redirectUrl == null
        result?.course != null
	}

	/**
	 * Test the report when nothing has been uploaded. Regression test for #18
	 */
	void testReportEmptyCourse() {

		given: 'ReportController'
        def controller = new ReportController()
        
        and: 'with current_course set to AA1003'
        controller.session.putAt('current_course', 'AA1003')
		
		when: 'course is called'
        def model = controller.course()
        
        then: 'model is not null'
        model != null
	}

}
