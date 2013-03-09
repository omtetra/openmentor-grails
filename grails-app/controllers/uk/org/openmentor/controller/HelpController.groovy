package uk.org.openmentor.controller

/**
 * The HelpController
 * @author morungos.
 */
class HelpController {

	/**
	 * The index action for the help controller
	 * @return
	 */
    def index() { 
		
	}

    /**
	 * The contact action for the help controller
	 * @return
	 */
    def contact() { 
		
	}
	
	/**
	 * The error action for the help controller
	 * @return
	 */
    def error() {
		throw new RuntimeException("This action throws an error")
	}
}
