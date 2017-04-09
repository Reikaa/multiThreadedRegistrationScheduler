package registrationScheduler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileProcessor implements FileProcessorInterface {
	
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
    public FileProcessor(BufferedReader bufferedReaderIn){
    	Logger.writeMessage("In FileProcessor, BufferedReader constructor", Logger.DebugLevel.CONSTRUCTOR);
    	bufferedReader = bufferedReaderIn;
    	bufferedWriter = null;
    }
    
    public FileProcessor(BufferedWriter bufferedWriterIn){
    	Logger.writeMessage("In FileProcessor, BufferedWriter constructor", Logger.DebugLevel.CONSTRUCTOR);
    	bufferedWriter = bufferedWriterIn;
    	bufferedReader = null;
    }
    
    /**
     * @return String
     */
    @Override
    public synchronized String readLineFromFile(){
    	try {
    		return bufferedReader.readLine();
    	} catch (IOException exception) {
    		System.err.println("Error while reading from while.");
    		exception.printStackTrace();
    		System.exit(1);
    	} finally {
    		
    	}
    	return null;
    }
    
    @Override
    public synchronized void closeWriterFile() {
    	try {
    		bufferedWriter.close();
    	} catch (IOException exception) {
    		System.err.println("Error while closing file");
    		exception.printStackTrace();
    		System.exit(1);
    	}
    }

    @Override
	public synchronized void closeReaderFile() {
		try {
			bufferedReader.close();
		} catch (IOException exception) {
			System.err.println("Error while closing file.");
			exception.printStackTrace();
			System.exit(1);
		}
	}
    
    @Override
	public synchronized void writeLineToFile(String outStringIn) {
		try {
			bufferedWriter.write(outStringIn);
		} catch (IOException exception) {
			System.err.println("Error while writing to file.");
			exception.printStackTrace();
			System.exit(1);
		}
	}
}