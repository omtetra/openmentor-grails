import uk.org.openmentor.config.Grade
import uk.org.openmentor.config.Weight
import uk.org.openmentor.config.Category
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.courseinfo.Tutor
import uk.org.openmentor.data.Assignment
import uk.org.openmentor.auth.Role
import uk.org.openmentor.auth.User
import uk.org.openmentor.auth.UserRole
import uk.org.openmentor.system.DataSourceUtils
import grails.util.Environment
import org.apache.log4j.Logger

class BootStrap {
	
	static final Logger log = Logger.getLogger(this)
	
	def springSecurityService
	def grailsApplication

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

		def adminUser = User.findByUsername('admin') ?: new User(
			username: 'admin',
			password: springSecurityService.encodePassword('admin'),
			enabled: true).save(failOnError: true)
			
		if (! adminUser.authorities.contains(adminRole)) {
			UserRole.create adminUser, adminRole
		}
	}
	
	/**
	 * When run in test or development mode, create a few courses, students, and 
	 * tutors, ready to test out the rest of the system. 
	 */
	private void seedTestData() {
		// Seed only when there's no data
		if (Course.count() == 0) {
			
			def courseCM2006 = new Course(courseId: "CM2006", courseTitle: "Interface Design")
			courseCM2006.save(failOnError:true)

			def courseCM2007 = new Course(courseId: "CM2007", courseTitle: "Intranet Systems Development")
			courseCM2007.save(failOnError:true)

			def courseCM3010 = new Course(courseId: "CM3010", courseTitle: "Information Retrieval")
			courseCM3010.save(failOnError:true)

			def courseAA1003 = new Course(courseId: "AA1003", courseTitle: "Multimedia Programming")
			courseAA1003.save(failOnError:true)

			def student09000231 = new Student(studentId: "09000231", givenName: "Gwenda", familyName: "Blane")
			student09000231.save(failOnError:true)

			def student09000232 = new Student(studentId: "09000232", givenName: "Fred", familyName: "Zucker")
			student09000232.save(failOnError:true)

			def student09000233 = new Student(studentId: "09000233", givenName: "Caitlyn", familyName: "Respass")
			student09000233.save(failOnError:true)

			def student09000234 = new Student(studentId: "09000234", givenName: "Luke", familyName: "Naccarato")
			student09000234.save(failOnError:true)

			def student09000235 = new Student(studentId: "09000235", givenName: "Pierre", familyName: "Busse")
			student09000235.save(failOnError:true)

			def student09000236 = new Student(studentId: "09000236", givenName: "Ami", familyName: "Montalvo")
			student09000236.save(failOnError:true)

			def student09000237 = new Student(studentId: "09000237", givenName: "Jackie", familyName: "Nicolas")
			student09000237.save(failOnError:true)

			def tutorM4000061 = new Tutor(tutorId: "M4000061", givenName: "Zena", familyName: "Beatrice")
			tutorM4000061.save(failOnError:true)

			def tutorM4000062 = new Tutor(tutorId: "M4000062", givenName: "Levi", familyName: "Evert")
			tutorM4000061.save(failOnError:true)

			def tutorM4000063 = new Tutor(tutorId: "M4000063", givenName: "Jeanie", familyName: "Denman")
			tutorM4000061.save(failOnError:true)
			
			courseCM2006.addToStudents(student09000231)
			courseCM2006.addToStudents(student09000232)
			courseCM2006.addToStudents(student09000233)
			courseCM2006.addToStudents(student09000234)
			courseCM2006.addToStudents(student09000235)
			courseCM2006.addToStudents(student09000237)
			courseCM2006.addToTutors(tutorM4000061)
			courseCM2006.addToTutors(tutorM4000062)
			courseCM2006.addToTutors(tutorM4000063)
			courseCM2006.save(failOnError:true)
			
			courseCM2007.addToStudents(student09000231)
			courseCM2007.addToStudents(student09000232)
			courseCM2007.addToStudents(student09000234)
			courseCM2007.addToStudents(student09000236)
			courseCM2007.addToTutors(tutorM4000062)
			courseCM2007.save(failOnError:true)
			
			courseCM3010.addToStudents(student09000233)
			courseCM3010.addToStudents(student09000234)
			courseCM3010.addToStudents(student09000235)
			courseCM3010.addToTutors(tutorM4000063)
			courseCM3010.save(failOnError:true)
			
			courseAA1003.addToStudents(student09000232)
			courseAA1003.addToStudents(student09000233)
			courseAA1003.addToStudents(student09000234)
			courseAA1003.addToStudents(student09000236)
			courseAA1003.addToStudents(student09000237)
			courseAA1003.addToTutors(tutorM4000061)
			courseAA1003.save(failOnError:true)
			
			def assignment1 = new Assignment(courseId: courseCM2006.courseId, code: "TMA01").save(failOnError:true)
			def assignment2 = new Assignment(courseId: courseCM2006.courseId, code: "TMA02").save(failOnError:true)
			def assignment3 = new Assignment(courseId: courseCM2006.courseId, code: "TMA03").save(failOnError:true)
			def assignment4 = new Assignment(courseId: courseCM2007.courseId, code: "TMA01").save(failOnError:true)
			def assignment5 = new Assignment(courseId: courseCM3010.courseId, code: "TMA01").save(failOnError:true)
			def assignment6 = new Assignment(courseId: courseAA1003.courseId, code: "TMA01").save(failOnError:true)
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
