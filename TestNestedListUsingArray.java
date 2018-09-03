package questionone;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNestedListUsingArray {

	NestedListUsingArray newNestedList = new NestedListUsingArray(2);
	
	/**
	 * Test cases to add a new list in  nested list
	 */
	
	// Test case to add lists in nested list
	@Test
	public void testAddList() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		boolean expectedResult = true;
		boolean actualResult = newNestedList.addList(firstListtoAdd);
		assertEquals(expectedResult, actualResult);	
		
		int[] secondListtoAdd = new int[]{5, 6};
		boolean expectedResultOne = true;
		boolean actualResultOne = newNestedList.addList(secondListtoAdd);
		assertEquals(expectedResultOne, actualResultOne);			
		
	}
	
	// Test case to add new list after maximum capacity of nested list is reached
	@Test
	public void testAddListAfterReachingMaximumLimit() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		boolean expectedResult = true;
		boolean actualResult = newNestedList.addList(firstListtoAdd);
		assertEquals(expectedResult, actualResult);	
		
		int[] secondListtoAdd = new int[]{5, 6};
		boolean expectedResultOne = true;
		boolean actualResultOne = newNestedList.addList(secondListtoAdd);
		assertEquals(expectedResultOne, actualResultOne);
		
		int[] thirdListtoAdd = new int[]{50, 66, 78, 65};
		boolean expectedResultTwo = false;
		boolean actualResultTwo = newNestedList.addList(thirdListtoAdd);
		assertEquals(expectedResultTwo, actualResultTwo);
		
	}
	
	// Test case to add a new list with length equal to zero
	@Test
	public void testAddListWithEmptyListAsParameter() 
	{
		int[] listtoAdd = new int[]{};
		boolean expectedResult = false;
		boolean actualResult = newNestedList.addList(listtoAdd);
		assertEquals(expectedResult, actualResult);	
	}
	
	// Test case to add null list in nested list
	@Test 
	public void testAddListWithNullListAsParameter() 
	{
		int[] listtoAdd = null;
		boolean expectedResult = false;
		boolean actualResult = newNestedList.addList(listtoAdd);
		assertEquals(expectedResult, actualResult);	
	}
	
	/**
	 * Test cases to access element at specified position in nested list
	 */
	
	// Test to get element present at specified position in the list 
	@Test
	public void testGetElementAtSpecifiedPositionInList() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		newNestedList.addList(firstListtoAdd);
		newNestedList.addList(secondListtoAdd);
		
		try
		{
			int expectedValue = 78;
			int actualValue = newNestedList.getElementAtSpecifiedPosition(1, 2);
			assertEquals(expectedValue, actualValue);
		
			int secondExpectedValue = 3;
			int secondActualValue = newNestedList.getElementAtSpecifiedPosition(0, 2);
			assertEquals(secondExpectedValue, secondActualValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	// Test to get element present at specified position in the list with invalid row index as input
	@Test 
	public void testGetElementAtSpecifiedPositionWithInvalidRowIndex() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		newNestedList.addList(firstListtoAdd);
		newNestedList.addList(secondListtoAdd);
		
		try
		{
			int expectedValue = 78;
			int actualValue = newNestedList.getElementAtSpecifiedPosition(2, 2); // Invalid row number
			assertEquals(expectedValue, actualValue);
			
			int secondExpectedValue = 3;
			int secondActualValue = newNestedList.getElementAtSpecifiedPosition(0, 2);
			assertEquals(secondExpectedValue, secondActualValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	// Test to get element present at specified position in the list with invalid column index as input
	@Test 
	public void testGetElementAtSpecifiedPositionWithInvalidColumnIndex() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		newNestedList.addList(firstListtoAdd);
		newNestedList.addList(secondListtoAdd);
		
		try
		{
			int expectedValue = 78;
			int actualValue = newNestedList.getElementAtSpecifiedPosition(1, 2); 
			assertEquals(expectedValue, actualValue);
			
			int secondExpectedValue = 3;
			int secondActualValue = newNestedList.getElementAtSpecifiedPosition(0, 3); // Invalid column number
			assertEquals(secondExpectedValue, secondActualValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	/**
	 * Test cases to test method to get largest integer from the nested list
	 */
	
	// Test case to find largest integer in nested list
	@Test 
	public void testGetLargestValue() 
	{
		int[] firstListtoAdd = new int[]{1, -2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		try
		{
			newNestedList.addList(firstListtoAdd);
			newNestedList.addList(secondListtoAdd);
			
			int expectedValue = 78;
			int actualValue = newNestedList.getLargestValue(); 
			assertEquals(expectedValue, actualValue);
		}	
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
		
	}
	
	// Test case to find largest integer in nested list
	@Test 
	public void testGetLargestValueWithNullList() 
	{
		try
		{
			int n = newNestedList.getLargestValue();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	/**
	 * Test cases to test method to search an integer in the nested list
	 */
	// Test case to search an integer in nested list when it is present in the list
	@Test 
	public void testSearchValueWhenPresent() 
	{
		int[] firstListtoAdd = new int[]{1, -2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		try
		{
			newNestedList.addList(firstListtoAdd);
			newNestedList.addList(secondListtoAdd);
			
			boolean expectedValue = true;
			boolean actualValue = newNestedList.searchElement(65); 
			assertEquals(expectedValue, actualValue);	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}
	
	// Test case to search an integer in nested list when it is not present in the list
	@Test 
	public void testSearchValueWhenNotPresent() 
	{
		int[] firstListtoAdd = new int[]{1, -2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		try
		{
			newNestedList.addList(firstListtoAdd);
			newNestedList.addList(secondListtoAdd);
			
			boolean expectedValue = false;
			boolean actualValue = newNestedList.searchElement(-33); 
			assertEquals(expectedValue, actualValue);	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	/**
	 * Test cases to test method to find sum of all integers in the nested list
	 */
	
	// Test case to find sum with size of nested list as 2
	@Test 
	public void testGetSumOfAllIntegersCaseOne() 
	{
		int[] firstListtoAdd = new int[]{1, 2, 3};
		int[] secondListtoAdd = new int[]{50, 66, 78, 65};
		
		try
		{
			newNestedList.addList(firstListtoAdd);
			newNestedList.addList(secondListtoAdd);
			
			int expectedValue = 265;
			int actualValue = newNestedList.getSumOfAllIntegers();
			assertEquals(expectedValue, actualValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	// Test case to find sum with size of nested list as 3
	@Test 
	public void testGetSumOfAllIntegersCaseTwo() 
	{
		NestedListUsingArray newNestedList = new NestedListUsingArray(3);
		
		int[] firstListtoAdd = new int[]{1, 2, 3};
		int[] secondListtoAdd = new int[]{10, 20, 30};
		int[] thirdListtoAdd = new int[]{-10, 0, 0};
		
		try
		{
			newNestedList.addList(firstListtoAdd);
			newNestedList.addList(secondListtoAdd);
			newNestedList.addList(thirdListtoAdd);
			
			int expectedValue = 56;
			int actualValue = newNestedList.getSumOfAllIntegers();
			assertEquals(expectedValue, actualValue);		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	

	}
}