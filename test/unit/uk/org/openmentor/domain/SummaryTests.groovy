package uk.org.openmentor.domain

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import uk.org.openmentor.util.MultiMap
import uk.org.openmentor.domain.SummaryEntry

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class SummaryTests {
	
	Summary summary
	
	MultiMap data = new MultiMap(
		"test1a.doc": new MultiMap(
			"A": new SummaryEntry(actual: 2, ideal: 4),
			"B": new SummaryEntry(actual: 4, ideal: 7),
			"C": new SummaryEntry(actual: 5, ideal: 2),
			"D": new SummaryEntry(actual: 1, ideal: 1)
		),
		"test2a.doc": new MultiMap(
			"A": new SummaryEntry(actual: 6, ideal: 5),
			"B": new SummaryEntry(actual: 9, ideal: 9),
			"C": new SummaryEntry(actual: 4, ideal: 4),
			"D": new SummaryEntry(actual: 0, ideal: 1)
		),
		"test5a.doc": new MultiMap(
			"A": new SummaryEntry(actual: 18, ideal: 10),
			"B": new SummaryEntry(actual: 44, ideal: 40),
			"C": new SummaryEntry(actual: 16, ideal: 13),
			"D": new SummaryEntry(actual: 9, ideal: 3)
		)
	)

    void setUp() {
		summary = new Summary(data: data, dimensions: [], submissionCount: 4, commentCount: 66)
    }

    void tearDown() {
        // Tear down logic here
    }

    void testFilteringNull() {
        Summary modified = summary.filter([null, null])
		
		assertEquals 66, modified.commentCount
		assertEquals 4, modified.submissionCount
		
		assertTrue modified.data instanceof MultiMap
		def val = modified.data.get("test1a.doc")
		assertTrue modified.data.get("test1a.doc") instanceof MultiMap
		assertTrue modified.data.get("test1a.doc").get("B") instanceof SummaryEntry
		assertEquals 7, modified.data.get("test1a.doc").get("B").ideal
    }

    void testFilteringInitial() {
        Summary modified = summary.filter(["test1a.doc", null])
		
		assertTrue modified.data instanceof MultiMap
		assertTrue modified.data.get("B") instanceof SummaryEntry
		assertEquals 7, modified.data.get("B").ideal
    }

    void testFilteringFinal() {
        Summary modified = summary.filter([null, "B"])
		
		assertTrue modified.data instanceof MultiMap
		assertTrue modified.data.get("test1a.doc") instanceof SummaryEntry
		assertEquals 7, modified.data.get("test1a.doc").ideal
    }

    void testClone() {
        Summary modified = summary.clone()
		assertEquals summary.commentCount, modified.commentCount
		assertEquals summary.submissionCount, modified.submissionCount
		assertTrue modified.dimensions.equals(summary.dimensions)
		assertEquals modified.data.toString(), summary.data.toString()
    }
}
