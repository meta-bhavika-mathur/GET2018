package session2;

import Session1.QueueInterface;

public class CircularQueue<T> implements QueueInterface<T>
{

    int maximumCapacity;
    int front, rear, lengthOfQueue;
    T array[];

    // Constructor for queue
    public CircularQueue()
    {
        front = rear = -1;
        lengthOfQueue = 0;
        maximumCapacity = 100;
        array = (T[]) new Object [ maximumCapacity ];
    }
    
    // Parameterized constructor for queue
    public CircularQueue(int capacity)
    {
        front = rear = -1;
        lengthOfQueue = 0;
        maximumCapacity = capacity;
        array = (T[]) new Object [ maximumCapacity ];
    }
    

    /**
     * To insert element into the queue in First In First Out Order( FIFO )
     * @param, data of any type that is to be inserted in the queue
     * @return true, if data is successfully inserted otherwise false
     */
    @Override
    public boolean addItemToQueue(T data)
    {
        
        if ((front == 0 && rear == maximumCapacity-1) || (rear == (front - 1) % ( maximumCapacity-1 )))
            return false;
        else if (front == -1) 
            front = rear = 0;
        else if ( rear == maximumCapacity - 1 && front != 0 )
            rear = 0;
        else
            rear++;
     
        array[rear] = data;
        return true;
    }

    /**
     * To remove an item from the queue, requires that element must be deleted from the front
     * @return deletedItem, item that is deleted from the list otherwise null
     */
    @Override
    public T deleteItemFromQueue() throws ArrayIndexOutOfBoundsException
    {
        T itemDeleted;
        
        if (front == -1)
            throw new ArrayIndexOutOfBoundsException("Underflow Exception");
        else
        {
            itemDeleted = array[front];
            
            if( front == rear)
                front = rear = -1;
            else if( front == maximumCapacity - 1 )
                front = 0;
            else
                front++;
        }
     
        return itemDeleted;
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
        if(( front == rear + 1 ) || ( front == 0 && rear == maximumCapacity - 1))
            return true;
        else 
            return false;
        
    }
}
