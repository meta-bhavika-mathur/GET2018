package priorityqueue;

// Interface to implement priority queue
public interface PriorityQueueInterface
{ 
	public boolean insertElement(String data, int priority);
	public Node removeElement();
	public boolean isEmpty();	
	public boolean isFull();	
}
