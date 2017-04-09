package registrationScheduler.util;

public class Logger{
    /*DEBUG_VALUE=4 [Print to stdout everytime a constructor is called]
      DEBUG_VALUE=3 [Print to stdout everytime a thread's run() method is called]
      DEBUG_VALUE=2 [Print to stdout everytime an entry is added to the Results data structure]
      DEBUG_VALUE=1 [Print to stdout the contents of the data structure in the store]
      DEBUG_VALUE=0 [No output should be printed from the application, except the line "The average preference value is X.Y" ]
    */

  public static enum DebugLevel {RELEASE, FROM_RESULTS, IN_RESULTS, IN_RUN, CONSTRUCTOR                                  };

  private static DebugLevel debugLevel;

  public static void setDebugValue (int levelIn) {
    switch (levelIn) 
    {
    	case 1:
    		debugLevel = DebugLevel.FROM_RESULTS;
    		break;
    	
    	case 2:
    		debugLevel = DebugLevel.IN_RESULTS;
    		break;
    		
    	case 3:
    		debugLevel = DebugLevel.IN_RUN;
    		break;
    		
    	case 4: 
		   	debugLevel = DebugLevel.CONSTRUCTOR; 
		   	break;
	   // add code for other cases   		
    	case 0: 
		   	debugLevel = DebugLevel.RELEASE; 
		   	break;
    }
  }

  public static void setDebugValue (DebugLevel levelIn) {
	  debugLevel = levelIn;
  }

  // @return None
  public static void writeMessage (String  message, DebugLevel levelIn ) {
	  if (levelIn == debugLevel)
		  System.out.println(message);
  }
  
  public static boolean checkDebugLevel(DebugLevel levelIn) {
	  if(levelIn == debugLevel)
		  return true;
	  return false;
  }

  /**
	*  @return String
	*/
  public String toString() {
	  return "Debug Level is " + debugLevel;
  }
}