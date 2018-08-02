package Session1;

public interface Queue<T> {
    
    boolean addItemToQueue(T data);
    T deleteItemFromQueue();
    boolean isEmpty();
    boolean isFull();
    
}
