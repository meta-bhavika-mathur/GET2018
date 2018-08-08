package priorityqueue;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPriorityQueue 
{
	PriorityQueue newPriorityQueue = new PriorityQueue(10);
	
	/**
	 * To test insertion of an element in a priority queue
	 */
	@Test
	public void testInsertionInPriorityQueue() 
	{
		boolean firstResult = newPriorityQueue.insertElement("ABC", 2);
		boolean secondResult = newPriorityQueue.insertElement("DEF", 10);

		assertEquals(true, firstResult);
		assertEquals(true, secondResult);		
	}
	
	/**
	 * To test removal of an element from a queue
	 */
	@Test
	public void tesDeletionInPriorityQueue() 
	{
		newPriorityQueue.insertElement("ABC", 2);
		newPriorityQueue.insertElement("DEF", 10);
		newPriorityQueue.insertElement("GHI", 2);
		newPriorityQueue.insertElement("JKL", 4);
		newPriorityQueue.insertElement("MNO", 6);
		
		Node firstResult = newPriorityQueue.removeElement();
		Node secondResult = newPriorityQueue.removeElement();
		
		String firstResultValue = firstResult.getData();
		String secondResultValue = secondResult.getData();
		String firstExpectedValue = "DEF";
		String secondExpectedValue = "MNO";
		
		assertEquals(firstExpectedValue, firstResultValue);
		assertEquals(secondExpectedValue, secondResultValue);		
	}
	
	/**
	 * Test to get element with maximum priority from the queue
	 */
	@Test
	public void testToGetElementWithMaximumPriority() 
	{
		newPriorityQueue.insertElement("ABC", 2);
		newPriorityQueue.insertElement("DEF", 10);
		newPriorityQueue.insertElement("GHI", 2);
		newPriorityQueue.insertElement("JKL", 4);
		newPriorityQueue.insertElement("MNO", 6);
		
		Node result = newPriorityQueue.getElementWithMaximumPriority();	
		String resultValue = result.getData();
		String expectedValue = "DEF";
		
		assertEquals(expectedValue, resultValue);			
	}
	
	/**
	 * Test to get element with maximum priority from the queue when all elements have equal priority
	 */
	@Test
	public void testToGetElementWithMaximumPriorityWhenAllPrioritiesAreEqual() 
	{
		newPriorityQueue.insertElement("ABC", 2);
		newPriorityQueue.insertElement("DEF", 2);
		newPriorityQueue.insertElement("GHI", 2);
		newPriorityQueue.insertElement("JKL", 2);
		newPriorityQueue.insertElement("MNO", 2);
		
		Node result = newPriorityQueue.getElementWithMaximumPriority();	
		String resultValue = result.getData();
		String expectedValue = "ABC";
		
		assertEquals(expectedValue, resultValue);			
	}
	
	/**
	 * Test to check method to check whether the queue is full or not
	 */
	@Test
	public void testToCheckIsQueueFull() 
	{
		PriorityQueue newPriorityQueue = new PriorityQueue(5);
		newPriorityQueue.insertElement("ABC", 2);
		newPriorityQueue.insertElement("DEF", 10);
		newPriorityQueue.insertElement("GHI", 2);
		newPriorityQueue.insertElement("JKL", 4);
		newPriorityQueue.insertElement("MNO", 6);
		
		boolean result = newPriorityQueue.isFull();	
		boolean expectedValue = true;
		
		assertEquals(expectedValue, result);			
	}
	
	/**
	 * Test to check method to check whether the queue is empty or not
	 */
	@Test
	public void testToCheckIsQueueEmpty() 
	{
		PriorityQueue newPriorityQueue = new PriorityQueue(5);
		boolean result = newPriorityQueue.isEmpty();	
		boolean expectedValue = true;
		
		assertEquals(expectedValue, result);			
	}
}