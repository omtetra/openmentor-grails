package uk.org.openmentor.util

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import uk.org.openmentor.domain.SummaryEntry;

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MultiMapTests {
	
	MultiMap data = new MultiMap(
		"test1a.doc": new MultiMap(
			"A": new SummaryEntry(actual: 2, ideal: 4),
			"B": new SummaryEntry(actual: 4, ideal: 7)
		),
		"test2a.doc": new MultiMap(
			"A": new SummaryEntry(actual: 6, ideal: 5),
			"B": new SummaryEntry(actual: 9, ideal: 9)
		)
	)

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testClone() {
		MultiMap modified = data.clone()
        assertEquals modified.toString(), data.toString()
    }
}
