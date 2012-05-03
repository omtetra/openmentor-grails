package uk.org.openmentor.controller

import grails.test.*
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils

import uk.org.openmentor.config.Grade;
import uk.org.openmentor.data.Assignment
import uk.org.openmentor.data.Submission
import uk.org.openmentor.domain.Categorization;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.service.CurrentUserService;
import uk.org.openmentor.service.SummarizationService

import org.gmock.WithGMock

@WithGMock
class SubmissionControllerIntegrationTests extends GroovyTestCase {
	
	private def controller
	private def summarizationService
	
	Map savedMetaClasses = [:]
	Map renderMap
	Map redirectMap
	
    protected void setUp() {
        super.setUp()
		
		registerMetaClass(SubmissionController.class)
		SubmissionController.metaClass.render = {Map m ->
			renderMap = m
		}
		SubmissionController.metaClass.redirect = {Map m ->
			redirectMap = m
		}
		
		controller = new SubmissionController()
		
		summarizationService = new SummarizationService()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	private def getMockFile(String fileName) {
		def mockFile = mock(MultipartFile)
		mockFile.getOriginalFilename().returns(fileName).stub()
		mockFile.getBytes().returns(IOUtils.toByteArray(new FileInputStream(fileName))).stub()
		mockFile.getContentType().returns("application/msword").stub()
		
		return mockFile
	}

    void testSubmission() {
		def mockFile = getMockFile("test/resources/test1a.doc")
		
		def sc = new SubmissionCommand()
		
		Assignment ass = Assignment.findByCode("TMA03")
		
		def mockCurrentUserService = mock(CurrentUserService)
		mockCurrentUserService.currentUserName().returns("admin").stub()		
		controller.currentUserService = mockCurrentUserService
		
		controller.session.putAt('current_course', 'CM2006')
		play {
			sc.dataFile = mockFile
			sc.grade = "A"
			sc.courseId = 'CM2006'
			sc.studentIds = '09000231'
			sc.tutorIds = 'M4000061'
			sc.assignmentId = ass.id
		
			controller.save(sc)
		}

		assertNull(renderMap)
		assertNotNull(redirectMap)
		
		def identifier = redirectMap.id
		Submission submissionInstance = Submission.get(identifier)
		
		assertNotNull(submissionInstance)
		
		Summary summary = summarizationService.getSubmissionSummary(submissionInstance, true)
		assertNotNull summary
		
		List<String> comments = summary.data.getAt("B").comments
		assertNotNull comments.find { it.contains("Not a word wasted here!") }
    }
	
	// Stolen from GrailsUnitTestCase
	/**
	 * Use this method when you plan to perform some meta-programming
	 * on a class. It ensures that any modifications you make will be
	 * cleared at the end of the test.
	 * @param clazz The class to register.
	 */
	protected void registerMetaClass(Class clazz) {
		// If the class has already been registered, then there's
		// nothing to do.
		if (savedMetaClasses.containsKey(clazz)) return

		// Save the class's current meta class.
		savedMetaClasses[clazz] = clazz.metaClass

		// Create a new EMC for the class and attach it.
		def emc = new ExpandoMetaClass(clazz, true, true)
		emc.initialize()
		GroovySystem.metaClassRegistry.setMetaClass(clazz, emc)
	}

}
