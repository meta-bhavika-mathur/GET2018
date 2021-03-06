package Session1;

public class StackUsingLinkedList<T> implements StackInterface<T> 
{
	   final String UNDERFLOW_EXCEPTION_MESSAGE = "Empty stack found!";
    protected NodeOfLinkedList<T> top;
    
    // Constructor for stack using linked list
    public StackUsingLinkedList()
    {
        top = null;
    } 
    
    /**
     *  To push data into the stack requires that stack should not be full
     *  @param data, data of type T that is to be inserted into the stack
     */
    @Override
    public boolean push(T dataToPush)
    {
        NodeOfLinkedList<T> newNode = new NodeOfLinkedList<T>( dataToPush, null );
        
        if( top == null )
            top = newNode;
        else
        {
            newNode.setNextNode(top);
            top = newNode;
        }
        return true;
    }
    
    /**
     * To pop data from the stack
     */
    @Override
    public T pop() throws UnderflowException
    {
        if(isEmpty())
            throw new UnderflowException(UNDERFLOW_EXCEPTION_MESSAGE);
        else
        {
            T poppedData; 
            NodeOfLinkedList<T> pointerToTop = top;
            poppedData =  pointerToTop.getData();
            top = pointerToTop.getNextNode();
            return poppedData;
        }
    }

   /**
    * To get top element in the stack
    */
    public T top() throws UnderflowException
    {
        NodeOfLinkedList<T> pointerToTop = top;

        if(isEmpty())
            throw new UnderflowException(UNDERFLOW_EXCEPTION_MESSAGE);
        else
            return pointerToTop.getData();
    }
    
    /**
     * To check whether the stack is empty or not
     * @return  true, if the stack is empty otherwise false
     */
    public boolean isEmpty() 
    {
            return top == null;
    }
}
