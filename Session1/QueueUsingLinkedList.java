package Session1;


public class QueueUsingLinkedList<T> implements Queue<T> {
    
    protected NodeOfLinkedList<T> front, rear;
    
    // Constructor for queue
    public QueueUsingLinkedList()
    {
        front = null;
        rear = null;
    }

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

    @Override
    public boolean isEmpty() 
    {
        return front == null;
    }

    @Override
    public boolean isFull() 
    {
        return false;
    }

}
