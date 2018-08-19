package virtualcommandprompt;

public class DirectoryNameNotSpecifiedException extends Exception
{
	String exceptionMessage;  
    /**
     * Initiates Empty String Received Exception when directory name is not specified
     * @param message, exception message
     */
    public DirectoryNameNotSpecifiedException(String message)
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
