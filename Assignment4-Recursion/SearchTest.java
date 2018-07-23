package assignment4;

import static org.junit.Assert.*;
import org.junit.Test;

public class SearchTest {
    
    Search obj = new Search();
    
    //Positive test cases for linear search
    @Test
    public void testLinearSearch1() {
        assertEquals(3, obj.linearSearch(new int[]{2,3,4,5}, 5));   
    }
    
    @Test (expected = AssertionError.class)
    public void testLinearSearch2() {
        assertEquals(-1, obj.linearSearch(new int[]{1,2,3,4,5,6}, 8));      
    }
    
  //Negative test cases for linear search
    @Test
    public void testLinearSearch3() {
        assertNotEquals(2, obj.linearSearch(new int[]{20,30,40,50}, 50));   
    }
    
    @Test (expected = AssertionError.class)
    public void testLinearSearch4() { 
        assertNotEquals(5, obj.linearSearch(new int[]{1,2,3,4,5,6}, 8));      
    }
    
  //When empty array passed
    @Test(expected = AssertionError.class)
    public void testLinearSearch5() { 
        assertEquals(5, obj.linearSearch(new int[]{}, 8));      
    }
    
  //Positive test cases for binary search
    @Test
    public void testBinarySearch1() {
        assertEquals(3, obj.binarySearch(new int[]{2,3,4,5}, 0, 3, 5));
    }
    
    @Test
    public void testBinarySearch2() {
        assertEquals(-1, obj.binarySearch(new int[]{1,2,3,4,5,6}, 0, 5, 8));
    }
    
  //Negative test cases for binary search
    @Test
    public void testBinarySearch3() {
        assertNotEquals(3, obj.binarySearch(new int[]{2,3,4,5}, 0, 3, 10));
    }
    
    @Test
    public void testBinarySearch4() {
        assertNotEquals(2, obj.binarySearch(new int[]{1,2,3,4,5,6}, 0, 5, 4));
    }
    
  //When array passed is empty
    @Test(expected = AssertionError.class)
    public void testBinarySearch5() { 
        assertEquals(5, obj.linearSearch(new int[]{}, 8));      
    }
    
}
