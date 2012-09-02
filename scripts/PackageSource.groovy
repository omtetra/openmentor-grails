includeTargets << grailsScript("_GrailsInit")

// This script creates a distributable source tarall. This is part of the process
// used to generate an RPM, as this file forms the source. We use Grails to manage
// this partly because it is aware of the actual application name and version,
// independently of that known to the VCS.

target(default:"The default target is the one that gets executed by Grails") {
	def metadata = grails.util.Metadata.getCurrent()
	def app_name = metadata."app.name" 
    def app_version = metadata."app.version" 
	
	ant.delete(dir:"target/tar")
	
    ant.mkdir(dir:"target/tar/${app_name}-${app_version}")
	ant.copydir(src:"grails-app", dest:"target/tar/${app_name}-${app_version}/grails-app")
	ant.copydir(src:"web-app", dest:"target/tar/${app_name}-${app_version}/web-app")
	ant.copydir(src:"scripts", dest:"target/tar/${app_name}-${app_version}/scripts")
	ant.copydir(src:"src", dest:"target/tar/${app_name}-${app_version}/src")
	ant.copyfile(src:"application.properties", dest:"target/tar/${app_name}-${app_version}/application.properties")
	ant.copyfile(src:"pom.xml", dest:"target/tar/${app_name}-${app_version}/pom.xml")
	ant.copyfile(src:"README.md", dest:"target/tar/${app_name}-${app_version}/README.md")

	ant.tar(destfile:"target/${app_name}-${app_version}.tar.gz", basedir:"target/tar", compression:"gzip")
}
