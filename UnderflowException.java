package Session1;

public class UnderflowException extends Exception
{
	String exceptionMessage;  
    /**
     * To initiate Underflow Exception when array is empty and no deletion can take place.
     * @param message, exception message
     */
    public UnderflowException(String message)
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
