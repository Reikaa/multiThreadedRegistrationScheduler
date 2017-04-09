package registrationScheduler.objectPool;

public interface ObjectPool {
	public boolean requestCourse(Course courseIn);
	public boolean checkAvailability(Course courseIn);
	public boolean releaseCourse(Course courseIn);
}