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

    void testExtractorTestSimple() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/testza.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Dell Support")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("Balloon here!") } != null
    }

    void testExtractorTest1() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test1a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("Not a word wasted here!") } != null
    }

    void testExtractorTest2() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test2a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("What is this based on?") } != null
    }

    void testExtractorTest3() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test3a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("So you are aiming to offer a blended approach") } != null
    }

	void testExtractorTest4() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test4a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("All this could be said of H801") } != null
	}

	void testExtractorTest5() {
		Extractor ext = new ExtractStandard()
		
		File fileName = new File("test/resources/test5a.doc")
		InputStream input = new FileInputStream(fileName)
		
		ext.extract(input)
		
		String text = ext.getBody()
		assertTrue text.contains("Paragraph 2")
		
		Set<String> annotations = ext.getAnnotations()
		assertTrue annotations.find { it.contains("I have genuine sympathy here.") } != null
	}
}
