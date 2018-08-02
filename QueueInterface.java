package Session1;

// A generic interface for queue
public interface QueueInterface<T> 
{
    boolean addItemToQueue(T data);
    T deleteItemFromQueue();
    boolean isEmpty();
    boolean isFull();
}
