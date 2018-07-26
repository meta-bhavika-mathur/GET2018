package assignment5;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntSetTest {

    // To check that invalid value is not present in integer set
 	  @Test (expected = AssertionError.class)
    public void testInvalidSet()
    {
		      int value = 993;
		      int array[] = new int[]{-1, -992, 993, 994, 995, 996, 997, 998, 999, 5000};
        IntSet check_member = new IntSet(array);
        
        assertEquals( true, check_member.isMember(value));		
    }
	
	   // Test case to check isMember method i.e to find if a value is a member of the set
    @Test
    public void testIfMember()
    {
    	   int value = 993;
        int array[] = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
        IntSet check_member = new IntSet(array);
        
        assertEquals( true, check_member.isMember(value));		
    }
    
    // Test case to check isMember method i.e to find if a value is not a member of the set
    @Test
    public void testIfNotMember()
    {
    	   int value = 99;
        int array[] = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
        IntSet check_member = new IntSet(array);
        
        assertEquals( false, check_member.isMember(value));		
    }
    
    // Test case to check isMember method when set referenced is empty
    @Test (expected = NullPointerException.class)
    public void testIsMemberWithEmptyArray()
    {
    	   int value = 993;
        int array[] = new int[]{};
        IntSet check_member = new IntSet(array);
        
        assertEquals( true, check_member.isMember(value));		
    }
    
    // Test case to check isMember method when value exceeds the allowed integer range (1-1000)
    @Test (expected = AssertionError.class)
    public void testIsMemberWithValueOutsideUpperLimit()
    {
    	   int value = 1500;
    	   int[] array = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
        IntSet check_member = new IntSet(array);
        
        assertEquals( true, check_member.isMember(value));		
    }
    
    // Test case to check isMember method when value exceeds the allowed integer range (1-1000)
    @Test (expected = AssertionError.class)
    public void testIsMemberWithValueOutsideLowerLimit()
    {
    	   int value = -20;
    	   int[] array = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
        IntSet check_member = new IntSet(array);
        
        assertEquals( true, check_member.isMember(value));		
    }
 
    // To check if a set is subset of another
    @Test
    public void testIfSubset() {
        
        int[] first_array = new int[]{4,1,5,10,3,2};
        int[] second_array = new int[]{5, 10};
        
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        
        assertEquals(true, first_set.isSubSet(second_set));
    }
    
    // Test case to check that a set is not a subset of another
    @Test
    public void testIfNotSubset() {
        
        int[] first_array = new int[]{4,1,5,10,3,2};
        int[] second_array = new int[]{60, 8};
        
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        
        assertEquals(false, first_set.isSubSet(second_set));
    }

   
    // Test case to check if NullPointerException is caught when empty array is passed to isSubset method
    @Test (expected = NullPointerException.class)
    public void testIfNullSetPassed() {
        
        int[] first_array = new int[]{4,1,5,10,3,2};
        int[] second_array = new int[]{};
        
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        
        assertEquals(second_set, first_set.isSubSet(second_set));
    }
    
    // Test case to check that size of first array must be greater than second for isSubset method
    @Test (expected = AssertionError.class)
    public void testIfSizeOfSecondSetSmall() {
        
        int[] first_array = new int[]{5, 8, 10, 2};
        int[] second_array = new int[]{4,1,5,10,3,2};
      
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        
        assertEquals(second_set, first_set.isSubSet(second_set));
    }
    
    // Test case to check for correct output for get union method with valid inputs
    @Test
    public void testGetUnion() {
        
    	   int[] first_array = new int[]{1,8,10,11};
        int[] second_array = new int[]{8,2,16,10,20};
        int[] third_array = new int[]{1,2,8,10,11,16,20};
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        IntSet result = new IntSet(third_array);
        
        IntSet union = result.getUnion(first_set, second_set);
        int a[] = union.convertSetToArray();
        int b[] = new int[]{1,2,8,10,11,16,20};
        
        assertArrayEquals(a, b);
    }
    
    // Test case to check when empty array is passed to getUnion method
    @Test (expected = NullPointerException.class)
    public void testNullSetPassedForUnion() {
        
        int[] first_array = new int[]{};
        int[] second_array = new int[]{8,2,16,10,20};
        int[] third_array = new int[]{1,2,8,10,11,16,20};
        
        IntSet first_set = new IntSet(first_array);
        IntSet second_set = new IntSet(second_array);
        IntSet result = new IntSet(third_array);
        
        IntSet temp = result.getUnion(first_set, second_set);
        int a[] = temp.convertSetToArray();
        int b[] = new int[]{1,2,8,10,11,16,20};
        
        assertEquals(a, b);
    }
    
    // Test case to find complement of a set with valid inputs
    @Test
    public void testComplement() {
       
       int test[] = new int[990];
       
       for(int i = 0; i < 990; i++)
           test[ i ] = i + 1;
       
       int a[] = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
       IntSet complement_set = new IntSet(test);
       IntSet final_set = complement_set.getComplement();
       int b[] = final_set.convertSetToArray();
       
       assertArrayEquals(a,b);
    }
    
    // Test case to find complement of a set with empty array as one parameter
    @Test(expected = NullPointerException.class)
    public void testComplementWithNullArrayAsParameter() {
       
       int test[] = new int[]{};
       int a[] = new int[]{991, 992, 993, 994, 995, 996, 997, 998, 999, 1000};
       
       IntSet complement_set = new IntSet(test);
       IntSet final_set = complement_set.getComplement();
       int b[] = final_set.convertSetToArray();
       
       assertArrayEquals(a,b);  
    }
}
