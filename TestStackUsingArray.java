package Session1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestStackUsingArray 
{

    StackUsingArray<Integer> stack = new StackUsingArray<Integer>(5);
        
    /**
     * Test to push data into stack
     */
    @Test
    public void testPushDataIntoStack() 
    {   
       int dataToPush = 25;
       boolean expectedoutput = stack.push(dataToPush);
        
       assertEquals(true, expectedoutput); 		// Returns true is data is successfully pushed into stack	
    }
    
    /**
     * Test to push data into stack when stack is full
     */
    @Test
    public void testPushDataIntoStackWhenFull() 
    {   
       stack.push(25);
       stack.push(55);
       stack.push(20);
       stack.push(30);
       stack.push(81);
        
       assertEquals(false, stack.push(200));	// Returns false when stack is full & data is not pushed
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
        assertEquals(75, poppedData );			// Pops item 75 as it was last item pushed into stack
    }
    
    /**
     * Test to pop data from stack when stack is empty
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testPopDataFromStackWhenEmpty() 
    { 
        int poppedData = stack.pop();			// Throws exception as stack was empty
        assertEquals(75, poppedData ); 			
    }
    
    /**
     * To test if array is empty
     */
    @Test
    public void testIsEmptyForStack() 
    {
        assertEquals( true , stack.isEmpty()); 	// Returns true as stack is empty
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
        
        assertEquals( 75, elementOnTop );		// Returns item 75 as it was on top of stack
    }
    
    /**
     * To test get element on the top of the stack when stack is empty
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testGetElementOnTopStackWhenStackISEmpty() 
    {
        int elementOnTop = stack.top();
        assertEquals( 75, elementOnTop );		// Returns item 75 as it was on top of stack
    }
}