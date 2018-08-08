package priorityqueue;

/**
 *  Class to implement a priority queue. A priority queue is a queue, that stores elements along 
 *  with their priority. The element with maximum priority is always present at the front of the queue
 *  Implemented using heap data structure whose elements are stored in an array.
 *  This class implements PriorityQueueinterface.
 */
public class PriorityQueue implements PriorityQueueInterface
{
    public final String OVERFLOW_MESSAGE = "Can't insert elememnts!";
    public Node[] array; 												// Array to store queue elements
    private int arraySize, capacity;
    
    // Constructor for priority queue
    public PriorityQueue(int capacityOfQueue) 
    {
    	this.capacity = capacityOfQueue + 1;							// Total capacity of queue
    	array = new Node[ this.capacity ];								
    	arraySize = 0;													// Current size of queue
    }
    
    /**
     * To add an element in the queue where the queue is stored as max heap where the fist element 
     * of the queue will always return the element with highest priority
     * @param data, data in the from of string that is to be inserted in the queue
     * @param priority, priority of given element
     */
    @Override
    public boolean insertElement(String data, int priority) 
    {	
    	if(isFull() || capacity <= 1)
        	return false;
    	
    	Node newElement = new Node(data, priority);
    	array[ ++arraySize ] = newElement;
        int currentPosition = arraySize;
        
        // Store new element in the form of heap
        while (currentPosition != 1 && newElement.priority > array[ currentPosition / 2 ].priority)
        {
        	array[ currentPosition ] = array[ currentPosition / 2 ];
        	currentPosition /= 2;
        }
        array[ currentPosition ] = newElement; 
        return true;
    }
    
    /**
     * To remove an element from the queue. The element with highest priority is always removed from the queue.
     * After removal of element, the structure of queue is rearranged again such the the next element with 
     * highest priority is found at the front of the queue. Thus, elements are always removed in decreasing
     * order of priority.
     * @return item, element that is removed from queue
     */
    @Override
    public Node removeElement() 
    {
        if (isEmpty())
            return null;
        
        int parent, child;
    	Node item, temp;
        item = array[ 1 ];
        temp = array[ arraySize-- ];
        parent = 1;
        child = 2;
        
        while (child <= arraySize)
        {
            if (child < arraySize && array[ child ].priority < array[ child + 1 ].priority)
                child++;
            if (temp.priority >= array[ child ].priority)
                break;
            array[parent] = array[child];
            parent = child;
            child *= 2;
        }  
        array[parent] = temp;
        return item;
    }   
    
    /**
     * To check whether the queue is full or not.
     * @return true if the queue is full, otherwise false.
     */
    @Override
    public boolean isFull() 
    {
    	 return arraySize == capacity - 1;
    }
    
    /**
     * To check whether the queue is empty or not.
     * @return true if queue is empty, otherwise false.
     */
    @Override
    public boolean isEmpty() 
    {
    	 return arraySize == 0;
    }
    
    /**
     * To get element with highest priority in the queue. The element with highest priority is always 
     * present at the front of the queue
     * @return maxElement
     */
    public Node getElementWithMaximumPriority()
    {
    	Node maxElement = array[1];
    	return maxElement;
    }  
}