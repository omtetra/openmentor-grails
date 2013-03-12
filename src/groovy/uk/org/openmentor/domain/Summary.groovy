package uk.org.openmentor.domain

import java.util.List;

import uk.org.openmentor.util.MultiMap;

class Summary {
	Integer submissionCount
	Integer commentCount
	List<String> dimensions
	MultiMap data
	
	Summary filter(List<String> criteria) {
		return new Summary(data: filteredData(data, criteria, 0), dimensions: dimensions, submissionCount: submissionCount, commentCount: commentCount)
	}
	
	private Object filteredData(MultiMap source, List<String> criteria, int offset) {
		if (criteria[offset] != null) {
			def value = source[criteria[offset]]
			if (value instanceof MultiMap) {
				return filteredData(value, criteria, offset + 1)
			} else {
				return value
			}
		} else {
			return (MultiMap) source.collectEntries { key, value ->
				if (value instanceof MultiMap) {
					[ key, filteredData(value, criteria, offset + 1) ]
				} else {
					[ key, value ]
				}
			}
		}
	}
}

class SummaryEntry {
	Integer actual = 0
	Integer ideal = 0
	List<String> comments = [] as List<String>
	
	String toString() {
		return "[actual: ${actual}, ideal: ${ideal}]"
	}
}
