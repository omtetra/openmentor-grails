package uk.org.openmentor.service

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.junit.rules.ExpectedException;

import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Categorization;
import uk.org.openmentor.domain.Category;
import uk.org.openmentor.domain.DataBook;
import uk.org.openmentor.domain.Grade;
import uk.org.openmentor.evaluator.EvaluationScheme;

/**
 * The AssessmentService is responsible for converting data-backed comment sets into
 * tables and data that can be displayed in views. It could be argued that this belongs
 * as a domain object, but that isn't really appropriate (in Grails) as it spans
 * virtually the whole domain layer. 
 * 
 * @author stuart
 */

class AssessmentService {

    static transactional = true

	/**
	 * Builds a DataBook for charting purposes.
	 * @return  the DataBook instance
	 */
	public final DataBook buildDataBook(Set<Submission> submissions) {

		DataBook dataBook = new DataBook();
		
		Categorization ctgz = new Categorization();
		ctgz.clear();
		ctgz.addComments(submissions);
		
		List<String> categories = Category.getCategories()
		List<String> bands = Category.getBands()

		List<List<String>> comments = new ArrayList<List<String>>();
		Map<String, Integer> actualCounts = new HashMap<String,Integer>();
		int commentCount = 0;
		for (String category: categories) {
			int n = ctgz.getCommentCount(category);
			commentCount += n;
			actualCounts.put(category, n);
			comments.add(ctgz.getComments(category));
			if (log.isDebugEnabled()) {
				log.debug("commentCount for " + category
						+ " is " + commentCount);
			}
		}

		Map<String, Integer> submissionCounts = ctgz.getSubmissionCounts();
		Map<String, Number> idealCounts = weightedIdealCounts(submissionCounts);
		Map<String, Number> actualAggregateCounts = aggregateBands(actualCounts);
		
		int submissionCount = 0;
		submissionCounts.each { key, value -> submissionCount += value }
		Double factor = commentCount / submissionCount;
		
		Map<String, Number> idealAggregateCounts = new HashMap<String, Number>();
		idealCounts.each { key, value -> idealAggregateCounts.put(key, value * factor) }
		
		dataBook.setDataPoints(bands);
		dataBook.setDataSeries("IdealCounts", toList(bands, idealAggregateCounts));
		dataBook.setDataSeries("ActualCounts", toList(bands, actualAggregateCounts));
		dataBook.setDataSeries("ActualComments", comments);
		//dataBook.setProperty("SubmissionCount", new Integer(submissionCount))
		//dataBook.setProperty("CommentCount", new Integer(commentCount))
		return dataBook;
	}
	
	/**
     * We work internally with maps, indexed by Category; externally
     * we prefer a list arranged in the same Category order as that
     * provided by the DescriptorFactory; this methods does the
     * conversion.
     *
     * @param map the given map indexed by Categories;
     * @return the corresponding list.
     */
    private List<Number> toList(List<String> categories, Map <String, Number> map) {
        List l = new ArrayList<Number>();
        for (String category : categories) {
            l.add(map.get(category));
        }
        return l;
    }
	
	private Map<String, Number> aggregateBands(Map<String, Number> count) {
		Map<String, Number> expected = new HashMap<String, Number>();
		Map<String, String> categoryBands = ConfigurationHolder.config.openmentor.categoryBands as Map<String, String>
		for (String category : Category.getCategories() ) {
			String band = categoryBands.get(category);
			Number value = count.get(category)
			if (! expected.containsKey(band)) {
				expected.put(band, value);
			} else {
				expected.put(band, expected.get(band) + value)
			}
		}
		return expected
	}
	
    /**
     * Returns a list of Submissions which will be used to build the
     * report.  This is the *only* place the submissionId, id and
     * reportFor parameters are now to be used.
     *
     * @return a <code>List</code> value
     */
    private Map<String, Number> weightedIdealCounts(Map<String, Integer> count) {
        Map<String, Number> expected = new HashMap<String, Integer>();
        for (String band : Category.getBands() ) {
            Number sum = 0.0;
            for (String grade : Grade.getGrades()) {
                sum += count.get(grade) * getIdealProportion(band, grade);
            }
            expected.put(band, sum);
            if (log.isDebugEnabled()) {
                log.debug("weightedIdealCounts: " + band + ", " + sum);
            }
        }
        return expected;        
    }
	
	public Number getIdealProportion(final String band, final String grade) {
		
		Map<String, Map<String, Double>> weights = ConfigurationHolder.config.openmentor.weights as Map<String, Double>
		Double value = weights.get(grade).get(band)
		return value
	}
}
