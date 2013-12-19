package uk.org.openmentor.service

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.regex.Matcher;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.*;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.Node;

import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Student;
import uk.org.openmentor.courseinfo.Tutor;
import uk.org.openmentor.data.Submission;

class BulkUploadService {

	static transactional = false

	def courseInfoService
	def analyzerService
	def currentUserService

	private Map gradeBoundaries = [
		"A": 85,
		"B": 70,
		"C": 55,
		"D": 40,
		"E": 30,
		"F": 15,
		"G": 0
	]

	private List grades = gradeBoundaries.entrySet().sort { it.value }.reverse().collect { it.key }

	private String getGradeFromScore(Integer score) {
		for(String grade in grades) {
			if (score > gradeBoundaries[grade]) {
				return grade;
			}
		}
	}

	// Called to handle a single zip entry. 
	// if this is a zip file, we can recurse, basically. 
	private void uploadZipEntry(ZipEntry ze, InputStream data) {
		String name = ze.getName()
		if(name =~ /\.zip/)	{
			upload(data)
		} else {
			System.err.println("File: " + name)			
		}
	}

	void upload(File fileZip) {
		ZipFile zipFile = new ZipFile(fileZip);
		Enumeration entries = zipFile.getEntries();

		while (entries.hasMoreElements()) {
            ZipArchiveEntry zipEntry = entries.nextElement();
            handleUploadEntry(zipFile, zipEntry);
        }
    }

    class ZipArchiveEntryComparator implements Comparator<ZipArchiveEntry> {
	    @Override
	    public int compare(ZipArchiveEntry a, ZipArchiveEntry b) {
	    	return b.getTime() - a.getTime();
	    }
	}

    void handleUploadZipFile(File entryFile) {
 		ZipFile zipFile = new ZipFile(entryFile);
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		List<ZipArchiveEntry> contentEntries = new ArrayList<ZipArchiveEntry>()
		ZipArchiveEntry dataEntry = null

		while (entries.hasMoreElements()) {
            ZipArchiveEntry zipEntry = entries.nextElement();
            String name = zipEntry.getName()
            if (name =~ /^Assessment Summary.*\(pt3\)\.htm$/) {
            	dataEntry = zipEntry
            } else if (name == "PT3OULogo.gif") {
            	// Do nothing
            } else {
            	contentEntries.add(zipEntry)
            }
		}

		if (! dataEntry) {
			return;
		}

		// If there are several entries, pick the newest

		if (contentEntries.size() > 1) {
			Collections.sort(contentEntries, new ZipArchiveEntryComparator());
		} else if (contentEntries.size() < 1) {
			log.error("Incomplete: " + entryFile.toString())
		} else {
			Course.withTransaction { status ->
				try {
					handleUploadSubmission(zipFile, dataEntry, contentEntries.get(0))
				} catch (Exception e) {
					e.printStackTrace();
					status.setRollbackOnly()
				}
			}
		}
		zipFile.close()
    }

