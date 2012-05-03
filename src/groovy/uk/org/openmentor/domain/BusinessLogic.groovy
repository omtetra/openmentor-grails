package uk.org.openmentor.domain

import org.apache.log4j.Logger;

class BusinessLogic {
	static final Logger log = Logger.getLogger(this)

	/**
	 * Returns a list of Submissions which will be used to build the
	 * report.  This is the *only* place the submissionId, id and
	 * reportFor parameters are now to be used.
	 *
	 * @return a <code>List</code> value
	 */
	private Map<String, Integer> weightedIdealCounts(Map<String, Integer> count) {
		Map<Category, Integer> expected = new HashMap<Category, Integer>();
		for (String category : Category.getCategories() ) {
			int sum = 0;
			for (String grade : Grade.getGrades()) {
				sum += count.get(grade) * evaluationScheme.getIdealProportion(category, grade);
			}
			expected.put(category,sum);
			if (log.isDebugEnabled()) {
				log.debug("weightedIdealCounts: " + category + ", " + sum);
			}
		}
		return expected;
	}
}
