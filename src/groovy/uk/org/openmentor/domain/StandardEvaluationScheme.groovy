package uk.org.openmentor.domain

import java.util.Map;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import uk.org.openmentor.evaluator.EvaluationScheme;

/**
 * The standard evaluation scheme simply uses a set of weights defined in the main
 * configuration files. It is simple and easy to adapt to different organizations. 
 * 
 * @author morungos
 */
class StandardEvaluationScheme implements EvaluationScheme {
	
	/**
	 * Method to look up the ideal proportion. 
	 */
	public double getIdealProportion(final String band, final String grade) {
		
		Map<String, Map<String, Double>> weights = ConfigurationHolder.config.openmentor.weights as Map<String, Double>
		
		return weights.getAt(grade).getAt(band).doubleValue()
	}
}
