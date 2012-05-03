package uk.org.openmentor.config

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Category)
class CategoryTests {
	
	void setUp() {
		mockDomain(Category)
	}

    void testBasicCategoryBehaviour() {

		Category cat = new Category(id: "A1", band: "A")
		cat.save()
		
		assertEquals "A1", cat.id
		assertEquals "A", cat.band
    }
}
