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
        stack.push(25);
        stack.push(50);
        stack.push(75);
        
        int poppedData = stack.pop();
        assertEquals(75, poppedData );
    }
    
    /**
     * Test to pop data from stack when stack is empty
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testPopDataFromStackWhenEmpty() 
    { 
        int poppedData = stack.pop();
        assertEquals(75, poppedData ); 
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
        stack.push(25);
        stack.push(50);
        stack.push(75);
        int elementOnTop = stack.top();
        
        assertEquals( 75, elementOnTop );
    }
    
    /**
     * To test get element on the top of the stack when stack is empty
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testGetElementOnTopStackWhenStackISEmpty() 
    {
        int elementOnTop = stack.top();
        assertEquals( 75, elementOnTop );
    }
}
