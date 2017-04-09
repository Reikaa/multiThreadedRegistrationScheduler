package registrationScheduler.store;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import registrationScheduler.objectPool.Course;
import registrationScheduler.util.FileDisplayInterface;
import registrationScheduler.util.FileProcessor;
import registrationScheduler.util.Logger;
import registrationScheduler.util.StdoutDisplayInterface;

import static registrationScheduler.util.Constants.MAX_STUDENTS;
import static registrationScheduler.util.Constants.NEWLINE;
import static registrationScheduler.util.Constants.ZERO;
import static registrationScheduler.util.Constants.SPACE;

/**
*   This class holds the data structure used to store course allocations.
*
*   @author     Abhijeet Kulkarni
*   @version    1.0
*   @since      2017/03/07
*/
public class Results implements StdoutDisplayInterface, FileDisplayInterface {

	private Map<String, Student> studentsRecords;
    
	public Results() {
		Logger.writeMessage("In Results class constructor.", Logger.DebugLevel.CONSTRUCTOR);
		studentsRecords = Collections.synchronizedMap(new HashMap<String, Student>());
	}
	
	@Override
	public void putStudent(String studentNameIn, Student studentIn) {
		studentsRecords.put(studentNameIn, studentIn);
	}
	
	public Student getStudent(String studentNameIn) {
		return studentsRecords.get(studentNameIn);
	}
	
	@Override
	public void updateStudent(String studentNameIn, Student updatedStudentIn) {
		studentsRecords.replace(studentNameIn, updatedStudentIn);
	}
    public Map<String, Student> getStudentsRecords() {
		return studentsRecords;
	}

	public void setStudentsRecords(Map<String, Student> studentsRecordsIn) {
		studentsRecords = studentsRecordsIn;
	}

    /**
    *   This method is used to print the scheduled to standard output.
    */
	@Override
    public synchronized void writeScheduleToStdout() {
		StringBuilder outputString;
    	double averagePreferenceScore = ZERO;
    	Map<String, Student> studentsRecordsTree = new TreeMap<String, Student>(
    			new Comparator<String>() {
    				@Override
    				public int compare(String o1, String o2) {
    					String[] newO1 = o1.split("");
    					String[] newO2 = o2.split("");
    					StringBuilder builder = new StringBuilder();
    					for(int i=8 ; i < newO1.length; i++) {
    						builder.append(newO1[i]);
    					}
    					Integer intO1 = Integer.parseInt(builder.toString());
    					builder = new StringBuilder();
    					for(int i=8 ; i < newO2.length; i++) {
    						builder.append(newO2[i]);
    					}
    					Integer intO2 = Integer.parseInt(builder.toString());
    					return intO1.compareTo(intO2);
    				}
		});
    	studentsRecordsTree.putAll(studentsRecords);
	   	for(Map.Entry<String, Student> student : studentsRecordsTree.entrySet()) {
	   		outputString = new StringBuilder();
	   		averagePreferenceScore = averagePreferenceScore + student.getValue().getPreferenceScore();
	   		outputString.append(student.getValue().getStudentName());
	    	outputString.append(SPACE);
	    	for(Course studentCourse : student.getValue().getCourseSchedule()) {
	    		outputString.append(studentCourse.getCourseName());
	    		outputString.append(SPACE);
    		}
	   		outputString.append(Double.toString(student.getValue().getPreferenceScore()));
	   		outputString.append(SPACE);
	   		if(Logger.checkDebugLevel(Logger.DebugLevel.FROM_RESULTS)) {
	   			System.out.println(outputString.toString());
	    	}
	    }
	    System.out.println("Average preference_score is: " + Double.toString(averagePreferenceScore/MAX_STUDENTS));
    }
    
    /**
    * This method is used to write schedules to an output file.
    *
    * @param    outputFileProcessor
    */
    @Override
    public synchronized void writeSchedulesToFile(FileProcessor outputFileProcessorIn){
    	Logger.writeMessage("Method writeSchedulesToFile of Results is called.", Logger.DebugLevel.IN_RESULTS);
    	double averagePreferenceScore = ZERO;
    	Map<String, Student> studentsRecordsTree = new TreeMap<String, Student>(
    			new Comparator<String>() {
    				@Override
    				public int compare(String o1, String o2) {
    					String[] newO1 = o1.split("");
    					String[] newO2 = o2.split("");
    					StringBuilder builder = new StringBuilder();
    					for(int i=8 ; i < newO1.length; i++) {
    						builder.append(newO1[i]);
    					}
    					Integer intO1 = Integer.parseInt(builder.toString());
    					builder = new StringBuilder();
    					for(int i=8 ; i < newO2.length; i++) {
    						builder.append(newO2[i]);
    					}
    					Integer intO2 = Integer.parseInt(builder.toString());
    					return intO1.compareTo(intO2);
    				}
		});
    	studentsRecordsTree.putAll(studentsRecords);
    	for(Map.Entry<String, Student> student : studentsRecordsTree.entrySet()) {
    		averagePreferenceScore = averagePreferenceScore + student.getValue().getPreferenceScore();
    		outputFileProcessorIn.writeLineToFile(student.getValue().getStudentName() + " ");
    		for(Course studentCourse : student.getValue().getCourseSchedule()) {
    			outputFileProcessorIn.writeLineToFile(studentCourse.getCourseName() + " ");
    		}
    		outputFileProcessorIn.writeLineToFile(Double.toString(student.getValue().getPreferenceScore()) + " ");
    		outputFileProcessorIn.writeLineToFile(NEWLINE);
    	}
    	outputFileProcessorIn.writeLineToFile("Average preference_score is: " + Double.toString(averagePreferenceScore/MAX_STUDENTS));
    }
    
    @Override
    public String toString() {
    	return "Results [ studentsRecords = " + Integer.toString(studentsRecords.size()) + "]";
    }
}