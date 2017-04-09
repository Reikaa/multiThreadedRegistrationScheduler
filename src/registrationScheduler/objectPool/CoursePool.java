package registrationScheduler.objectPool;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import registrationScheduler.objectPool.Course;
import registrationScheduler.util.Logger;

import static registrationScheduler.util.Constants.MAX_CLASS_SIZE;
import static registrationScheduler.util.Constants.COURSE_NAMES;

/**
*	This class is used as ObjectPool. This class can be used to request a course or
*	release a course.
*
* @author 	Abhijeet Kulkarni
* @version	1.0
* @version	2017-03-07
*/
public final class CoursePool implements ObjectPool{
	private static CoursePool coursePoolInstance = null;
	private static Map<String, Integer> courses;
	
	private CoursePool() {
		Logger.writeMessage("In CoursePool class constructor", Logger.DebugLevel.CONSTRUCTOR);
		courses = Collections.synchronizedMap(new HashMap<String, Integer>());
		for(String courseName : COURSE_NAMES) {
			courses.put(courseName, MAX_CLASS_SIZE);
		}
	}
	
	/**
	 * Returns an instance of singleton class CoursePool.
	 *
	 * @return	object of CoursePool
	 */
	public static synchronized CoursePool getInstance() {
		if(coursePoolInstance == null) {
			coursePoolInstance = new CoursePool();
		}
		return coursePoolInstance;
	}
	
	/**
	 * Returns a boolean value 'true' if new course can be allocated.
	 *
	 * @param 	courseIn 	course which is to be allocated.	
	 * @return	boolean value indicating if course can be allocated or not.
	 */
	@Override
	public boolean requestCourse(Course courseIn) {
		if(checkAvailability(courseIn)) {
			int newSize = courses.get(courseIn.getCourseName());
			courses.replace(courseIn.getCourseName(), --newSize);
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value 'true' if the size of the course is greater than zero.
	 *
	 * @param 	courseIn 	course whose size is to be checked.	
	 * @return	boolean value indicating if size of the course is greater than zero.
	 */
	@Override
	public boolean checkAvailability(Course courseIn) {
		if(courses.get(courseIn.getCourseName()) > 0)
			return true;
		return false;
	}

	/**
	 * Returns a boolean value 'true' if new course can be released.
	 *
	 * @param 	courseIn 	course which is to be released.	
	 * @return	boolean value indicating if course can be released or not.
	 */
	@Override
	public boolean releaseCourse(Course courseIn) {
		int currentSize = courses.get(courseIn.getCourseName());
		if(currentSize < MAX_CLASS_SIZE) {
			courses.replace(courseIn.getCourseName(), ++currentSize);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Course Pool [Size = " + Integer.toString(courses.size()) + "]";
	}
	
}