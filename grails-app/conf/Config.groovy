// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

grails.config.locations = [ 
	"classpath:${appName}-config.properties",
	"classpath:${appName}-config.groovy",
	"file:${userHome}/.grails/${appName}-config.properties",
	"file:${userHome}/.grails/${appName}-config.groovy"
]

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
}

// log4j configuration
log4j = {
    
	appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    }
	
	debug  'grails.app.controller',
	       'grails.app.domain'

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

//grails.plugins.springsecurity.rejectIfNoRule = true

grails.plugins.springsecurity.roleHierarchy = '''
ROLE_OPENMENTOR-ADMIN > ROLE_OPENMENTOR-USER
'''

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'uk.org.openmentor.auth.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'uk.org.openmentor.auth.UserRole'
grails.plugins.springsecurity.authority.className = 'uk.org.openmentor.auth.Role'

// This exploits the significantly more flexible handling of types in Grails to 
// allow configuration down to the grading scheme. 
openmentor {
	grades = [
		"A", "B", "C", "D", "E", "F"
	]
	categories = [
		"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3"
	]
	bands = [
		"A", "B", "C", "D"
	]
	categoryBands = [
		"A1": "A",
		"A2": "A",
		"A3": "A",
		"B1": "B",
		"B2": "B",
		"B3": "B",
		"C1": "C",
		"C2": "C",
		"C3": "C",
		"D1": "D",
		"D2": "D",
		"D3": "D"
	]
	weights = [
		"A": [
			"A": 0.45,
            "B": 0.25,
			"C": 0.2,
			"D": 0.1
		],
		"B": [
			"A": 0.4,
            "B": 0.3,
			"C": 0.2,
			"D": 0.1
		],
		"C": [
			"A": 0.35,
            "B": 0.35,
			"C": 0.2,
			"D": 0.1
		],
		"D": [
			"A": 0.3,
            "B": 0.4,
			"C": 0.2,
			"D": 0.1
		],
		"E": [
			"A": 0.25,
            "B": 0.45,
			"C": 0.2,
			"D": 0.1
		],
		"F": [
			"A": 0.2,
            "B": 0.5,
			"C": 0.2,
			"D": 0.1
		],
	]
}
