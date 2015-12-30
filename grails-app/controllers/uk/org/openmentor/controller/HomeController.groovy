package uk.org.openmentor.controller

import grails.plugin.springsecurity.annotation.Secured;

/**
 * The HomeController
 * @author morungos.
 */
@Secured(['ROLE_OPENMENTOR-USER', 'ROLE_ANONYMOUS'])
class HomeController {

	/**
	 * The index action for the help controller
	 * @return
	 */
    def index() { 
		
	}
}