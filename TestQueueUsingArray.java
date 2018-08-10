package Session1;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestQueueUsingArray 
{
    QueueUsingArray<Integer> queue = new QueueUsingArray<Integer>(5); 
    
   /**
    * To add item into the queue
    */
    @Test
    public void testAddItemToQueue() 
    {
           int data = 25;
           boolean result = queue.addItemToQueue(data);         
           assertEquals(true, result);						// Return true if item is successfully added
    }
    
    /**
     * To add item into the queue when queue is full
     */
     @Test
     public void testAddItemToQueueWhenFull() 
     {
            int[] data = new int[]{1, 2, 3, 4, 5};
            
            queue.addItemToQueue(data[0]);
            queue.addItemToQueue(data[1]);
            queue.addItemToQueue(data[2]);
            queue.addItemToQueue(data[3]);
            queue.addItemToQueue(data[4]);
            
            int newItem = 50;
            assertEquals(false, queue.addItemToQueue(newItem)); // Return false when queue is full
     }
     
     /**
      * To delete item from the queue
      */
     @Test
     public void testDeleteItemFromQueue()
     {
         int[] data = new int[]{1, 2};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         try
         {
        	 int deletedItem = queue.deleteItemFromQueue();
        	 assertEquals(1, deletedItem);						// Returns 1 as item at front in queue was 1
         }
         catch(Exception exception)
         {
         	System.out.println(exception.getMessage());
         }
     }
     
     /**
      * To delete item from the queue when queue is empty
      */
     @Test
     public void testDeleteItemFromQueueWhenEmpty()
     {
         try
         {
        	 int deletedItem = queue.deleteItemFromQueue();
        	 assertEquals(null, deletedItem);
         }
         catch(Exception exception)
         {
         	System.out.println(exception.getMessage());
         }
     }
     
     /**
      * To test whether queue is empty
      */
     @Test
     public void testIsQueueEmpty()
     {
         int[] data = new int[]{1, 2};
         
         try
         {
        	 queue.addItemToQueue(data[0]);
        	 queue.addItemToQueue(data[1]);
        	 queue.deleteItemFromQueue();
        	 queue.deleteItemFromQueue();
        	 assertEquals(true, queue.isEmpty());				// Returns true as queue is empty
         }
         catch(Exception exception)
         {
         	System.out.println(exception.getMessage());
         }
        
     }
     
     /**
      * To test whether queue is full
      */
     @Test
     public void testIsQueueFull()
     {
         int[] data = new int[]{1, 2, 3, 4, 5};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         queue.addItemToQueue(data[2]);
         queue.addItemToQueue(data[3]);
         queue.addItemToQueue(data[4]);
         
         assertEquals(true, queue.isFull());				// Returns true as queue is full 
     }
}
