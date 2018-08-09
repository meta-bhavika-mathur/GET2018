package dsassignment5;

public class EmptyStringRecievedException extends Exception
{
	String exceptionMessage;  
    /**
     * Initiates Empty String Received Exception when empty string is received
     * @param message, exception message
     */
    public EmptyStringRecievedException(String message)
    {
    	exceptionMessage = message;
    }
    
    /**
     * Returns exception message that appears when exception occurs
     */
    public String getMessage() 
    {
        return exceptionMessage;
    }
}
