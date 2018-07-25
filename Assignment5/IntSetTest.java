package assignment5;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntSetTest {

 
    
    int[] test_array3 = new int[]{10, 4, 11, 5, 6};
    int[] test_array4 = new int[]{4, 5, 6, 10, 11};
    int[] test_array5 = new int[]{1,8,10,11};
    int[] test_array6 = new int[]{8,2,16,10,20};
    int[] test_array7 = new int[]{1,2,8,10,11,16,20};
    int[] test_array1 = new int[]{4,1,5,10,3,2};
    int[] test_array2 = new int[]{5, 10};
    IntSet first_set = new IntSet(test_array1);
    IntSet second_set = new IntSet(test_array2);

    
    
    
    IntSet first_set3 = new IntSet(test_array3);
    IntSet first_set4 = new IntSet(50);
    
    
    
   // IntSet second_set = new IntSet(test_array2);
    
    
    
    /**
     * Positive test cases for isSubset method
     */
    
    // To check if a set is subset of another
    @Test
    public void testIfSubset() {
        
        int[] test_array1 = new int[]{4,1,5,10,3,2};
        int[] test_array2 = new int[]{5, 10};
        
        IntSet first_set = new IntSet(test_array1);
        IntSet second_set = new IntSet(test_array2);
        
        assertEquals(true, first_set.isSubSet(second_set));
    }
    
    // To check if a set is not a subset of another
    @Test
    public void testIfNotSubset() {
        
        int[] test_array1 = new int[]{4,1,5,10,3,2};
        int[] test_array2 = new int[]{60, 8};
        
        IntSet first_set = new IntSet(test_array1);
        IntSet second_set = new IntSet(test_array2);
        
        assertEquals(false, first_set.isSubSet(second_set));
    }
    
    /**
     * Negative test cases for isSubset method
     */
   
    // Test case to check if NullPointerException is caught when empty array is passed to isSubset method
    @Test (expected = NullPointerException.class)
    public void testIfNullSetPassed() {
        
        int[] test_array1 = new int[]{4,1,5,10,3,2};
        int[] test_array2 = new int[]{};
        
        IntSet first_set = new IntSet(test_array1);
        IntSet second_set = new IntSet(test_array2);
        
        assertEquals(second_set, first_set.isSubSet(second_set));
    }
    
    // Test case to check that size of first array must be greater than second for isSubset method
    @Test (expected = AssertionError.class)
    public void testIfSizeOfSecondSetSmall() {
        
        int[] test_array1 = new int[]{5, 8, 10, 2};
        int[] test_array2 = new int[]{4,1,5,10,3,2};
      
        IntSet first_set = new IntSet(test_array1);
        IntSet second_set = new IntSet(test_array2);
        
        assertEquals(second_set, first_set.isSubSet(second_set));
    }
    
   /**
    * Tests to find union of two sets
    */
    
    // Test case to check when correct union is obtained
    @Test
    public void testGetUnion() {
        
        IntSet u1 = new IntSet(test_array5);
        IntSet u2 = new IntSet(test_array6);
        IntSet result = new IntSet(test_array7);
        
        IntSet temp = result.getUnion(u1, u2);
        int a[] = temp.convertSetToArray();
        int b[] = new int[]{1,2,8,10,11,16,20};
        
        assertArrayEquals(a, b);
    }
    
    // Test case to check when correct union is not obtained
    @Test
    public void testIncorrectUnion() {
        
        IntSet u1 = new IntSet(test_array5);
        IntSet u2 = new IntSet(test_array6);
        IntSet result = new IntSet(test_array7);
        
        IntSet temp = result.getUnion(u1, u2);
        int a[] = temp.convertSetToArray();
        int b[] = new int[]{1,2,8,11,16,20};
        
        assertNotEquals(a, b);
    }
    
    // To check when empty array is passed to getUnion method
    @Test (expected = NullPointerException.class)
    public void testNullSetPassedForUnion() {
        
        int[] test_array1 = new int[]{};
        int[] test_array2 = new int[]{8,2,16,10,20};
        int[] test_array3 = new int[]{1,2,8,10,11,16,20};
        
        IntSet u1 = new IntSet(test_array1);
        IntSet u2 = new IntSet(test_array2);
        IntSet result = new IntSet(test_array3);
        
        IntSet temp = result.getUnion(u1, u2);
        int a[] = temp.convertSetToArray();
        int b[] = new int[]{1,2,8,10,11,16,20};
        
        assertEquals(a, b);
    }
    
    
    
    @Test
    public void testSort() {
        assertArrayEquals( test_array4, first_set3.selectionSort());
    }
    
   @Test
    public void testComplement() {
       
       int test[] = new int[990];
       for(int i = 0; i < 990; i++)
           test[ i ] = i + 1;
       
       int a[] = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
       IntSet result_set = new IntSet(a);
       IntSet complement_set = new IntSet(test);
       IntSet final_set = complement_set.getComplement();
       int b[] = final_set.convertSetToArray();
       
      assertArrayEquals(a,b);
    }
}
