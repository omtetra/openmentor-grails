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
	
	def getSummary(Boolean comments = false) {
		makeSummary([], [], [], [:], comments)
	}
	
	def getCourseSummary(String course, Boolean comments = false) {
		makeSummary([], ["sub.assignment as ass"], ["ass.courseId = :courseId"], ["courseId": course], comments)
	}

	def getCourseSummaryByAssignment(String course, Boolean comments = false) {
		makeSummary(["ass.code"], ["sub.assignment as ass"], ["ass.courseId = :courseId"], ["courseId": course], comments)
	}

	def getCourseSummaryByTutor(String course, Boolean comments = false) {
		makeSummary(["tutor_id"], ["sub.assignment as ass", "sub.tutorIds as tutor_id"], ["ass.courseId = :courseId"], ["courseId": course], comments)
	}

	def getCourseSummaryByStudent(String course, Boolean comments = false) {
		makeSummary(["student_id"], ["sub.assignment as ass", "sub.studentIds as student_id"], ["ass.courseId = :courseId"], ["courseId": course], comments)
	}

	def getSubmissionSummary(Submission submission, Boolean comments = false) {
		makeSummary([], ["sub.assignment as ass"], ["sub.id = :id"], ["id": submission.id], comments)
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
		
		String countQuery = "\
select new map(count(distinct sub.id) as submissionCount, count(distinct comment.id) as commentCount) \
from Submission sub \
  join sub.comments comment \
  join comment.categories cat \
${joinString} \
${whereString}"
				
		String idealQuery = "\
select new list(${dimensionsString} weight.band, round(weight.weight * count(distinct comment.id))) \
from Submission sub, Weight weight \
  join sub.comments comment \
${joinString} \
where sub.grade = weight.grade \
${whereAndString} \
group by ${dimensionsString} weight.band \
order by ${dimensionsString} weight.band"
		
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
				
		def idealResult = Submission.executeQuery(idealQuery, namedParams)
		
		def actualResult = Submission.executeQuery(actualQuery, namedParams)
		
		MultiMap values = new MultiMap()
		for(List row in idealResult) {
			def next = values
			def keyCount = row.size() - 1
			for(def key in row.subList(0, keyCount - 1)) {
				next = next.getAt(key)
			}
			next.putAt(row[keyCount - 1], new SummaryEntry(ideal: row.last()))
		}

		for(List row in actualResult) {
			def next = values
			def keyCount = row.size() - 1
			for(def key in row.subList(0, keyCount - 1)) {
				next = next.getAt(key)
			}
			next.getAt(row[keyCount - 1]).actual = row.last()
		}
		
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

		return new Summary(
			dimensions: dimensions, 
			data: values,
			submissionCount: countResult[0].submissionCount,
			commentCount: countResult[0].commentCount
		)
	}
}

