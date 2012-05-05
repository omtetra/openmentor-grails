package uk.org.openmentor.charts

import grails.test.*
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.test.LoadedSubmissionsTestCase

class TagLibTests extends LoadedSubmissionsTestCase {
	
	def summarizationService
	
	protected void setUp() {
		super.setUp()
	}

    void testCourseTaglib() {
		Summary summary = summarizationService.getCourseSummary("CM2006")

		def taglib = new CourseTagLib()
		def result = taglib.courseChart([summary: summary, ref: "ref"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""ideal":[50,101,40,10]""")
    }
	
	void testDifferenceChartTagLib() {
		Summary summary = summarizationService.getCourseSummaryByAssignment("CM2006")
		
		def taglib = new DifferenceChartTagLib()
		def result = taglib.differenceChart([summary: summary, ref: "ref", band: "B"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""categories":["TMA01","TMA02"]""")
	}
}
