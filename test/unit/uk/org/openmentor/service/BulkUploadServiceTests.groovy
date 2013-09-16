package uk.org.openmentor.service

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

@TestFor(BulkUploadService)
class BulkUploadServiceTests {

    void setUp() {
        
    }

    void tearDown() {
        
    }

    void testUpload() {
    	File testDataFile = new File("test/resources/upload.zip")
    	def uploads = service.upload(testDataFile)
    }
}
