package uk.org.openmentor.controller

import grails.plugin.springsecurity.annotation.Secured;

/**
 * The HelpController
 * @author morungos.
 */
@Secured(['ROLE_OPENMENTOR-USER', 'ROLE_ANONYMOUS'])
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
