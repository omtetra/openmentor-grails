package uk.org.openmentor.service

import uk.org.openmentor.data.Submission;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.domain.SummaryEntry
import uk.org.openmentor.util.MultiMap;

/**
 * The SummarizationService is responsible for using the database to provide summaries
 * and breakdowns by various values and types. The general principle is that 
 * criteria can be used to select a set of submissions, and the scores of the
 * counts in these submissions can be integrated into a model object for display. 
 * At one extreme, a single submission will do. 
 * 
 * Ideal counts are also provided, but in a second pass. From the ideal, a difference
 * between actual and ideal can be generated, as can percentages. Percentages are 
 * used in a process that can be used to prepare clustered charts and tables, which
 * are the most common view rendering types. 
 * 
 * @author morungos
 */
class SummarizationService {
	
	def courseInfoService
	def currentUserService
	
	private def getUsername() {
		return courseInfoService.trainingMode ? currentUserService.currentUserName() : null
	}
    
    /**
     * Returns a Summary for a specified course.
     * @return
     */
    def getCourseSummary(String course, Boolean comments = false) {
		String userName = getUsername()
        makeSummary([], 
			["sub.assignment as ass", "ass.course as c"], 
			["c.courseId = :courseId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": course].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course, broken down by assignment.
     * @return
     */
    def getCourseSummaryByAssignment(String course, Boolean comments = false) {
		String userName = getUsername()
        makeSummary(["ass.code"], 
			["sub.assignment as ass", "ass.course as c"], 
			["c.courseId = :courseId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": course].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course, broken down by tutor.
     * @return
     */
    def getCourseSummaryByTutor(String course, Boolean comments = false) {
        String userName = getUsername()
        makeSummary(["tutor_id"], 
			["sub.assignment as ass", "ass.course as c", "sub.tutorIds as tutor_id"], 
			["c.courseId = :courseId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": course].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course, broken down by student.
     * @return
     */
    def getCourseSummaryByStudent(String course, Boolean comments = false) {
        String userName = getUsername()
        makeSummary(["student_id"], 
			["sub.assignment as ass", "ass.course as c", "sub.studentIds as student_id"], 
			["c.courseId = :courseId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": course].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a single submission.
     * @return
     */
    def getSubmissionSummary(Submission submission, Boolean comments = false) {
        String userName = getUsername()
        makeSummary([], 
			["sub.assignment as ass"], 
			["sub.id = :id"].plus(userName ? ["ass.owner = :user"] : []), 
			["id": submission.id].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course and assignment.
     * @return
     */
    def getCourseAndAssignmentSummary(String courseId, String assignment, Boolean comments = false) {
        String userName = getUsername()
        makeSummary([], 
			["sub.assignment as ass", "ass.course as c"], 
			["c.courseId = :courseId", "ass.code = :code"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "code": assignment].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for all the submissions for a specified course, broken down by assignment.
     * @return
     */
    def getCourseAndAssignmentSubmissions(String courseId, String assignment, Boolean comments = false) {
        String userName = getUsername()
        makeSummary(["sub.filename"], 
			["sub.assignment as ass", "ass.course as c"], 
			["c.courseId = :courseId", "ass.code = :code"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "code": assignment].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course and student.
     * @return
     */
    def getCourseAndStudentSummary(String courseId, String studentId, Boolean comments = false) {
        String userName = getUsername()
        makeSummary([], 
			["sub.assignment as ass", "ass.course as c", "sub.studentIds as student_id"], 
			["c.courseId = :courseId", "student_id = :studentId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "studentId": studentId].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for all the submissions for a specified course and student.
     * @return
     */
    def getCourseAndStudentSubmissions(String courseId, String studentId, Boolean comments = false) {
        String userName = getUsername()
        makeSummary(["sub.filename"], 
			["sub.assignment as ass", "ass.course as c", "sub.studentIds as student_id"], 
			["c.courseId = :courseId", "student_id = :studentId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "studentId": studentId].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for a specified course and tutor.
     * @return
     */
    def getCourseAndTutorSummary(String courseId, String tutorId, Boolean comments = false) {
        String userName = getUsername()
        makeSummary([], 
			["sub.assignment as ass", "ass.course as c", "sub.tutorIds as tutor_id"], 
			["c.courseId = :courseId", "tutor_id = :tutorId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "tutorId": tutorId].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Returns a Summary for all the submissions for a specified course and tutor.
     * @return
     */
    def getCourseAndTutorSubmissions(String courseId, String tutorId, Boolean comments = false) {
        String userName = getUsername()
        makeSummary(["sub.filename"], 
			["sub.assignment as ass", "ass.course as c", "sub.tutorIds as tutor_id"], 
			["c.courseId = :courseId", "tutor_id = :tutorId"].plus(userName ? ["c.owner = :user", "ass.owner = :user"] : []), 
			["courseId": courseId, "tutorId": tutorId].plus(userName ? ["user": userName] : []), 
			comments)
    }

    /**
     * Generates HQL for the actual and ideal queries, based on a key submission,
     * which will be labelled as 'sub'. To augment that, the queries can add
     * additional where clause filters, and additional group by values, both of
     * which can be based on joins. 
     * 
     * @return
     */
    private def makeSummary(List<String> dimensions, List<String> joins, List<String> criteria, Map namedParams, Boolean comments = false) {
        
        String criteriaString = criteria.join("\nand ")
        String joinString = joins.join("\n  join ")
        String dimensionsString = dimensions.join(", ")
        
        joinString = joinString ? "\n join ${joinString}" : ""
        dimensionsString = dimensionsString ? "${dimensionsString}, " : ""
        String whereString = criteriaString ? "where ${criteriaString}" : ""
        String whereAndString = criteriaString ? "and ${criteriaString}" : ""
        
        /*
         * HQL to generate a query generating counts for actual comments and submissions
         * based on the specified join and filter. 
         */
        String countQuery = "\
select new map(count(distinct sub.id) as submissionCount, count(distinct comment.id) as commentCount) \
from Submission sub \
  join sub.comments comment \
  join comment.categories cat \
${joinString} \
${whereString}"
                
        /*
         * HQL to generate a query generating ideal counts for the submissions
         * based on the specified join and filter. 
         */
        String idealQuery = "\
select new list(${dimensionsString} weight.band, round(weight.weight * count(distinct comment.id))) \
from Submission sub, Weight weight \
  join sub.comments comment \
${joinString} \
where sub.grade = weight.grade \
${whereAndString} \
group by ${dimensionsString} weight.band, weight.weight \
order by ${dimensionsString} weight.band, weight.weight"
        
        /*
         * HQL to generate a query generating actual counts for the submissions
         * based on the specified join and filter. 
         */
        String actualQuery = "\
select new list(${dimensionsString} cat.band, count(distinct comment.id)) \
from Submission sub \
  join sub.comments comment \
  join comment.categories cat \
${joinString} \
${whereString} \
group by ${dimensionsString} cat.band \
order by ${dimensionsString} cat.band"
        
        def countResult = Submission.executeQuery(countQuery, namedParams)
		log.trace("Count result query" + countQuery + ", args: " + namedParams.collect { it.toString()}.join(", "))
		//log.error("Result: " + countResult.collect { it.toString()}.join(", "))
                
        def idealResult = Submission.executeQuery(idealQuery, namedParams)
		log.trace("Ideal result query" + idealQuery + ", args: " + namedParams.collect { it.toString()}.join(", "))
		//log.error("Result: " + idealResult.collect { it.toString()}.join(", "))
		
        def actualResult = Submission.executeQuery(actualQuery, namedParams)
		log.trace("Actual result query" + actualQuery + ", args: " + namedParams.collect { it.toString()}.join(", "))
		//log.error("Result: " + actualResult.collect { it.toString()}.join(", "))
		
        /*
         * Dump the row data into a hierarchical structure in a MultiMap, this
         * is keyed by the return values in order, expecting the band and 
         * the count to be the last values. Create a summary for the leaf 
         * objects.
         */
        MultiMap values = new MultiMap()
        for(List row in idealResult) {
            def next = values
            def keyCount = row.size() - 1
            for(def key in row.subList(0, keyCount - 1)) {
                next = next.getAt(key)
            }
            next.putAt(row[keyCount - 1], new SummaryEntry(ideal: row.last()))
        }

        /*
         * Add the row data into a hierarchical structure in a MultiMap, this
         * is keyed by the return values in order, expecting the band and 
         * the count to be the last values. This time we can assume the 
         * summary entry exists. Always do actual after ideal, as ideal must
         * be present for all bands, and creates the summary.
         */
        for(List row in actualResult) {
            def next = values
            def keyCount = row.size() - 1
            for(def key in row.subList(0, keyCount - 1)) {
                next = next.getAt(key)
            }
            next.getAt(row[keyCount - 1]).actual = row.last()
        }
        
        /*
         * If we are doing comments too, and this is an optional boolean parameter
         * that defaults to false, then grab the comments and add them into the
         * same structure. 
         */
        if (comments) {
            String commentQuery = "\
select new list(${dimensionsString} cat.band, comment.text) \
from Submission sub \
  join sub.comments comment \
  join comment.categories cat \
${joinString} \
${whereString} \
order by ${dimensionsString} cat.band"
            
            def commentResult = Submission.executeQuery(commentQuery, namedParams)
            
            for(List row in commentResult) {
                def next = values
                def keyCount = row.size() - 1
                for(def key in row.subList(0, keyCount - 1)) {
                    next = next.getAt(key)
                }
                next.getAt(row[keyCount - 1]).comments << row.last()
            }
        }

        /*
         * And finally, return a new Summary. 
         */
        return new Summary(
            dimensions: dimensions, 
            data: values,
            submissionCount: countResult[0].submissionCount,
            commentCount: countResult[0].commentCount
        )
    }
}

