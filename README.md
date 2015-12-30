## OpenMentor

This is a web-based application designed to help evaluate use of feedback in 
electronically marked assignments. It extracts comments from Word files and 
classifies them automatically, then offering a variety of ways of visualizing
and analyzing the results.

### Requirements

 * Grails 2.4.4
 * MySQL (or an equivalent relational database)

### Build process

This should build using either Grails directly, or Maven. 

Maven build now restored. Use the following command to build the code, test
it, and build a set of Maven web pages containing some useful-ish reports
on the code, how well it tested, and so on. 

 mvn integration-test site