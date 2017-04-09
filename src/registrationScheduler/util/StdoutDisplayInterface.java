package registrationScheduler.util;

import registrationScheduler.store.Student;

public interface StdoutDisplayInterface {
    public void writeScheduleToStdout();
    public void putStudent(String studentName, Student newStudent);
    public void updateStudent(String studentNameIn, Student updatedStudent);
}