grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        mavenRepo: "http://maven.springframework.org/milestone/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.18'
		
		compile 'org.apache.poi:poi:3.9'
		compile 'org.apache.poi:poi-scratchpad:3.9'
		compile 'org.apache.poi:poi-ooxml:3.9'
        compile 'org.apache.commons:commons-compress:1.5'
		compile 'org.htmlparser:htmlparser:1.6'
        
		test    'org.gmock:gmock:0.8.2'
    }
    plugins {
        runtime ":hibernate:$grailsVersion"
        build ":tomcat:$grailsVersion"
        compile ":webxml:1.4.1"
    }
}
