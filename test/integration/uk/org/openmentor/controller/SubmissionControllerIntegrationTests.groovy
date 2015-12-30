package uk.org.openmentor.controller

import grails.test.*
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils

import grails.plugin.springsecurity.SpringSecurityUtils;

import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.data.Submission
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.service.CurrentUserService;
import uk.org.openmentor.service.SummarizationService

import grails.test.spock.IntegrationSpec

class SubmissionControllerIntegrationTests extends IntegrationSpec {
	
	static transactional = true
	
	private def summarizationService
	
	// Public so it can be injected
	def courseInfoService
	def currentUserService
	def categorizationInfoService
	def summarizationService
	
	
	/**
	 * Test that the upload page redirects when we don't have a selected
	 * course
	 */
	void testUploadRedirect() {
		given: 'SubmissionController'
        def controller = new SubmissionController()
        
        when: 'upload is called'
		controller.upload()
		
		then: 'check redirect url'
        controller.response.redirectUrl == '/course/select'
	}

	/**
	 * Test that the upload page doesn't redirect when we do have a selected
	 * course
	 */
	void testUploadNoRedirect() {
		given: 'SubmissionController'
        def controller = new SubmissionController()
	
		and: 'current_course is CM2006'	
		controller.session.putAt('current_course', 'CM2006')
		
        when: 'upload is called'
		def model = controller.upload()
		
		then: 'model is correct'
		model?.courseInstance != null
	}

    void testSubmissionSave() {
		given: 'SubmissionController'
        def controller = new SubmissionController()
        controller.courseInfoService = courseInfoService
        controller.categorizationInfoService = categorizationInfoService

		and: 'given a submission with uploaded file'
		def mockFile = Mock(MultipartFile)
		mockFile.getOriginalFilename() >> "test/resources/test1a.doc"
		mockFile.getBytes() >> IOUtils.toByteArray(new FileInputStream("test/resources/test1a.doc"))
		mockFile.getContentType() >> "application/msword"
				
		def ass = courseInfoService.findAssignment("CM2006", "TMA03")
		
		controller.params.dataFile = mockFile
		controller.params.grade = "Pass 1"
		controller.params.courseId = 'CM2006'
		controller.params.studentIds = '09000231'
		controller.params.tutorIds = 'M4000061'
		controller.params.assignmentId = ass.id

		controller.session.putAt('current_course', 'CM2006')

        when: 'save is called'
		SpringSecurityUtils.doWithAuth("admin") {
			def result = controller.save()
		}
		
		then: 'redirect to list and the user has been renamed'
		def group = (controller.response.redirectUrl =~ /^\/submission\/show\/(\d+)$/)
		group.hasGroup()
		1 == group.size()
		def identifier = group[0][1]
		
		Submission submissionInstance = Submission.get(identifier)
		null != submissionInstance
		
		"test1a.doc" == submissionInstance.filename
		"test/resources/test1a.doc" == submissionInstance.longFilename
		
		Summary summary = summarizationService.getSubmissionSummary(submissionInstance, true)
		null != summary
		
		Set<String> comments = summary.data.getAt("B").comments
		null != comments.find { it.contains("Not a word wasted here!") }
    }
	
    void testSubmissionSaveDOCX() {
		given: 'SubmissionController'
        def controller = new SubmissionController()
        controller.courseInfoService = courseInfoService
        controller.categorizationInfoService = categorizationInfoService

		and: 'given a submission with uploaded file'
		def mockFile = Mock(MultipartFile)
		mockFile.getOriginalFilename() >> "test/resources/test3a.docx"
		mockFile.getBytes() >> IOUtils.toByteArray(new FileInputStream("test/resources/test3a.docx"))
		mockFile.getContentType() >> "application/msword"
		
		def ass = courseInfoService.findAssignment("CM2006", "TMA03")

		controller.params.dataFile = mockFile
		controller.params.grade = "Pass 2"
		controller.params.courseId = 'CM2006'
		controller.params.studentIds = '09000232'
		controller.params.tutorIds = 'M4000062'
		controller.params.assignmentId = ass.id
		
		controller.session.putAt('current_course', 'CM2006')

        when: 'save is called'
		SpringSecurityUtils.doWithAuth("admin") {
			controller.save()
		}

		then: 'redirect to list and the user has been renamed'
		def group = (controller.response.redirectUrl =~ /^\/submission\/show\/(\d+)$/)
		group.hasGroup()
		1 == group.size()
		def identifier = group[0][1]

		Submission submissionInstance = Submission.get(identifier)
		
		null != submissionInstance
		"test3a.docx" == submissionInstance.filename
		"test/resources/test3a.docx" == submissionInstance.longFilename

		Summary summary = summarizationService.getSubmissionSummary(submissionInstance, true)
		null != summary
		
		Set<String> comments = summary.data.getAt("B").comments
		null != comments.find { it.contains("You might need to be a little more explicit") }
   }	
}
