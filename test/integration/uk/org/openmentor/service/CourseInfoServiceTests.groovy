package uk.org.openmentor.service

import grails.test.mixin.*
import org.junit.*
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.plugin.springsecurity.SpringSecurityUtils;
import uk.org.openmentor.courseinfo.Assignment
import uk.org.openmentor.courseinfo.Course
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.courseinfo.Tutor

class CourseInfoServiceTests extends GroovyTestCase {
	
	def courseInfoService = new CourseInfoService()
	
	def withAuthenticatedTrainingMode(String user, Closure c) {
		SpringSecurityUtils.doWithAuth(user) {
			def oldTrainingMode = courseInfoService.trainingMode
			courseInfoService.trainingMode = true
			try {
				c()
			} finally {
				courseInfoService.trainingMode = false
			}
		}
	}

	/**
	 * Check the course count returned from the service
	 */
	void testCourseCount() {
		
		def result = courseInfoService.getCourseCount()
		assertEquals 6, result
	}

	/**
	 * Check the course count with a given logged in user
	 * when we are in training mode
	 */
	void testAuthenticatedCourseCount() {

		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getCourseCount()
			assertEquals 1, result
		}
	}
	
	/**
	 * Check the course count with a given logged in user
	 * when we are in training mode
	 */
	void testAuthenticatedCourseCountAnother() {

		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getCourseCount()
			assertEquals 0, result
		}
	}
	
	/**
	 * Check the course list returned from the service
	 */
	void testCourseList() {
		
		def result = courseInfoService.getCourses([:])
		assertEquals 6, result.size()
	}
	
	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedCourseList() {

		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getCourses([:])
			assertEquals 1, result.size()
		}
	}
	
	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedCourseListAnother() {

		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getCourses([:])
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Check the course list returned from the service
	 */
	void testFindCourse() {
		
		def result = courseInfoService.findCourse("CM2006")
		assertEquals "CM2006", result.courseId
	}

	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedFindCourse() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findCourse("CM2006")
			assertEquals null, result
		}
	}

	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedFindAnotherCourse() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findCourse("CMM511")
			assertEquals "CMM511", result?.courseId
		}
	}

	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedFindAnotherCourseAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findCourse("CMM511")
			assertEquals null, result
		}
	}

	/**
	 * Check the course list returned from the service
	 */
	void testFindCoursesLike() {
		
		def result = courseInfoService.findCoursesLike("CM%")
		assertEquals 5, result.size()
	}
	
	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedFindCoursesLike() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findCoursesLike("CM%")
			assertEquals 1, result.size()
		}
	}
	
	/**
	 * Check the course list returned from the service when we
	 * are authenticated and in training mode
	 */
	void testAuthenticatedFindCoursesLikeAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findCoursesLike("CM%")
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Find a student
	 */
	void testFindStudent() {
		def result = courseInfoService.findStudent("09000231")
		assertEquals "09000231", result.studentId
		assertEquals "Gwenda", result.givenName		
	}

	/**
	 * Find a student when we are authenticated and in training mode
	 */
	void testAuthenticatedFindStudent() {
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findStudent("09000238")
			assertEquals "09000238", result.studentId
			assertEquals "Quincy", result.givenName		
		}
	}

	/**
	 * Fail to find someone else's student when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAnotherStudent() {
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findStudent("09000238")
			assertEquals null, result
		}
	}

	/**
	 * Check the student count returned from the service
	 */
	void testStudentCount() {
		
		def result = courseInfoService.getStudentCount()
		assertEquals 9, result
	}

	/**
	 * Check the student count returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedStudentCount() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getStudentCount()
			assertEquals 1, result
		}
	}

	/**
	 * Check the student count returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedStudentCountAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getStudentCount()
			assertEquals 0, result
		}
	}

	/**
	 * Check the student list returned from the service
	 */
	void testStudentList() {
		
		def result = courseInfoService.getStudents([:])
		assertEquals 9, result.size()
	}
	
	/**
	 * Check the student list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedStudentList() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getStudents([:])
			assertEquals 1, result.size()
		}
	}
	
	/**
	 * Check the student list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedStudentListAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getStudents([:])
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Check the student list returned from the service
	 */
	void testFindStudentsLike() {
		
		def result = courseInfoService.findStudentsLike("%231")
		assertEquals 1, result.size()
	}
	
	/**
	 * Check the student list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindStudentsLike() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findStudentsLike("%238")
			assertEquals 1, result.size()
		}
	}
	
	/**
	 * Check the student list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindStudentsLikeAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findStudentsLike("%238")
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Find a tutor
	 */
	void testFindTutor() {
		def result = courseInfoService.findTutor("M4000061")
		assertEquals "M4000061", result.tutorId
		assertEquals "Zena", result.givenName		
	}

	/**
	 * Find a tutor when we are authenticated and in training mode
	 */
	void testAuthenticatedFindTutor() {
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findTutor("M4000064")
			assertEquals "M4000064", result.tutorId
			assertEquals "Monroe", result.givenName
		}	
	}

	/**
	 * Fail to find a tutor when we are authenticated and in training mode
	 */
	void testAuthenticatedFindTutorAnother() {
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findTutor("M4000064")
			assertEquals null, result
		}	
	}

	/**
	 * Check the tutor count returned from the service
	 */
	void testTutorCount() {
		
		def result = courseInfoService.getTutorCount()
		assertEquals 5, result
	}

	/**
	 * Check the tutor count returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedTutorCount() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getTutorCount()
			assertEquals 1, result
		}
	}

	/**
	 * Check the tutor count returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedTutorCountAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getTutorCount()
			assertEquals 0, result
		}
	}

	/**
	 * Check the tutor list returned from the service
	 */
	void testTutorList() {
		
		def result = courseInfoService.getTutors([:])
		assertEquals 5, result.size()
	}

	/**
	 * Check the tutor list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedTutorList() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.getTutors([:])
			assertEquals 1, result.size()
		}
	}

	/**
	 * Check the tutor list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedTutorListAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.getTutors([:])
			assertEquals 0, result.size()
		}
	}

	/**
	 * Check the tutor list returned from the service
	 */
	void testFindTutorsLike() {
		
		def result = courseInfoService.findTutorsLike("M4%")
		assertEquals 5, result.size()
	}	

	/**
	 * Check the tutor list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindTutorsLike() {
		
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findTutorsLike("M4%")
			assertEquals 1, result.size()
		}
	}	

	/**
	 * Check the tutor list returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindTutorsLikeAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findTutorsLike("M4%")
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Check the assignment list returned from the service
	 * for a given course.
	 */
	void testAssignmentList() {
		
		def course = Course.findByCourseId("CM2006")
		def result = courseInfoService.getAssignments(course, [:])
		assertEquals 3, result.size()
	}

	/**
	 * Check the assignment list returned from the service
	 * for a given course when we are authenticated and in training mode
	 */
	void testAuthenticatedAssignmentList() {
		
		withAuthenticatedTrainingMode "user", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.getAssignments(course, [:])
			assertEquals 2, result.size()
		}
	}

	/**
	 * Check the assignment list returned from the service
	 * for a given course when we are authenticated and in training mode
	 */
	void testAuthenticatedAssignmentListAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.getAssignments(course, [:])
			assertEquals 0, result.size()
		}
	}

	/**
	 * Check the assignment count returned from the service
	 * for a given course
	 */
	void testAssignmentCount() {
		
		def course = Course.findByCourseId("CM2006")
		def result = courseInfoService.getAssignmentCount(course)
		assertEquals 3, result
	}

	/**
	 * Check the assignment count returned from the service
	 * for a given course
	 */
	void testAuthenticatedAssignmentCount() {
		
		withAuthenticatedTrainingMode "user", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.getAssignmentCount(course)
			assertEquals 2, result
		}
	}

	/**
	 * Check the assignment count returned from the service
	 * for a given course
	 */
	void testAuthenticatedAssignmentCountAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.getAssignmentCount(course)
			assertEquals 0, result
		}
	}

	/**
	 * Find an assignment for a given course 
	 */
	void testFindAssignment() {
		def course = Course.findByCourseId("CM2006")
		def result = courseInfoService.findAssignment(course, "TMA03")
		assertEquals "TMA03", result.code
	}

	/**
	 * Find an assignment for a given course when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignment() {
		withAuthenticatedTrainingMode "user", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.findAssignment(course, "TMA01")
			assertEquals "TMA01", result.code
		}
	}

	/**
	 * Fail to find someone else's assignment when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignmentAnother() {
		withAuthenticatedTrainingMode "admin", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.findAssignment(course, "TMA01")
			assertEquals null, result
		}
	}

	/**
	 * Find an assignment for a given course by course name
	 */
	void testFindAssignmentByCourseName() {
		def result = courseInfoService.findAssignment("CM2006", "TMA03")
		assertEquals "TMA03", result.code
	}

	/**
	 * Find an assignment for a given course when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignmentByCourseName() {
		withAuthenticatedTrainingMode "user", {
			def result = courseInfoService.findAssignment("CMM511", "TMA01")
			assertEquals "TMA01", result.code
		}
	}

	/**
	 * Fail to find someone else's assignment when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignmentByCourseNameAnother() {
		withAuthenticatedTrainingMode "admin", {
			def result = courseInfoService.findAssignment("CMM511", "TMA01")
			assertEquals null, result
		}
	}

	/**
	 * Check the assignment list for a given course returned from the service
	 */
	void testFindAssignmentsLike() {
		
		def course = Course.findByCourseId("CM2006")
		def result = courseInfoService.findAssignmentsLike(course, "TM%")
		assertEquals 3, result.size()
	}	

	/**
	 * Check the assignment list for a given course returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignmentsLike() {
		
		withAuthenticatedTrainingMode "user", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.findAssignmentsLike(course, "TM%")
			assertEquals 2, result.size()
		}
	}	

	/**
	 * Check the assignment list for a given course returned from the service
	 * when we are authenticated and in training mode
	 */
	void testAuthenticatedFindAssignmentsLikeAnother() {
		
		withAuthenticatedTrainingMode "admin", {
			def course = Course.findByCourseId("CMM511")
			def result = courseInfoService.findAssignmentsLike(course, "TM%")
			assertEquals 0, result.size()
		}
	}
	
	/**
	 * Test the initialization of a course.
	 */
	void testInitializeCourse() {
		def course = new Course()
		courseInfoService.initializeCourse(course)
		assertEquals null, course.owner
	}
	
	/**
	 * Test the initialization of a course when authenticated and
	 * in training mode.	
	 */
	void testAuthenticatedInitializeCourse() {
		withAuthenticatedTrainingMode "admin", {
			def course = new Course()
			courseInfoService.initializeCourse(course)
			assertEquals "admin", course.owner
		}
	}

	/**
	 * Test the initialization of a student.
	 */
	void testInitializeStudent() {
		def student = new Student()
		courseInfoService.initializeStudent(student)
		assertEquals null, student.owner
	}
	
	/**
	 * Test the initialization of a student when authenticated and
	 * in training mode.	
	 */
	void testAuthenticatedInitializeStudent() {
		withAuthenticatedTrainingMode "admin", {
			def student = new Student()
			courseInfoService.initializeStudent(student)
			assertEquals "admin", student.owner
		}
	}

	/**
	 * Test the initialization of a tutor.
	 */
	void testInitializeTutor() {
		def tutor = new Tutor()
		courseInfoService.initializeTutor(tutor)
		assertEquals null, tutor.owner
	}
	
	/**
	 * Test the initialization of a tutor when authenticated and
	 * in training mode.	
	 */
	void testAuthenticatedInitializeTutor() {
		withAuthenticatedTrainingMode "admin", {
			def tutor = new Tutor()
			courseInfoService.initializeTutor(tutor)
			assertEquals "admin", tutor.owner
		}
	}

	/**
	 * Test the initialization of a tutor.
	 */
	void testInitializeAssignment() {
		def assignment = new Assignment()
		courseInfoService.initializeAssignment(assignment)
		assertEquals null, assignment.owner
	}
	
	/**
	 * Test the initialization of a tutor when authenticated and
	 * in training mode.	
	 */
	void testAuthenticatedInitializeAssignment() {
		withAuthenticatedTrainingMode "admin", {
			def assignment = new Assignment()
			courseInfoService.initializeAssignment(assignment)
			assertEquals "admin", assignment.owner
		}
	}
	
	/**
	 * Test deletion of a course.
	 */
	void testDeleteCourse() {
		def course = courseInfoService.findCourse("CM2006")
		courseInfoService.deleteCourse(course)
		def finder = courseInfoService.findCourse("CM2006")
		assertEquals(null, finder)
	}

	/**
	 * Test deletion of a course when authenticated and
	 * in training mode.
	 */
	void testAuthenticatedDeleteCourse() {
		withAuthenticatedTrainingMode "user", {
			def course = courseInfoService.findCourse("CMM511")
			courseInfoService.deleteCourse(course)
			def finder = courseInfoService.findCourse("CMM511")
			assertEquals(null, finder)
		}
	}

	/**
	 * Test deletion of a student.
	 */
	void testDeleteStudent() {
		def student = courseInfoService.findStudent("09000237")
		courseInfoService.deleteStudent(student)
		def finder = courseInfoService.findStudent("09000237")
		assertEquals(null, finder)
	}

	/**
	 * Test deletion of a student when authenticated and
	 * in training mode.
	 */
	void testAuthenticatedDeleteStudent() {
		withAuthenticatedTrainingMode "user", {
			def student = courseInfoService.findStudent("09000238")
			courseInfoService.deleteStudent(student)
			def finder = courseInfoService.findStudent("09000238")
			assertEquals(null, finder)
		}
	}

	/**
	 * Test deletion of a tutor.
	 */
	void testDeleteTutor() {
		def tutor = courseInfoService.findTutor("M4000061")
		courseInfoService.deleteTutor(tutor)
		def finder = courseInfoService.findTutor("M4000061")
		assertEquals(null, finder)
	}

	/**
	 * Test deletion of a tutor when authenticated and
	 * in training mode.
	 */
	void testAuthenticatedDeleteTutor() {
		withAuthenticatedTrainingMode "user", {
			def tutor = courseInfoService.findTutor("M4000064")
			courseInfoService.deleteTutor(tutor)
			def finder = courseInfoService.findTutor("M4000064")
			assertEquals(null, finder)
		}
	}

	/**
	 * Test deletion of a assignment.
	 */
	void testDeleteAssignment() {
		def course = courseInfoService.findCourse("CM2006")
		def assignment = courseInfoService.findAssignment(course, "TMA03")
		courseInfoService.deleteAssignment(assignment)
		def finder = courseInfoService.findAssignment(course, "TMA03")
		assertEquals(null, finder)
	}

	/**
	 * Test deletion of a assignment when authenticated and
	 * in training mode.
	 */
	void testAuthenticatedDeleteAssignment() {
		withAuthenticatedTrainingMode "user", {
			def course = courseInfoService.findCourse("CMM511")
			def assignment = courseInfoService.findAssignment(course, "TMA01")
			assertNotNull(assignment)
			courseInfoService.deleteAssignment(assignment)
			def finder = courseInfoService.findAssignment(course, "TMA01")
			assertEquals(null, finder)
		}
	}
}