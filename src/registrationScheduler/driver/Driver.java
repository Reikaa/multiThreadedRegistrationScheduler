package registrationScheduler.driver;

import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.NumberFormatException;

import registrationScheduler.util.Logger;
import registrationScheduler.util.StdoutDisplayInterface;
import registrationScheduler.store.Results;
import registrationScheduler.threadMgmt.CreateWorkers;
import registrationScheduler.util.FileProcessor;
import registrationScheduler.util.FileDisplayInterface;

import static registrationScheduler.util.Constants.PREF_FILE_INDEX;
import static registrationScheduler.util.Constants.ADD_DROP_FILE_INDEX;
import static registrationScheduler.util.Constants.THREAD_COUNT_INDEX;
import static registrationScheduler.util.Constants.DEBUG_LEVEL_INDEX;
import static registrationScheduler.util.Constants.MAX_THREAD_COUNT;
import static registrationScheduler.util.Constants.MIN_THREAD_COUNT;
import static registrationScheduler.util.Constants.MAX_DEBUG_LEVEL;
import static registrationScheduler.util.Constants.MIN_DEBUG_LEVEL;
import static registrationScheduler.util.Constants.OUTPUT_FILE_INDEX;

/**
*	The driver call implements an application that generates the schedule of
* courses for a student given the course preference and add/drop requests.
*
* @author 	Abhijeet Kulkarni
* @version	1.0
* @version	2017-03-07
*/
public class Driver{

	public static void main(String args[]) {
	    Driver dr = new Driver();
	    dr.validateArgs(args);
	    try {
	    	Logger.setDebugValue(Integer.parseInt(args[DEBUG_LEVEL_INDEX]));
		    FileProcessor prefFileProcessor = new FileProcessor(new BufferedReader(new FileReader(args[PREF_FILE_INDEX])));
		    FileProcessor addDropFileProcessor = new FileProcessor(new BufferedReader(new FileReader(args[ADD_DROP_FILE_INDEX])));
		    FileProcessor outputFileProcessor = new FileProcessor(new BufferedWriter(new FileWriter(args[OUTPUT_FILE_INDEX])));
		    FileDisplayInterface fileDisplay = new Results();
		    StdoutDisplayInterface stdoutDisplay = new Results();
		    CreateWorkers worker = new CreateWorkers(prefFileProcessor, addDropFileProcessor, fileDisplay, stdoutDisplay);
		    worker.startWorkers(Integer.parseInt(args[THREAD_COUNT_INDEX]));
			fileDisplay.writeSchedulesToFile(outputFileProcessor);
			stdoutDisplay.writeScheduleToStdout();
		    prefFileProcessor.closeReaderFile();
		    addDropFileProcessor.closeReaderFile();
		    outputFileProcessor.closeWriterFile();
	    } catch (IOException | NumberFormatException exception) {
	    	System.err.println("Number expected.");
	    	exception.printStackTrace();
	    	System.exit(1);
	    } finally {
	    	
	    }
	}
	
	/**
	 * Returns a boolean value 'true' if arguments to the program are in correct
	 * sequence and are of proper type.
	 *
	 * @param 	args	a string containig command line arguments.
	 * @return			boolean value indicating validation of command line argument
	*/
	private void validateArgs(String args[]){
		if(args.length==5){
			File prefFile = new File(args[PREF_FILE_INDEX]);
			File addDropFile = new File(args[ADD_DROP_FILE_INDEX]);
			if (prefFile.isFile() && prefFile.canRead()) {
				try {
					new FileInputStream(prefFile);
				} catch (IOException exception) {
					System.err.println("Cannot create file stream.");
					exception.printStackTrace();
					System.exit(1);
				}
			}
			if (addDropFile.isFile() && addDropFile.canRead()) {
				try {
					new FileInputStream(addDropFile);
				} catch (IOException exception) {
					System.err.println("Cannot create file stream.");
					exception.printStackTrace();
					System.exit(1);
				}
			}
			try{
				int numThreads = Integer.parseInt(args[THREAD_COUNT_INDEX]);
				if(!validRange(numThreads, MIN_THREAD_COUNT, MAX_THREAD_COUNT)) {
					System.err.println("Invalid nunmber of threads. Enter value between 1 and 4.");
					System.exit(1);
				}
				int debugLevel = Integer.parseInt(args[DEBUG_LEVEL_INDEX]);
				if(!validRange(debugLevel, MIN_DEBUG_LEVEL, MAX_DEBUG_LEVEL)) {
					System.err.println("Invalid debug level. Enter value between 0 and 4.");
					System.exit(1);
				}
			}catch(IllegalArgumentException ex){
				System.err.println("NumberFormatException-Cannot parse to integer.");
				ex.printStackTrace();
				System.exit(1);
			}
		}else{
			System.err.println("Invalid number of arguments. Expected [FIXME: provide details here]");
			System.exit(1);
		}
	}

	private boolean validRange(int num, int minValue, int maxValue) {
		if(num >= minValue && num <= maxValue)
			return true;
		return false;
	}
}