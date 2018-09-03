package dsassignment5;

// Class to define custom exception to throw excepio when empty string is recieved as input
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
