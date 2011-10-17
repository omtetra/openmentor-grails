package uk.org.openmentor.domain

import java.util.Map;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import uk.org.openmentor.evaluator.EvaluationScheme;

class StandardEvaluationScheme implements EvaluationScheme {
	
	public double getIdealProportion(final String category, final String grade) {
		
		Map<String, String> bands = ConfigurationHolder.config.openmentor.bands as Map<String, String>
		Map<String, Map<String, Double>> weights = ConfigurationHolder.config.openmentor.weights as Map<String, Double>
		
		return weights.get(grade).get(bands.getAt(category)).value
	}
}
