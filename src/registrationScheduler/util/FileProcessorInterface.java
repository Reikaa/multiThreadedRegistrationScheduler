package registrationScheduler.util;

public interface FileProcessorInterface {
	public String readLineFromFile();
	public void closeWriterFile();
	public void closeReaderFile();
	public void writeLineToFile(String outStringIn);
}