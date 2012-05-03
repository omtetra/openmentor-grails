package uk.org.openmentor.domain

import java.util.List;

import uk.org.openmentor.util.MultiMap;

class Summary {
	Integer submissionCount
	Integer commentCount
	List<String> dimensions
	MultiMap data
}

class SummaryEntry {
	Integer actual = 0
	Integer ideal = 0
	List<String> comments = [] as List<String>
	
	String toString() {
		return "[actual: ${actual}, ideal: ${ideal}]"
	}
}
