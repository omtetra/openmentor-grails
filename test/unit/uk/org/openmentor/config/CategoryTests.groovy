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

		Category cat = new Category(band: "A", category: "A1")
		cat.save()
		
		assertEquals "A", cat.band
		assertEquals "A1", cat.category
		assertNotNull cat.id
    }
}
