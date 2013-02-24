package uk.org.openmentor.service

import uk.org.openmentor.courseinfo.Assignment
import uk.org.openmentor.courseinfo.Course;
import uk.org.openmentor.courseinfo.Student
import uk.org.openmentor.courseinfo.Tutor

class CourseInfoService {
	
	def currentUserService

	/**
	 * Used to select whether or not we should run in training mode
	 */
	boolean trainingMode = false
	
	boolean getAllowDeletion() {
		return trainingMode
	}
	
	/**
	 * Returns the a list of courses. 
	 * @param params a map of filtering features
	 * @return the list of courses
	 */
    List<Course> getCourses(Map params) {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'courseId'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Course.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		def courseList = criteria.list {
			if (username) {
				eq("owner", username)
			}
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}

		return courseList
    }
	
	/**
	 * Returns the course count
	 * @return
	 */
	Integer getCourseCount() {
		if (! trainingMode) {
			return Course.count()
		} else {
			def criteria = Course.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				if (username) {
					eq("owner", username)
				}
				projections {
					rowCount()
				}
			}
		}
	}
	
	/**
	 * Locates and returns a course by identifier
	 * @param courseId
	 * @return the course
	 */
	Course findCourse(String courseId) {
		if (! trainingMode) {
			return Course.findByCourseId(courseId)
		} else {
			def criteria = Course.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				eq("courseId", courseId)
				if (username) {
					eq("owner", username)
				}
			}
		}
	}

	/**
	 * Locates and returns a list of courses by like pattern
	 * @param courseId
	 * @return a list of courses
	 */
	List<Course> findCoursesLike(String courseId) {
		def criteria = Course.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		return criteria.list {
			if (username) {
				eq("owner", username)
			}
			ilike("courseId", courseId)
			order("courseId", "asc")
		}
	}

	/**
	 * Returns the a list of students. 
	 * @param params a map of filtering features
	 * @return the list of students
	 */
    List<Student> getStudents(Map params) {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'studentId'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Student.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		def studentList = criteria.list {
			if (username) {
				eq("owner", username)
			}
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}

		return studentList
    }
	
	/**
	 * Returns the student count
	 * @return
	 */
	Integer getStudentCount() {
		if (! trainingMode) {
			return Student.count()
		} else {
			def criteria = Student.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				if (username) {
					eq("owner", username)
				}
				projections {
					rowCount()
				}
			}
		}
	}
	
	/**
	 * Locates and returns a student by identifier
	 * @param studentId
	 * @return the student
	 */
	Student findStudent(String studentId) {
		
		if (! trainingMode) {
			return Student.findByStudentId(studentId)
		} else {
			def criteria = Student.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				eq("studentId", studentId)
				if (username) {
					eq("owner", username)
				}
			}
		}
	}
	
	/**
	 * Locates and returns a list of students by like pattern
	 * @param studentId
	 * @return a list of students
	 */
	List<Student> findStudentsLike(String studentId) {
		
		def criteria = Student.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		return criteria.list {
			if (username) {
				eq("owner", username)
			}
			ilike("studentId", studentId)
			order("studentId", "asc")
		}
	}

	/**
	 * Returns the a list of students. 
	 * @param params a map of filtering features
	 * @return the list of students
	 */
    List<Tutor> getTutors(Map params) {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'tutorId'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Tutor.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		def tutorList = criteria.list {
			if (username) {
				eq("owner", username)
			}
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}

		return tutorList
    }
	
	/**
	 * Returns the tutor count
	 * @return
	 */
	Integer getTutorCount() {
		if (! trainingMode) {
			return Tutor.count()
		} else {
			def criteria = Tutor.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				if (username) {
					eq("owner", username)
				}
				projections {
					rowCount()
				}
			}
		}
	}
	
	/**
	 * Locates and returns a tutor by identifier
	 * @param tutorId
	 * @return the tutor
	 */
	Tutor findTutor(String tutorId) {
		if (! trainingMode) {
			return Tutor.findByTutorId(tutorId)
		} else {
			def criteria = Tutor.createCriteria()
			String username = currentUserService.currentUserName()
			return criteria.get {
				eq("tutorId", tutorId)
				if (username) {
					eq("owner", username)
				}
			}
		}
	}
	
	/**
	 * Locates and returns a list of students by like pattern
	 * @param studentId
	 * @return a list of students
	 */
	List<Tutor> findTutorsLike(String tutorId) {
		
		def criteria = Tutor.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		return criteria.list {
			if (username) {
				eq("owner", username)
			}
			ilike("tutorId", tutorId)
			order("tutorId", "asc")
		}
	}
	
	/**
	 * Returns the a list of assignments for a given course. 
	 * @param params a map of filtering features
	 * @return the list of assignments
	 */
	List<Assignment> getAssignments(Course course, Map params) {
		assert course != null
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort ?: 'code'
		params.order = params.order ?: 'asc'
		params.offset = params.offset ?: '0'
				
		def criteria = Assignment.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		return criteria.list {
			if (username) {
				eq("owner", username)
			}
			eq("course", course)
			order(params.sort, params.order)
			maxResults(params.max)
			firstResult(Integer.parseInt(params.offset))
		}
    }
	
	/**
	 * Returns the course count of assignments for a given course
	 * @return
	 */
	Integer getAssignmentCount(Course course) {
		assert course != null
		def criteria = Assignment.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		return criteria.get {
			if (username) {
				eq("owner", username)
			}
			eq("course", course)
			projections {
				rowCount()
			}
		}
	}

	/**
	 * Locates and returns an assignment for a given course by identifier
	 * @param code
	 * @return the assignment
	 */
	Assignment findAssignment(Course course, String code) {
		assert course != null
		def criteria = Assignment.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		return criteria.get {
			eq("course", course)
			eq("code", code)
			if (username) {
				eq("owner", username)
			}
		}
	}
	
	/**
	 * Locates and returns an assignment for a given course by identifier
	 * @param code
	 * @return the assignment
	 */
	Assignment findAssignment(String courseId, String code) {
		assert courseId != null
		def criteria = Assignment.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		return criteria.get {
			course {
				eq("courseId", courseId)
				if (username) {
					eq("owner", username)
				}
			}
			eq("code", code)
			if (username) {
				eq("owner", username)
			}
		}
	}
	
	/**
	 * Locates and returns a list of assignments for a given course by like pattern
	 * @param code
	 * @return a list of assignments
	 */
	List<Assignment> findAssignmentsLike(Course course, String code) {
		
		def criteria = Assignment.createCriteria()
		String username = trainingMode ? currentUserService.currentUserName() : null
		
		return criteria.list {
			eq("course", course)
			ilike("code", code)
			if (username) {
				eq("owner", username)
			}
			order("code", "asc")
		}
	}
	
	/**
	 * Initializes a course with the appropriate owner, if required.
	 * @param course
	 */
	void initializeCourse(Course course) {
		String username = trainingMode ? currentUserService.currentUserName() : null
		if (username) {
			course.owner = username
		}
	}

	/**
	 * Initializes a student with the appropriate owner, if required.
	 * @param student
	 */
	void initializeStudent(Student student) {
		String username = trainingMode ? currentUserService.currentUserName() : null
		if (username) {
			student.owner = username
		}
	}

	/**
	 * Initializes a tutor with the appropriate owner, if required.
	 * @param tutor
	 */
	void initializeTutor(Tutor tutor) {
		String username = trainingMode ? currentUserService.currentUserName() : null
		if (username) {
			tutor.owner = username
		}
	}

	/**
	 * Initializes an assignment with the appropriate owner, if required.
	 * @param assignment
	 */
	void initializeAssignment(Assignment assignment) {
		String username = trainingMode ? currentUserService.currentUserName() : null
		if (username) {
			assignment.owner = username
		}
	}
}
