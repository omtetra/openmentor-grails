package uk.org.openmentor.controller

import uk.org.openmentor.data.Submission;

/**
 * This controller is used to manage the views and actions associated with the
 * history. The precise contents of this history may evolve over time, but it 
 * mainly allows people to track what submissions have been uploaded when, and by
 * whom.
 * 
 * @author morungos
 */
class HistoryController {

    def index() { 
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'dateSubmitted'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Submission.createCriteria()
		
		def submissionList = criteria.list {
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
		
		def submissionCount = Submission.count()
		
		[submissionInstanceList: submissionList, submissionInstanceTotal: submissionCount]
	}

}
