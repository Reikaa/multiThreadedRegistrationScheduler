package registrationScheduler.threadMgmt;

import registrationScheduler.objectPool.Course;
import registrationScheduler.objectPool.CoursePool;
import registrationScheduler.store.Student;
import registrationScheduler.util.FileDisplayInterface;
import registrationScheduler.util.FileProcessor;
import registrationScheduler.util.Logger;
import registrationScheduler.util.Logger.DebugLevel;
import registrationScheduler.util.StdoutDisplayInterface;

import static registrationScheduler.util.Constants.ADD;
import static registrationScheduler.util.Constants.ADD_DROP_INDEX;
import static registrationScheduler.util.Constants.COURSE_NAMES;
import static registrationScheduler.util.Constants.DROP;
import static registrationScheduler.util.Constants.MAX_COURSES;
import static registrationScheduler.util.Constants.MAX_PREFERENCE_SCORE;
import static registrationScheduler.util.Constants.ONE;
import static registrationScheduler.util.Constants.STUDENT_NAME_INDEX;
import static registrationScheduler.util.Constants.SUBJECTS_INDEX;
import static registrationScheduler.util.Constants.ZERO;

/**
* This class implments the 'run' method for threads created in CreateWorkers class.
*
* @author 	Abhijeet Kulkarni
* @version 	1.0
* @version 	2017/03/07
*/
public class WorkerThread implements Runnable  {

	private FileProcessor prefFileProcessor;
	private FileProcessor addDropFileProcessor;
	private FileDisplayInterface fileDisplay;
	private StdoutDisplayInterface stdoutDisplay;

    public WorkerThread(FileProcessor prefFileProcessorIn, FileProcessor addDropFileProcessorIn, FileDisplayInterface fileDisplayIn, StdoutDisplayInterface stdoutDisplayIn) {
    	Logger.writeMessage("Constructor of WorkerThread class is called", DebugLevel.CONSTRUCTOR);
    	prefFileProcessor = prefFileProcessorIn;
    	addDropFileProcessor = addDropFileProcessorIn;
    	fileDisplay = fileDisplayIn;
    	stdoutDisplay = stdoutDisplayIn;
    }
    
    public synchronized void run() {
    	Logger.writeMessage("Run method for " + Thread.currentThread().getName(), DebugLevel.IN_RUN);
    	String line = null;
    	while (null != (line = prefFileProcessor.readLineFromFile())) {
    		processPreferenceRequest(line);
		}
		while (null != (line = addDropFileProcessor.readLineFromFile())) {
			processAddDropRequest(line);
		}
    }
    
    /**
	* This method is used to process the course allocation request given the preference of a studennt.
	*
	* @param	studentPreferenceIn	string contaning name of the student and course preference.
    */
	private synchronized void processPreferenceRequest(String studentPreferenceIn) {
		String[] studentPreference = studentPreferenceIn.split("\\s+");
		Student newStudent = new Student(studentPreference[STUDENT_NAME_INDEX],0);
		for (int prefNum = ONE; prefNum <= MAX_COURSES; prefNum++) {
			Course newCourse = new Course(studentPreference[prefNum]);
			newStudent.getPreferenceList().add(newCourse);
		}
		for(int prefNum = ZERO; prefNum < MAX_COURSES; prefNum++) {
			// get current class size
			Course currentCourse = newStudent.getPreferenceList().get(prefNum);
			CoursePool pool = CoursePool.getInstance();
			if(pool.requestCourse(currentCourse))
				newStudent.getCourseSchedule().add(currentCourse);
			else {
				for(String course : COURSE_NAMES) {
					Course newCourse = new Course(course);
					if(!newStudent.getCourseSchedule().contains(newCourse) && pool.checkAvailability(newCourse)) {
						pool.requestCourse(newCourse);
						newStudent.getCourseSchedule().add(newCourse);
						return;
					}
				}
				newStudent.getCourseSchedule().add(null);
				return;
			}
		}
		fileDisplay.putStudent(newStudent.getStudentName(), newStudent);
		stdoutDisplay.putStudent(newStudent.getStudentName(), newStudent);
		Logger.writeMessage("New Entry added to Results data structure.", Logger.DebugLevel.IN_RESULTS);
		newStudent.calculatePreferenceScore();
	}
	
	/**
	* This method is used to process the add/drop request of a studennt.
	*
	* @param	addDropRequestIn	string contaning name of the student, add/drop indicator and list of course.
    */
	private synchronized void processAddDropRequest(String addDropRequestIn) {
		String[] addDropRequest = addDropRequestIn.split("\\s+");
		Student currentStudent = fileDisplay.getStudent(addDropRequest[STUDENT_NAME_INDEX]);
		switch(Integer.parseInt(addDropRequest[ADD_DROP_INDEX])) {
		case ADD:
			int addSubjectIndex = SUBJECTS_INDEX;
			while(addSubjectIndex < addDropRequest.length) {
				Course addCourse = new Course(addDropRequest[addSubjectIndex]);
				CoursePool pool = CoursePool.getInstance();
				Boolean validFlag = true;
				for(Course course : currentStudent.getCourseSchedule()) {
					if(course.getCourseName().equals(addCourse.getCourseName()))
						validFlag = false;
				}
				if(validFlag) {
					if(pool.requestCourse(addCourse)) {
						currentStudent.setPreferenceScore(currentStudent.getPreferenceScore() + ONE);
						currentStudent.getCourseSchedule().add(addCourse);
					}
				}
				addSubjectIndex++;
			}
			fileDisplay.updateStudent(currentStudent.getStudentName(), currentStudent);
			stdoutDisplay.updateStudent(currentStudent.getStudentName(), currentStudent);
			break;
			
		case DROP:
			int dropSubjectIndex = SUBJECTS_INDEX;
			int preferenceIndex = ZERO;
			while(dropSubjectIndex < addDropRequest.length) {
				Course dropCourse = new Course(addDropRequest[dropSubjectIndex]);
				CoursePool pool = CoursePool.getInstance();
				for(Course course : currentStudent.getCourseSchedule()) {
					if(course.getCourseName().equals(dropCourse.getCourseName())) {
						if(pool.releaseCourse(dropCourse)) {
							for(Course tempCourse : currentStudent.getCourseSchedule()) {
								if(tempCourse.getCourseName().equals(dropCourse.getCourseName()))
									break;
							}
							currentStudent.setPreferenceScore(currentStudent.getPreferenceScore() - (MAX_PREFERENCE_SCORE - preferenceIndex));
							preferenceIndex++;
							currentStudent.removeCourse(dropCourse);
						}
					}				
				}
				dropSubjectIndex++;
			}
			fileDisplay.updateStudent(currentStudent.getStudentName(), currentStudent);
			stdoutDisplay.updateStudent(currentStudent.getStudentName(), currentStudent);
			break;
		}
	}
}