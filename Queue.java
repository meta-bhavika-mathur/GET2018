package Session1;

// A generic interface for queue
public interface Queue<T> 
{
    boolean addItemToQueue(T data);
    T deleteItemFromQueue();
    boolean isEmpty();
    boolean isFull();
}
