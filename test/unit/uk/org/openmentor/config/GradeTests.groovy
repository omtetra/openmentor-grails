package uk.org.openmentor.config

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Grade)
class GradeTests {

	void setUp() {
		mockDomain(Grade)
	}

    void testBasicGradeBehaviour() {

		Grade grd = new Grade(id: "Pass 1")
		grd.save()
		
		assertEquals "Pass 1", grd.id
    }
}
