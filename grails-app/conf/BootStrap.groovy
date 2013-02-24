import uk.org.openmentor.config.Grade
import uk.org.openmentor.config.Weight
import uk.org.openmentor.config.Category
import uk.org.openmentor.courseinfo.Assignment;
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.courseinfo.Tutor
import uk.org.openmentor.auth.Role
import uk.org.openmentor.auth.User
import uk.org.openmentor.auth.UserRole
import uk.org.openmentor.system.DataSourceUtils
import grails.util.Environment
import org.apache.log4j.Logger

class BootStrap {
    
    static final Logger log = Logger.getLogger(this)
    
    def grailsApplication
    def springSecurityService
	def courseInfoService

    def init = { servletContext ->
        DataSourceUtils.tune(servletContext)
                
        seedUserData()
        
        switch (Environment.current) {
            
            case Environment.DEVELOPMENT:
                seedTestData()
                break;
            
            case Environment.TEST:
                seedTestData()
                break;
            
            case Environment.PRODUCTION:
                break;
        }
        
        initializeConfiguration()
    }

    def destroy = {
    }
    
    /**
     * Initializes the user data, ensuring that all required roles exist, and that an 
     * user "admin" exists if it doesn't already. It is initially created with a password
     * "admin", that probably ought to be changed. That probably requires an interface. 
     */
    private void seedUserData() {
        
        def userRole = Role.findByAuthority('ROLE_OPENMENTOR-USER') ?: new Role(authority: 'ROLE_OPENMENTOR-USER').save(failOnError: true)
        def adminRole = Role.findByAuthority('ROLE_OPENMENTOR-ADMIN') ?: new Role(authority: 'ROLE_OPENMENTOR-ADMIN').save(failOnError: true)
		
		def encodedPassword = springSecurityService.encodePassword('admin')
        def adminUser = User.findByUsername('admin') ?: new User(
            username: 'admin',
            password: encodedPassword,
			confirm: encodedPassword,
            enabled: true).save(failOnError: true)
			
		if (! adminUser.authorities.contains(userRole)) {
			UserRole.create adminUser, userRole
		}
        if (! adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }

		def encodedUserPassword = springSecurityService.encodePassword('user')
		def userUser = User.findByUsername('user') ?: new User(
			username: 'user',
			password: encodedUserPassword,
			confirm: encodedUserPassword,
			enabled: true).save(failOnError: true)
            
		if (! userUser.authorities.contains(userRole)) {
			UserRole.create userUser, userRole
		}
    }
    
