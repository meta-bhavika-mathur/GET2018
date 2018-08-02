package Session1;

public class QueueUsingArray<T> implements Queue<T> 
{
    int queueMaximumCapacity;
    int front, rear;
    T array[];

    // Constructor for queue
    public QueueUsingArray(int capacity )
    {
        front = rear = -1;
        this.queueMaximumCapacity = capacity;
        array = (T[]) new Object [ queueMaximumCapacity ];
    }

    /**
     * To insert element into the queue in First In First Out Order( FIFO )
     * @param, data of any type that is to be inserted in the queue
     * @return true, if data is successfully inserted otherwise false
     */
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
       // lengthOfQueue++;
        return true;
    }
    
    /**
     * To remove an item from the queue, requires that element must be deleted from the front
     * @return deletedItem, item that is deleted from the list
     * @throws ArrayIndexOutOfBoundException if queue is empty
     */
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

    /**
     * To check if the queue is empty
     * @return, true if the queue is empty otherwise false
     */
    @Override
    public boolean isEmpty() 
    {
        return front == -1;
    }

    /**
     * To check if the queue is full
     * @return, true if queue is full otherwise false 
     */
    @Override
    public boolean isFull() 
    {
        return rear == queueMaximumCapacity - 1;
    }
}