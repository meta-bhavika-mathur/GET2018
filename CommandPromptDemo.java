package virtualcommandprompt;

// To execute Virtual Command Prompt
public class CommandPromptDemo 
{
	public static void main(String[] args)
    {
        try
        {
            VirtualCommandPrompt.displayPrompt();
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
}