package registrationScheduler.objectPool;

import registrationScheduler.util.Logger;

public class Course {
	private String courseName;
	
	public Course(String courseNameIn) {
		Logger.writeMessage("In Course class constructor", Logger.DebugLevel.CONSTRUCTOR);
		courseName = courseNameIn;
	}
	
	public void setCourseName(String courseNameIn) {
		courseName = courseNameIn;
	}
	
	public String getCourseName() {
		return courseName;
	}

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + "]";
	}
}