package uk.org.openmentor.service

import java.io.InputStream;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.org.openmentor.classifier.Classifier;
import uk.org.openmentor.config.Category;
import uk.org.openmentor.config.Grade;
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.data.Comment;
import uk.org.openmentor.data.Submission;
import uk.org.openmentor.extractor.Extractor;

class AnalyzerService {

    static final Logger log = Logger.getLogger(this)

    static transactional = true
    
    Classifier classifierComponent
    Extractor extractorComponent

    /**
     * Analyses a file, and returns the new submission.
     *
     * @param assignment    the Assignment
     * @param students      a Set of students
     * @param tutorIds      a Set of tutorIds
     * @param grade         the grade of assessment
     * @param filename      the name of the file
     * @param fileContents  a byte array of content data
     *
     * @return the submission id for the newly created submission
     * @throws Exception if something goes wrong
     */
    public Submission newSubmission(Assignment assignment,
                                    Set<String> studentsIds,
                                    Set<String> tutorIds,
                                    String grade,
                                    String username,
                                    String longFilename,
                                    byte[] fileContents) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Analyzing submission: grade: " + grade
                      + ", parameter filename: " + filename
                      + ", parameter assignment: " + assignment
                      + ", parameter studentsIds: " + studentsIds
                      + ", parameter tutorIds: " + tutorIds
                      + ", parameter username: " + username);
        }
        
        Grade gradeInstance = Grade.get(grade)
		    String filename = (longFilename =~ /[^\/]+$/)[0]

        Submission sub = new Submission()
        sub.setAssignment(assignment)
        sub.setStudentIds(studentsIds)
        sub.setTutorIds(tutorIds)
        sub.setGrade(gradeInstance)
        sub.setFilename(filename)
		    sub.setLongFilename(longFilename)
        sub.setUsername(username)
        sub.setFileContents(fileContents)
        
        Set<Comment> commentSet = new HashSet<Comment>();
        
        InputStream theInput = new ByteArrayInputStream(fileContents);

        extractorComponent.extract(theInput);
        Set comments = extractorComponent.getAnnotations();
        Iterator i = comments.iterator();
        while (i.hasNext()) {
            String comment = (String) i.next();
            if (log.isDebugEnabled()) {
                log.debug("Comment: " + comment);
            }
            Comment comm = new Comment();
            Set<String> classes = classifierComponent.classifyString((String) comment);
            Set<Category> categories = classes.collect { Category.get(it) }
            
            comm.setText(comment);
            comm.setCategories(categories);
            comm.setSubmission(sub);
            commentSet.add(comm);
        }

        sub.setComments(commentSet);

        if (log.isDebugEnabled()) {
            log.debug("Number of comments: " + commentSet.size())
            log.debug("Number of classified comments: " + commentSet.findAll { it.getCategories().size() > 0 }.size())
            log.debug("Completed analysis of submission OK");
        }

        return sub;
    }
}
