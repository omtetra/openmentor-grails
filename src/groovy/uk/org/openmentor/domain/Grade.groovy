package uk.org.openmentor.domain

import java.util.List;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

class Grade {
	static List<String> getGrades() {
		return ConfigurationHolder.config.openmentor.grades as List<String>
	}

}
