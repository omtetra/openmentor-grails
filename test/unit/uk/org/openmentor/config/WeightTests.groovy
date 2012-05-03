package uk.org.openmentor.config

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Weight)
class WeightTests {

	void setUp() {
		mockDomain(Weight)
		mockDomain(Grade)
	}

    void testBasicWeightBehaviour() {

		Weight wgt = new Weight(band: "A", grade: new Grade(id: "F"), weight: 0.25)
		wgt.save()
		
		assertEquals "A", wgt.band
		assertEquals "F", wgt.grade.id
		assertEquals 0.25, wgt.weight, 0.00001
		assertNotNull wgt.id
    }
}
