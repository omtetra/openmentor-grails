package uk.org.openmentor.controller

import grails.test.*
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils
import uk.org.openmentor.data.Assignment
import uk.org.openmentor.data.Submission
import uk.org.openmentor.domain.Categorization;
import uk.org.openmentor.service.CurrentUserService;

import org.gmock.WithGMock

@WithGMock
class ReportControllerIntegrationTests extends GroovyTestCase {
	
	private def controller
	
    protected void setUp() {
        super.setUp()
		
		controller = new SubmissionController()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	
	/**
	 * Test the report when nothing has been uploaded. Regression test for #18
	 */
	void testReportEmptyCourse() {
		def controller = new ReportController()
		controller.session.putAt('current_course', 'AA1003')
		def model = controller.course()
		
		assertTrue model != null
	}
}
