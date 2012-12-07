## OpenMentor

This is a web-based application designed to help evaluate use of feedback in 
electronically marked assignments. It extracts comments from Word files and 
classifies them automatically, then offering a variety of ways of visualizing
and analyzing the results.

### Requirements

 * Grails 2.1.1
 * MySQL (or an equivalent relational database)

### Build process

This should build using either Grails directly, or Maven. 

Maven is not currently viable on OSX 10.8, due to a problem in the Maven
plugin for Grails, which only works on JDK6. JDK6 is not supported on OSX
10.8 (Mountain Lion). On this platform, use Grails directly.