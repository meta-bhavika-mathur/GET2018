package Session1;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestQueueUsingList {

    QueueUsingLinkedList<Integer> queue = new QueueUsingLinkedList<Integer>(); 
    
   /**
    * To add item into the queue
    */
    @Test
    public void testAddItemToQueue() 
    {
           int data = 25;
           boolean result = queue.addItemToQueue(data);           
           assertEquals(true, result);
    }
     
    /**
     * To delete item from the queue
     */
     public void testDeleteItemFromQueue()
     {
         int[] data = new int[]{1, 2};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         
         int deletedItem = queue.deleteItemFromQueue();
         assertEquals(1, deletedItem);					// Returns 1 as item at front in queue was 1        
     }
     
    /**
     * To delete item from the queue when queue is empty
     */
     public void testDeleteItemFromQueueWhenEmpty()
     {
         int deletedItem = queue.deleteItemFromQueue();
         assertEquals(null, deletedItem);				// Returns null when no item is present in queue
     }
     
     /**
      * To test whether queue is empty
      */
     public void testIsQueueEmpty()
     {
         int[] data = new int[]{1, 2};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         queue.deleteItemFromQueue();
         queue.deleteItemFromQueue();
         
         assertEquals(true, queue.isEmpty());		// Returns true as queue is empty
     }
     
     /**
      * To test whether queue is full
      */
     public void testIsQueueFull()
     {
         int[] data = new int[]{1, 2, 3, 4, 5};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         queue.addItemToQueue(data[2]);
         queue.addItemToQueue(data[3]);
         queue.addItemToQueue(data[4]);
         
         assertEquals(true, queue.isFull());		// Returns true as queue reaches its maximum capacity
     }
}
