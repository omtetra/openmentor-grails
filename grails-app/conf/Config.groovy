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
	"file:/etc/${appName}/${appName}-config.properties",
	"file:/etc/${appName}/${appName}-config.groovy",
	"file:${userHome}/.grails/${appName}-config.properties",
	"file:${userHome}/.grails/${appName}-config.groovy"
]

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://openmentor.cloudfoundry.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
}

def catalinaBase = System.env.CATALINA_BASE ?: "."
def logDirectory = "${catalinaBase}/logs"
def applicationName = appName

// log4j configuration
log4j = {
	
	appenders {
		console name: "stdout",
				layout: pattern(conversionPattern: "%r [%t] %p %c %x - %m%n")
	}
	
	root {
		debug 'stdout'
	}
	
	//debug  'org.hibernate.hql.ast.QueryTranslatorImpl'
    
	info   'grails.app',
	       'uk.org.openmentor'

    warn   'grails.plugin',
		   'grails.spring',
	       'org.apache',
	       'org.springframework',
		   'org.hibernate',
           'org.eclipse',
           'asset.pipeline',
           'ScaffoldingGrailsPlugin'
		   
	error  'net.sf.ehcache',
		   'org.codehaus.groovy.grails',
		   'org.springframework.aop',
		   'grails.plugin.webxml',
		   'org.grails.datastore.mapping',
		   'grails.plugin.springsecurity.web.access.intercept'
}

//grails.plugin.springsecurity.rejectIfNoRule = true

grails.plugin.springsecurity.roleHierarchy = '''
ROLE_OPENMENTOR-ADMIN > ROLE_OPENMENTOR-USER
'''

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'uk.org.openmentor.auth.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'uk.org.openmentor.auth.UserRole'
grails.plugin.springsecurity.authority.className = 'uk.org.openmentor.auth.Role'

// Added for the Spring Security LDAP plugin:
// Tested against slapd - 19/12/2012
//grails.plugin.springsecurity.ldap.context.managerDn = 'cn=Manager,dc=morungos,dc=com'
//grails.plugin.springsecurity.ldap.context.managerPassword = 'd1n0$aur'
//grails.plugin.springsecurity.ldap.context.anonymousReadOnly=true
//grails.plugin.springsecurity.ldap.context.server = 'ldap://localhost:389/'
//grails.plugin.springsecurity.ldap.search.base = 'dc=morungos,dc=com'
//grails.plugin.springsecurity.ldap.search.filter = '(uid={0})'
//grails.plugin.springsecurity.ldap.authorities.groupSearchBase = 'ou=groups,dc=morungos,dc=com'
//grails.plugin.springsecurity.ldap.authorities.groupSearchFilter = 'member={0}'
//grails.plugin.springsecurity.password.algorithm = 'MD5'
//grails.plugin.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
//grails.plugin.springsecurity.ldap.authenticator.useBind = false
//grails.plugin.springsecurity.providerNames = ['ldapAuthProvider', 'daoAuthenticationProvider']

// This exploits the significantly more flexible handling of types in Grails to 
// allow configuration down to the grading scheme. 
//
// By default, work in non-training mode
openmentor {
	trainingMode = false
	
	grades = [
		"Pass 1", "Pass 2", "Pass 3", "Pass 4", "Bare fail", "Fail"
	]
	categories = [
		"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3"
	]
	bands = [
		"A", "B", "C", "D"
	]
	bandLabels = [
		"A": "Positive comments",
		"B": "Teaching points",
		"C": "Questions",
		"D": "Negative comments"	
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
		"Pass 1": [
			"A": 0.30,
            "B": 0.50,
			"C": 0.15,
			"D": 0.05
		],
		"Pass 2": [
			"A": 0.25,
            "B": 0.50,
			"C": 0.20,
			"D": 0.05
		],
		"Pass 3": [
			"A": 0.15,
            "B": 0.60,
			"C": 0.20,
			"D": 0.05
		],
		"Pass 4": [
			"A": 0.10,
            "B": 0.60,
			"C": 0.25,
			"D": 0.05
		],
		"Bare fail": [
			"A": 0.05,
            "B": 0.65,
			"C": 0.25,
			"D": 0.05
		],
		"Fail": [
			"A": 0.05,
            "B": 0.65,
			"C": 0.25,
			"D": 0.05
		],
	]
}
