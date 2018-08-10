package Session1;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestStackUsingLinkedList 
{   
    StackUsingLinkedList<Integer> stack = new StackUsingLinkedList<Integer>();
        
    /**
     * Test to push data into stack
     */
    @Test
    public void testPushDataIntoStackUsingLinkedList() 
    {   
       int dataToPush = 25;
       boolean expectedoutput = stack.push(dataToPush);       
       assertEquals(true, expectedoutput); 
    }
    
    /**
     * Test to pop data from stack
     */
    @Test
    public void testPopDataFromStack() 
    {
        try
        {
        	stack.push(25);
        	stack.push(50);
        	stack.push(75);
        	int poppedData = stack.pop();
        	assertEquals(75, poppedData );      	
        }
        catch(Exception exception)
        {
        	System.out.println(exception.getMessage());
        }
    }
    
    /**
     * Test to pop data from stack when stack is empty
     */
    @Test 
    public void testPopDataFromStackWhenEmpty() 
    { 
    	try
    	{
    		int poppedData = stack.pop();
    		assertEquals(75, poppedData ); 
    	}
    	catch(Exception exception)
    	{
    		System.out.println(exception.getMessage());
    	}
    }
    
    /**
     * To test for is stack empty
     */
    @Test
    public void testIsEmptyForStack() 
    {
        assertEquals( true , stack.isEmpty()); 
    }
    
    /**
     * To test get element on the top of the stack
     */
    @Test
    public void testGetElementOnTopStack() 
    {
        try
        {
        	stack.push(25); 
        	stack.push(50);
            stack.push(75);
            int elementOnTop = stack.top();        
            assertEquals( 75, elementOnTop );      	
        }
        catch(Exception exception)
        {	 
        	System.out.println(exception.getMessage());
        }      
    }
    
    /**
     * To test get element on the top of the stack when stack is empty
     */
    @Test 
    public void testGetElementOnTopStackWhenStackISEmpty() 
    {
        try
        {
        	int elementOnTop = stack.top();
            assertEquals( 75, elementOnTop );
        }
        catch(Exception exception)
        {	 
        	System.out.println(exception.getMessage());
        }  
    }
}
