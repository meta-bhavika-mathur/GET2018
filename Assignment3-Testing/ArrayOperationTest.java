package assignment3;

import static org.junit.Assert.*;

public class ArrayOperationTest {

    ArrOperation arrayOperation = new ArrOperation();
    
    /**
     * Test cases for split array problem
     */
   
    // Positive case for splitArray if array can be split
	@org.junit.Test
	public void splitArrayTest1() {
		
		int splitIndex = arrayOperation.splitArray(new int[]{1, 1, 1, 2, 1});
		assertEquals(3, splitIndex);
	}
	
	@org.junit.Test
	public void splitArrayTest2() {
        
	    int splitIndex = arrayOperation.splitArray(new int[]{10, 10});
	    assertEquals(1, splitIndex);
	}
	
	
   // Test case for splitArray if array cannot be split
	@org.junit.Test
	public void splitArrayTest3() {
		
		int splitIndex = arrayOperation.splitArray(new int[]{2, 1, 1, 2, 1});
		assertEquals(-1, splitIndex);
	}
	
	@org.junit.Test
    public void splitArrayTest4() {
        
        int splitIndex = arrayOperation.splitArray(new int[]{2, 3, 5, 2, 1});
        assertEquals(-1, splitIndex);
    }
	
	//Test case for splitArray if array is empty
	@org.junit.Test(expected = AssertionError.class)
	public void splitArrayTest5() {
		
		int splitIndex = arrayOperation.splitArray(new int[]{});
		assertEquals(1, splitIndex);
	}
    
    /**
     * Test cases for countClumps 
     */
    
    //Positive test cases for countClumps
    @org.junit.Test
    public void countClumpsTest1() {   
        int noOfClumps = arrayOperation.countClumps(new int[]{1, 2, 2, 3, 4, 4});
        assertEquals(2, noOfClumps);
    }
    
    @org.junit.Test
    public void countClumpsTest2() {
        int noOfClumps = arrayOperation.countClumps(new int[]{1, 1, 2, 1, 1});
        assertEquals(2, noOfClumps);
    }
       
    //Negative test cases for countClumps
    @org.junit.Test
    public void countClumpsTest3() {
        int noOfClumps = arrayOperation.countClumps(new int[]{1, 2, 3, 5, 6});
        assertEquals(0, noOfClumps);
    }
    
    // Test case for countClumps when array is empty 
    @org.junit.Test(expected = AssertionError.class)
    public void noOfClumpsTest5() {
        int noOfClumps = arrayOperation.countClumps(new int[]{});
    }
    
    
    /**
     * Test case for to find Largest Mirror Section in an array
     */
    
    //Positive test cases
    @org.junit.Test
    public void maxMirrorTest1() {
        int max = arrayOperation.maxMirror(new int[]{1, 4, 5, 3, 5, 4, 1});
        assertEquals(7, max);
    }
    
    @org.junit.Test
    public void maxMirrorTest2() {
        int max = arrayOperation.maxMirror(new int[]{8, 7, 1, 4, 3, 4, 1, 7, 8});
        assertEquals(9, max);
    }
    
    //Negative test cases
    @org.junit.Test
    public void maxMirrorTest3() {
        int max = arrayOperation.maxMirror(new int[]{7, 12, 4, 8, 7, 5, 1});
        assertNotEquals(1, max);
    }
    
    @org.junit.Test
    public void maxMirrorTest4() {
        int max = arrayOperation.maxMirror(new int[]{7, 1, 4});
        assertNotEquals(6, max);
    }
   
    // Test case for maxMirror when empty array is passed as input
    @org.junit.Test(expected = AssertionError.class)
    public void maxMirrorTest5() {
        
        int max = arrayOperation.maxMirror(new int[]{});
        assertEquals(0 , max);
    }
    
    /**
     * Test cases for fix XY problem
     */
    
    // Positive test cases for fixXY problem
    @org.junit.Test
    public void fixXYTest1() {
        
        int[] fixXY = arrayOperation.fixXY(new int[]{5, 4, 9, 4, 9, 5}, 4, 5);
        assertArrayEquals(new int[]{9, 4, 5, 4, 5, 9}, fixXY);
    }
    
    @org.junit.Test
    public void fixXYTest2() {
        
        int[] fixXY = arrayOperation.fixXY(new int[]{5, 4, 9, 4, 9, 5}, 4, 5);
        assertArrayEquals(new int[]{9, 4, 5, 4, 5, 9}, fixXY);
    }
    
   // Negative test cases for fixXY problem
    @org.junit.Test
    public void fixXYTest3() {
        
        int[] fixXY = arrayOperation.fixXY(new int[]{5, 4, 9, 4, 9, 5}, 4, 5);
        assertNotEquals(new int[]{9, 4, 9, 4, 5, 9}, fixXY);
    }
    
    @org.junit.Test
    public void fixXYTest4() {
        
        int[] fixXY = arrayOperation.fixXY(new int[]{5, 4, 9, 4, 9, 5}, 4, 5);
        assertNotEquals(new int[]{5, 4, 5, 4, 5, 9}, fixXY);
    }
    
    // Test case for fixXY when empty array is passed as input
    @org.junit.Test(expected = AssertionError.class)
    public void fixXYTest5() {
       
        int[] fixXY = arrayOperation.fixXY(new int[]{}, 4, 5);
    }
    
    // Test case for fixXY if array cannot be rearranged when there are two adjacent X in array
    @org.junit.Test(expected = AssertionError.class)
    public void fixXYTest6() {
   
        int[] fixXY = arrayOperation.fixXY(new int[]{1, 4, 4, 5, 3, 5, 1}, 4, 5);
    }
    
   // Test case for fixXY if array cannot be rearranged when X is at last position
    @org.junit.Test(expected = AssertionError.class)
    public void fixXYTest7() {
   
        int[] fixXY = arrayOperation.fixXY(new int[]{1, 5, 1, 4}, 4, 5);
    }
    
 
    // Test case for fixXY if array cannot be rearranged because of unequal X & Y    
    @org.junit.Test(expected = AssertionError.class)
    public void fixXYTest8() {
   
        int[] fixXY = arrayOperation.fixXY(new int[]{1, 4, 1, 5, 3, 4, 1}, 4, 5);
    }
    

}
