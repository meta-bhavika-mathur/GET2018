package virtualcommandprompt;

public class DirectoryExistsException extends Exception
{
	String exceptionMessage;  
    /**
     * Initiates Empty String Received Exception when a directory already exists 
     * @param message, exception message
     */
    public DirectoryExistsException(String message)
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