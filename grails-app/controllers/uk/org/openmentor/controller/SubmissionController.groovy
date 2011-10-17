package uk.org.openmentor.controller

class SubmissionController {

    def index = { 
		redirect(action: "new", params: params)
	}
}
