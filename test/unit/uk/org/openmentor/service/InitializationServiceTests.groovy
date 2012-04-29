package uk.org.openmentor.service

import grails.test.GrailsUnitTestCase
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.config.Weight;
import uk.org.openmentor.config.Category;

class InitializationServiceTests extends GrailsUnitTestCase {
	
	def grailsApplication
	
	protected void setUp() {
		super.setUp()
		mockDomain(Category)
		mockDomain(Grade)
		mockDomain(Weight)
		
		grailsApplication = [config: 
			[openmentor: [
				grades: [
					"A", "B", "C"
				],
				bands: [
					"A", "B"
				],
				categoryBands: [
					"A1": "A",
					"A2": "A",
					"A3": "A",
			        "B1": "B",
			        "B2": "B"
			    ],
				weights: [
					"A": [
						"A": 0.65,
			            "B": 0.35
					],
					"B": [
						"A": 0.55,
			            "B": 0.45
					],
					"C": [
						"A": 0.45,
			            "B": 0.55
					]
			    ]
			]
		]]
	}

    void testInitialization() {
        InitializationService service = new InitializationService()
		
		service.grailsApplication = grailsApplication
		service.initializeConfiguration()
		
		assertNotNull Grade.findByGrade("B")
		
		assertNotNull Category.findByBand("B")
		assertNotNull Category.findByCategory("B2")

		assertNull Category.findByCategory("B3")
		
		assertNotNull Weight.findByGradeAndBand("A", "B")
		assertEquals 0.35, Weight.findByGradeAndBand("A", "B").weight, 0.00001
    }
}
