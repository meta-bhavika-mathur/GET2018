package Session1;

public class QueueUsingLinkedList<T> implements Queue<T> 
{
	
    protected NodeOfLinkedList<T> front, rear;
    
    // Constructor for queue
    public QueueUsingLinkedList()
    {
        front = null;
        rear = null;
    }
    
    /**
     * To insert element into the queue in First In First Out Order( FIFO )
     * @param, data of any type that is to be inserted in the queue
     * @return true, if data is successfully inserted otherwise false
     */
    @Override
    public boolean addItemToQueue(T data)
    {
        NodeOfLinkedList<T> newItem = new NodeOfLinkedList<T>(data, null);
        
        if (rear == null)
        {
            front = newItem;
            rear = newItem;
            return true;
        }
        else
        {
            rear.setNextNode(newItem);
            rear = rear.getNextNode();
            return true;
        }
    }

    /**
    * To remove an item from the queue, requires that element must be deleted from the front
    * @return deletedItem, item that is deleted from the list
    */
    @Override
    public T deleteItemFromQueue() 
    {
        if (isEmpty())
            return null;
       
        NodeOfLinkedList<T> pointerToFront = front;
        front = pointerToFront.getNextNode();        
       
        if ( front == null )
            rear = null;   
        
        return pointerToFront.getData();
    }

    /**
     * To check if the queue is empty
     * @return, true if the queue is empty otherwise false
     */
    @Override
    public boolean isEmpty() 
    {
        return front == null;
    }

    /**
     * To check if the queue is full
     * @return, true if queue is full otherwise false 
     */
    @Override
    public boolean isFull() 
    {
        return false;
    }
}