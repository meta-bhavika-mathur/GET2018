package Session1;

public class QueueUsingArray<T> implements Queue<T> {
    
    private final static int MAX_SIZE_OF_QUEUE = 5;
    int front, rear, lengthOfQueue;
    T array[];

    // Constructor for queue
    public QueueUsingArray()
    {
        front = rear = -1;
        lengthOfQueue = 0;
        array = (T[]) new Object [ MAX_SIZE_OF_QUEUE ];
    }

    @Override
    public boolean addItemToQueue(T data)
    {
        if(isFull())
            return false;
        else
        {
            if ( rear == -1 ) 
                front = rear = 0;
            else
                rear++;
        }
        
        array[rear] = data;
        lengthOfQueue++;
        return true;
    }

    @Override
    public T deleteItemFromQueue() throws ArrayIndexOutOfBoundsException
    {
        if(isEmpty())
            throw new ArrayIndexOutOfBoundsException("Underflow Exception");
        else
        {
            T deletedItem = array[front];
            
            if( front == rear )
                front = rear = -1;
            else 
                front++;
            
            return deletedItem;
        }
    }

    @Override
    public boolean isEmpty() 
    {
        return front == -1;
    }

    @Override
    public boolean isFull() 
    {
        return rear == MAX_SIZE_OF_QUEUE - 1;
    }
}
