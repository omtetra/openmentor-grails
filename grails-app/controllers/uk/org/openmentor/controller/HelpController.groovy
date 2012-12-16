package uk.org.openmentor.controller

class HelpController {

    def index = { }

    def contact = { }
	
	def error = {
		throw new RuntimeException("This action throws an error")
	}
}
