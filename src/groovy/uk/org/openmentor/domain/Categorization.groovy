package uk.org.openmentor.domain

import org.apache.log4j.Logger;

import uk.org.openmentor.data.Comment;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.config.Category;

class Categorization {
	
	/**
	 * Logger for the Categorization class
	 */
	static final Logger log = Logger.getLogger(this)
	
	/**
	 * A useful empty array which can be reused
	 */
	private static final List<String> emptyList = new ArrayList<String>();
	
	/**
     * Index first by grade and then by
     * category because a given submission is associated
     * with a single Grade but may have comments in all categories.
     **/
    private Map<Grade, Map> gradeMap = new HashMap<Grade, Map>();
	
	/**
	 * Stores the number of submissions of each grade.
	 **/
    private Map<Grade, Integer> submissionCount = new HashMap<Grade, Integer>();
   
	/**
     * Constructor sets submissions counts to zero.
     */
	Categorization() {

	}
	
	/**
	* Get the size of the comment list associated with the cell
	* (category, grade,), ensuring that 0 is returned when
	* appropriate.
	*
	* @param category one cell index;
	* @param grade    the other cell index;
	* @return the required count.
	*/
	public int getCommentCount(String category, String grade) {
		Map<Category,List<String>> categoryMap = gradeMap.get(grade);
		if (categoryMap == null) {
			return 0;
		}
		List<String> commentList = categoryMap.get(category);
		if (commentList == null) {
			return 0;
		}
		return commentList.size();
	}
   
	/**
	 * Calculate the total number of comments of a given catgeory,
	 * summing over over all possible grades.
	 *
	 * @param category the given category;
	 * @return the total number.
	 */
	public int getCommentCount(String category) {
		int sum = 0;
		for (String grade : Grade.getGrades()) {
			sum += getCommentCount(category,grade);
		}
		return sum;
	}
	
	/**
	 * Returns a list of comments for a given band within this Categorization
	 * instance. 
	 * @param band
	 * @return
	 */
	public final List<String> getBandComments(String band) {
		List<String> grades = Grade.getGrades()
		return grades.collectMany { getBandComments(band, it) }
	}
	
	/**
	 * Returns a list of all comments associated with a given category
	 * (not a band) in this categorization. 
	 *
	 * @param category a <code>String</code> value
	 * @return a <code>List</code> value
	 */
	public final List<String> getComments(String category) {
		List list = new ArrayList<String>();
		for (String grade : Grade.getGrades()) {
			list.addAll(getComments(category, grade));
		}
		return list;
	}
	
	/**
	 * Get the list of comments associated with the cell (category,
	 * grade), ensuring that an empty list is returned when
	 * appropriate.
	 *
	 * @param category one String value
	 * @param grade    another String value
	 * @return a <code>List</code> value.
	 */
	public final List<String> getComments(String category, String grade) {
		Map<Category,List<String>> categoryMap = gradeMap.get(grade);
		if (categoryMap == null) {
			return emptyList;
		}
		List<String> commentList = categoryMap.get(category);
		if (commentList == null) {
			return emptyList;
		}
		return commentList;
	}
	
	/**
	 * Get the list of comments associated with the cell (band,
	 * grade), ensuring that an empty list is returned when
	 * appropriate.
	 *
	 * @param band
	 * @param grade
	 * @return the list of comments
	 */
	public final List<String> getBandComments(String band, String grade) {
		Map<Category,List<String>> categoryMap = gradeMap.get(grade);
		if (categoryMap == null) {
			return emptyList;
		}
		List<String> categories = Category.getBandCategories(band)		
		return categories.collectMany { categoryMap.get(it) ?: [] }
	}
	
	/**
	 * Go through the comments associated with the given submission,
	 * each of which has zero or more associated rule categries, and
	 * store them as items in the list associated with the cell
	 * (grade,category).  The same comment may be stored in lists
	 * associated with more than one cell, but won't be in the same
	 * list twice.  Note that we treat the same actual text, from a
	 * different comment (perhaps from a different submission, as
	 * being different.
	 *
	 * @param submission a <code>Submission</code> value
	 * @return the <code>Categorization</code> created or processed so
	 *         the results from several submissions can be accumulated.
	 */
	public void addComments(Submission submission) {

		String gradeName = submission.getGrade();
		if (log.isDebugEnabled()) {
			log.debug("Found submission with Id " + submission.getId()
					+ " and grade " + gradeName);
		}
		// Increment the appropriate submission count
		submissionCount.put(gradeName, submissionCount.get(gradeName) + 1);
		Map<Category, List> categoryMap = (Map) gradeMap.get(gradeName);
		if (categoryMap == null ) {
			categoryMap = new HashMap<Category,List>();
		}

		Set<Comment> comments = submission.getComments();
		for (Comment comment: comments) {
			Set<Category> ruleCategories = comment.getCategories();
			// Different ruleCategories may generate the same category;
			// avoid adding the comment twice to the same list.
			Set<Category> categorySet = new HashSet<String>();
			for (Category category: ruleCategories) {
				List<String> commentList = categoryMap.get(category);
				if (commentList == null) { //First such comment
					commentList = new ArrayList<String>();
				}
				if (categorySet.contains(category)) {
					continue; // do nothing more this loop
				}
				categorySet.add(category);
				commentList.add(comment.getText());
				if (log.isDebugEnabled()) {
					String tmp = comment.getText();
					String commentText;
					if (tmp.length() > 30 ) {
						commentText = tmp.substring(0,27) + "...";
					} else {
						commentText = tmp;
					}
					log.debug("Adding \"" + commentText
							+ "\" to (" + gradeName + "," + category + ")");
				}
				categoryMap.put(category, commentList);
			}
		}
		gradeMap.put(gradeName, categoryMap);
		return;
	}

	/**
	 * Convenience method to add a set of Comments to this.
	 *
	 * @param comments the set of comments to be added.
	 * @return a <code>Categorization</code> value
	 */
	public void addComments(Set<Submission> submissions) {
		for (Submission submission : submissions) {
			this.addComments(submission);
		}
	}

	/**
	 * Set all the internal data to initial values.
	 *
	 */
	public void clear() {
		gradeMap.clear();
		for (String grade: Grade.getGrades()) {
			submissionCount.put(grade, 0);
		}
	}

	/**
	 * Return a map which gives the number of submissions of each
	 * grade which have been included in the categorization.
	 *
	 * @return the map;
	 */
	public Map<String,Integer> getSubmissionCounts() {
		if (log.isDebugEnabled()) {
			log.debug("submissionCount is" + submissionCount);
		}
		return submissionCount;
	}

	/**
	 * This is here primarily for use in debug statements.
	 *
	 * @return information about the current contents of the
	 *         categorization.
	 */
	public String toString() {
		StringBuilder b = new StringBuilder();
		List<String> grades = Grade.getGrades();
		b.append("\n");
		for (String g : grades) {
			b.append("Grade ");
			b.append(g);
			b.append(" has");
			List<String> categories = Category.getCategories();
			boolean firstTime = true;
			for (String c : categories) {
				b.append(firstTime ? " " : ", ");
				b.append(this.getCommentCount(c, g));
				b.append(" in ");
				b.append(c);
				firstTime = false;
			}
			b.append(".\n");
		}
		return b.toString();
	}
}
