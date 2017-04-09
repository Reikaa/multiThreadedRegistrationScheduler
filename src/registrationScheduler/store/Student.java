package registrationScheduler.store;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import registrationScheduler.objectPool.Course;
import registrationScheduler.util.Logger;

import static registrationScheduler.util.Constants.ZERO;
import static registrationScheduler.util.Constants.MAX_COURSES;
import static registrationScheduler.util.Constants.MAX_PREFERENCE_SCORE;

public class Student {
	private String studentName;
	private int registrationTime;
	private List<Course> preferenceList;
	private List<Course> courseSchedule;
	private double preferenceScore;

	
	public Student(String studentNameIn, int studentNumberIn){
		Logger.writeMessage("In Student class constructor", Logger.DebugLevel.CONSTRUCTOR);
		studentName = studentNameIn;
		preferenceList = new CopyOnWriteArrayList<Course>();
		courseSchedule = new CopyOnWriteArrayList<Course>();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentNameIn) {
		studentName = studentNameIn;
	}

	public List<Course> getPreferenceList() {
		return preferenceList;
	}

	public synchronized void setPreferenceList(List<Course> preferenceListIn) {
		preferenceList = preferenceListIn;
	}

	public synchronized void setCourseSchedule(List<Course> courseScheduleIn) {
		courseSchedule = courseScheduleIn;
	}

	public synchronized List<Course> getCourseSchedule() {
		return courseSchedule;
	}

	@Override
	public String toString() {
		return "Student Name = " + studentName + ", Registration Time = " + registrationTime + ", Preference List = "
				+ preferenceList + ", Course Schedule = " + courseSchedule;
	}

	public double getPreferenceScore() {
		return preferenceScore;
	}

	public void setPreferenceScore(double preferenceScoreIn) {
		preferenceScore = preferenceScoreIn;
	}

	public void calculatePreferenceScore() {
		for(int index = ZERO; index < MAX_COURSES; index++) {
			if(preferenceList.get(index).getCourseName().equals(courseSchedule.get(index).getCourseName()))
				preferenceScore = preferenceScore + (MAX_PREFERENCE_SCORE - index);
		}
	}
	
	public void removeCourse(Course courseToDelete) {
		for(Course course : courseSchedule) {
			if(courseToDelete.getCourseName().equals(course.getCourseName()))
				courseSchedule.remove(course);
		}
	}
}