    void handleUploadSubmission(ZipFile zipFile, ZipArchiveEntry dataEntry, ZipArchiveEntry contentEntry) {

		InputStream dataStream = zipFile.getInputStream(dataEntry);
		byte[] dataBytes = IOUtils.toByteArray(dataStream);
		String dataBody = new String(dataBytes);

		Parser parser = new Parser(new Lexer(dataBody));
		NodeList nl = parser.parse(null);
		NodeList cells = nl.extractAllNodesThatMatch(new TagNameFilter("td"), true);

		List<String> values = new ArrayList<String>();

		SimpleNodeIterator nodeIterator = cells.elements();
	    while (nodeIterator.hasMoreNodes()) {
	    	Node node = nodeIterator.nextNode();
			TableColumn tag = (TableColumn)node;
			values.add(tag.toPlainTextString())
		}
		String name = values[7];
		String studentIdentifier = values[15];
		String course = values[19];
		course = course.replaceAll(/&nbsp;/, " ");
		String assignment = values[21];
		String tutorIdentifier = values[26];
		Integer mark = values[53].toInteger();
		String grade = getGradeFromScore(mark);
		Matcher matcher = course =~ /(\w+)\s+(.*)/;
		String courseIdentifier = matcher[0][1];
		String cohort = matcher[0][2];

		log.error([name, studentIdentifier, courseIdentifier, cohort, assignment, tutorIdentifier, mark, grade]);    

		// Step 1. Locate the course
		//log.error("Locating course");
		Course courseInstance = courseInfoService.findCourse(courseIdentifier);
		//log.error("Located course: " + courseInstance);
		if (! courseInstance) {
			courseInstance = new Course([courseId: courseIdentifier, courseTitle: courseIdentifier])
			courseInfoService.initializeCourse(courseInstance);
			//log.error("Saving course: " + courseInstance);
			courseInstance.save(failOnError: true, flush: true);
		}

		// Step 2. Locate the assignment
		Assignment assignmentInstance = courseInfoService.findAssignment(courseInstance, "TMA" + assignment);
		//log.error("Located assignment: " + assignmentInstance);
		if (! assignmentInstance) {
			assignmentInstance = new Assignment([course: courseInstance, code: "TMA" + assignment])
			courseInfoService.initializeAssignment(assignmentInstance)
			//log.error("Saving assignment: " + assignmentInstance);
			assignmentInstance.save(failOnError: true, flush: true);
		}

		// Step 3. Locate the student
		Student studentInstance = courseInfoService.findStudent(studentIdentifier)
		//log.error("Located student: " + studentInstance);
		if (! studentInstance) {
			studentInstance = new Student([studentId: studentIdentifier])
			courseInfoService.initializeStudent(studentInstance)
			//log.error("Saving student: " + studentInstance);
			studentInstance.save(failOnError: true, flush: true);
			//log.error("Done saving student: " + studentInstance);
		}

		// Step 4. Locate the tutor
		Tutor tutorInstance = courseInfoService.findTutor(tutorIdentifier)
		//log.error("Located tutor: " + tutorInstance);
		if (! tutorInstance) {
			tutorInstance = new Tutor([tutorId: tutorIdentifier])
			courseInfoService.initializeTutor(tutorInstance)
			//log.error("Saving tutor: " + tutorInstance);
			tutorInstance.save(failOnError: true, flush: true);
		}

		// Step 5. Now we can get the file into a byte array, and the filename from the entry, and we're
		// good to create a submission through the AnalyzerService. 
		
		InputStream contentStream = zipFile.getInputStream(contentEntry);
		byte[] contentBytes = IOUtils.toByteArray(contentStream);
		
		Set<String> studentIds = new TreeSet<String>();
		Set<String> tutorIds = new TreeSet<String>();
		studentIds.add(studentIdentifier);
		tutorIds.add(tutorIdentifier);

		String userName = currentUserService.currentUserName();
		String fileName = contentEntry.getName();

		Submission sub = analyzerService.newSubmission(assignmentInstance, studentIds, tutorIds, grade, userName, fileName, contentBytes);
		sub.cohort = cohort;
		sub.score = mark;
		sub.save(failOnError: true, flush: true);
	}


    // If we have multiple content files, choose the most recent. 

    void handleUploadEntry(ZipFile zipFile, ZipArchiveEntry zipEntry) {
    	if (zipEntry.isDirectory()) {
            return;
        }

        String name = zipEntry.getName()
        if (name =~ /\.zip/)	{
	        handleUploadZipEntry(zipFile, zipEntry);
        }
    }

    void handleUploadZipEntry(ZipFile zipFile, ZipArchiveEntry zipEntry) {
        File temp = File.createTempFile("temp",".zip")
        temp.deleteOnExit()
        InputStream inStream = zipFile.getInputStream(zipEntry)
        OutputStream outStream = new FileOutputStream(temp)
        IOUtils.copy(inStream, outStream)
        outStream.close()
        handleUploadZipFile(temp)
    }
}