package uk.org.openmentor.extractor

import uk.org.openmentor.extractor.impl.ExtractStandard

import grails.test.*

class ExtractStandardTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExtractor() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test1a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("Not a word wasted here!") } != null
    }
}
