package registrationScheduler.util;

import registrationScheduler.store.Student;

public interface FileDisplayInterface {

	public void writeSchedulesToFile(FileProcessor outputFileProcessor);
	public void putStudent(String studentName, Student newStudent);
	public Student getStudent(String string);
	public void updateStudent(String studentName, Student updatedStudent);
}