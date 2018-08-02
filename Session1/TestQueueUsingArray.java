package Session1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQueueUsingArray {

    QueueUsingArray<Integer> queue = new QueueUsingArray<Integer>(); 
    
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
            assertEquals(false, queue.addItemToQueue(newItem));
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
         
         int deletedItem = queue.deleteItemFromQueue();
         assertEquals(1, deletedItem);
         
     }
     
     /**
      * To delete item from the queue when queue is empty
      */
     @Test(expected = ArrayIndexOutOfBoundsException.class)
     public void testDeleteItemFromQueueWhenEmpty()
     {
         int deletedItem = queue.deleteItemFromQueue();
         assertEquals(null, deletedItem);
     }
     
     /**
      * To test whether queue is empty
      */
     @Test
     public void testIsQueueEmpty()
     {
         int[] data = new int[]{1, 2};
         
         queue.addItemToQueue(data[0]);
         queue.addItemToQueue(data[1]);
         queue.deleteItemFromQueue();
         queue.deleteItemFromQueue();
         
         assertEquals(true, queue.isEmpty());
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
         
         assertEquals(true, queue.isFull());
     }
}