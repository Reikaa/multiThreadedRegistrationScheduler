package registrationScheduler.threadMgmt;

import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;
import java.lang.InterruptedException;

import registrationScheduler.util.FileProcessor;
import registrationScheduler.util.FileDisplayInterface;
import registrationScheduler.util.StdoutDisplayInterface;
import registrationScheduler.util.Logger;
import registrationScheduler.util.Logger.DebugLevel;

/**
* This class is used to create group of threads.
*
* @author   Abhijeet Kulkarni
* @version  1.0
* @since    2017/03/07
*/
public class CreateWorkers implements CreateWorkersInterface {
	private FileProcessor prefFileProcessor;
	private FileProcessor addDropFileProcessor;
	private FileDisplayInterface fileDisplay;
	private StdoutDisplayInterface stdoutDisplay;

    public CreateWorkers(FileProcessor prefFileProcessorIn, FileProcessor addDropFileProcessorIn, FileDisplayInterface fileDisplayIn, StdoutDisplayInterface stdoutDisplayIn) {
    	Logger.writeMessage("Constructor of CreateWorkers class is called.", DebugLevel.CONSTRUCTOR);
    	prefFileProcessor = prefFileProcessorIn;
    	addDropFileProcessor = addDropFileProcessorIn;
    	fileDisplay = fileDisplayIn;
    	stdoutDisplay = stdoutDisplayIn;
    }

    /**
    * This method is used to create threads.
    *
    * @param    noOfThreads     number of threads to be created.
    */
    public void startWorkers(int noOfThreads){
    	List<Thread> threadsList = new ArrayList<Thread>();
    	for (int threadNum = 0; threadNum < noOfThreads; threadNum++) {
    		WorkerThread worker = new WorkerThread(prefFileProcessor, addDropFileProcessor, fileDisplay, stdoutDisplay);
    		Thread thread = new Thread(worker, String.valueOf(threadNum));
    		thread.start();
    		threadsList.add(thread);
    	}
    	for (Thread thread : threadsList) {
    		try {
    			thread.join();
    		} catch (InterruptedException exception) {
    			System.err.println("Error occured while joining thread.");
    			exception.printStackTrace();
    			System.exit(1);
    		}
    	}
    }
}