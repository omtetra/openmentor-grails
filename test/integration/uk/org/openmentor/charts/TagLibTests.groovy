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

		def taglib = new ActualIdealTagLib()
		def result = taglib.actualIdealChart([summary: summary, ref: "ref"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""ideal":[50,101,40,10]""")
    }
	
	void testAssignmentDifferenceChartTagLib() {
		Summary summary = summarizationService.getCourseSummaryByAssignment("CM2006")
		
		def taglib = new DifferenceChartTagLib()
		def result = taglib.differenceChart([summary: summary, ref: "ref", band: "B", action: "assignment"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""categories":["TMA01","TMA02"]""")
	}

	void testTutorDifferenceChartTagLib() {
		Summary summary = summarizationService.getCourseSummaryByTutor("CM2006")
		
		def taglib = new DifferenceChartTagLib()
		def result = taglib.differenceChart([summary: summary, ref: "ref", band: "B", action: "tutor"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""categories":["M4000061","M4000062"]""")
	}

	void testStudentDifferenceChartTagLib() {
		Summary summary = summarizationService.getCourseSummaryByStudent("CM2006")
		
		def taglib = new DifferenceChartTagLib()
		def result = taglib.differenceChart([summary: summary, ref: "ref", band: "B", action: "student"])
		
		assertTrue result.contains("""<script type="text/javascript">""")
		assertTrue result.contains(""""categories":["09000231","09000232","09000237"]""")
	}
}
