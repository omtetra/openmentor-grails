package uk.org.openmentor.service

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import uk.org.openmentor.config.Category;
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.config.Weight;

/**
 * This service provides methods which are intended to be called at boot time, and 
 * which process the grade-specific configuration data into the database. This allows
 * HQL and SQL to be used with aggregate queries to derive the classifications with
 * better performance than tediously working through each and every individual 
 * comment classification. 
 * 
 * @author morungos
 */
class InitializationService {
	
	def grailsApplication
	
	void deletePreviousConfiguration() {
		
		Weight.executeUpdate("delete Weight w")
		Grade.executeUpdate("delete Grade g")
		Category.executeUpdate("delete Category c")
	}

	/**
	 * Called at boot time, and possibly at other times, to set up the configuration.
	 * Primarily, this reads configuration data from wherever, and creates the 
	 * database tables that are needed to do sensible calculations. 
	 */
    void initializeConfiguration() {
				
		List<String> grades = grailsApplication.config.openmentor.grades
		for(String grade in grades) {
			new Grade(grade: grade).save()
		}
		
		Map<String, String> categoryBands = grailsApplication.config.openmentor.categoryBands
		categoryBands.each { key, value ->
			new Category(category: key, band: value).save()
		}
		
		Map<String, Map<String, Double>> weights = grailsApplication.config.openmentor.weights
		weights.each { grade, values ->
			values.each { band, weight ->
				new Weight(grade: grade, band: band, weight: weight).save()
			}
		}
    }
}