    /**
     * When run in test or development mode, create a few courses, students, and 
     * tutors, ready to test out the rest of the system. 
     */
    private void seedTestData() {
        // Seed only when there's no data
        if (Course.count() == 0) {
            
            def courseCM2006 = new Course(courseId: "CM2006", courseTitle: "Interface Design").save(failOnError:true)
            def courseCM2007 = new Course(courseId: "CM2007", courseTitle: "Intranet Systems Development").save(failOnError:true)
            def courseCM3010 = new Course(courseId: "CM3010", courseTitle: "Information Retrieval").save(failOnError:true)
            def courseAA1003 = new Course(courseId: "AA1003", courseTitle: "Multimedia Programming").save(failOnError:true)

            def courseCMM511 = new Course(owner: "user", courseId: "CMM511", courseTitle: "Information Retrieval Systems").save(failOnError:true)

            def student09000231 = new Student(studentId: "09000231", givenName: "Gwenda", familyName: "Blane")
            student09000231.addToCourses(courseCM2006)
			student09000231.addToCourses(courseCM2007)
			student09000231.save(failOnError:true)

            def student09000232 = new Student(studentId: "09000232", givenName: "Fred", familyName: "Zucker")
            student09000232.addToCourses(courseCM2007)
			student09000232.addToCourses(courseCM2006)
			student09000232.addToCourses(courseAA1003)
			student09000232.save(failOnError:true)

            def student09000233 = new Student(studentId: "09000233", givenName: "Caitlyn", familyName: "Respass")
            student09000233.addToCourses(courseCM2006)
			student09000233.addToCourses(courseCM3010)
			student09000233.addToCourses(courseAA1003)
			student09000233.save(failOnError:true)

            def student09000234 = new Student(studentId: "09000234", givenName: "Luke", familyName: "Naccarato")
            student09000234.addToCourses(courseCM2006)
			student09000234.addToCourses(courseCM2007)
			student09000234.addToCourses(courseCM3010)
			student09000234.addToCourses(courseAA1003)
			student09000234.save(failOnError:true)

            def student09000235 = new Student(studentId: "09000235", givenName: "Pierre", familyName: "Busse")
            student09000235.addToCourses(courseCM3010)
			student09000235.addToCourses(courseCM2006)
			student09000235.save(failOnError:true)

            def student09000236 = new Student(studentId: "09000236", givenName: "Ami", familyName: "Montalvo")
            student09000236.addToCourses(courseCM2006)
            student09000236.addToCourses(courseCM2007)
			student09000236.addToCourses(courseAA1003)
			student09000236.save(failOnError:true)

            def student09000237 = new Student(studentId: "09000237", givenName: "Jackie", familyName: "Nicolas")
			student09000237.addToCourses(courseAA1003)
            student09000237.save(failOnError:true)

            def student09000238 = new Student(owner: "user", studentId: "09000238", givenName: "Quincy", familyName: "Kilgore")
            student09000238.addToCourses(courseCMM511)
			student09000238.save(failOnError:true)

            def tutorM4000061 = new Tutor(tutorId: "M4000061", givenName: "Zena", familyName: "Beatrice")
			tutorM4000061.addToCourses(courseAA1003)
			tutorM4000061.addToCourses(courseCM2006)
            tutorM4000061.save(failOnError:true)

            def tutorM4000062 = new Tutor(tutorId: "M4000062", givenName: "Levi", familyName: "Evert")
			tutorM4000062.addToCourses(courseCM2006)
			tutorM4000062.addToCourses(courseCM2007)
            tutorM4000062.save(failOnError:true)

            def tutorM4000063 = new Tutor(tutorId: "M4000063", givenName: "Jeanie", familyName: "Denman")
			tutorM4000063.addToCourses(courseCM2006)
			tutorM4000063.addToCourses(courseCM3010)
            tutorM4000063.save(failOnError:true)
            
            def tutorM4000064 = new Tutor(owner: "user", tutorId: "M4000064", givenName: "Monroe", familyName: "Taing")
			tutorM4000064.addToCourses(courseCMM511)
			tutorM4000064.save(failOnError:true)
            
			courseCM2006.addToAssignments(new Assignment(code: "TMA01")).save(failOnError:true)
			courseCM2006.addToAssignments(new Assignment(code: "TMA02")).save(failOnError:true)
			courseCM2006.addToAssignments(new Assignment(code: "TMA03")).save(failOnError:true)
			courseCM2007.addToAssignments(new Assignment(code: "TMA01")).save(failOnError:true)
			courseCM3010.addToAssignments(new Assignment(code: "TMA01")).save(failOnError:true)
			courseAA1003.addToAssignments(new Assignment(code: "TMA01")).save(failOnError:true)
			
			courseCMM511.addToAssignments(new Assignment(owner: "user", code: "TMA01")).save(failOnError:true)			
        }
    }
    
    
   /**
    * Called at boot time, and possibly at other times, to set up the configuration.
    * Primarily, this reads configuration data from wherever, and creates the
    * database tables that are needed to do sensible calculations.
    * 
    * NOTE: This doesn't change existing grades and categories. It is best to delete
    * them all before starting. Since submissions and comments key into the existing
    * grades and categories, mucking about with them is going to be an issue. 
    */
    private void initializeConfiguration() {
		
		Boolean trainingMode = grailsApplication.config.openmentor?.trainingMode
		if (trainingMode != null) {
			courseInfoService.trainingMode = trainingMode
		}
                
        List<String> grades = grailsApplication.config.openmentor.grades
        grades.each { value ->
            if (Grade.get(value) == null) {
                Grade instance = new Grade(id: value)
                instance.save(insert: true, failOnError: true, flush: true)
            }
        }
       
        Map<String, String> categoryBands = grailsApplication.config.openmentor.categoryBands       
        categoryBands.each { key, value ->
            if (Category.get(key) == null) {
                Category instance = new Category(id: key, band: value)
                instance.save(insert: true, failOnError: true, flush: true)
            }
        }
     
        // Nothing keys off weights, and they are more likely to change
        Weight.executeUpdate("delete Weight w")
        
        Map<String, Map<String, Double>> weights = grailsApplication.config.openmentor.weights
        weights.each { grade, values ->
            values.each { band, weight ->
                 Grade gradeInstance = Grade.get(grade)
                Float weightValue = new Float(weight.floatValue())
                Weight instance = new Weight(grade: gradeInstance, band: band, weight: weightValue)
                instance.save(insert: true, failOnError: true, flush: true)
            }
        }
    }
}
