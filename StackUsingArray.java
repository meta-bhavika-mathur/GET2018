package Session1;

public class StackUsingArray<T> implements StackInterface<T>
{
    final String UNDERFLOW_EXCEPTION_MESSAGE = "Empty Stack Found!";
    private int stackCapacity;
    private int top;
    T[] array;
    
    // Constructor for stack
    public StackUsingArray()
    {
        top = -1;
        stackCapacity = 100;
        array = (T[]) new Object [stackCapacity];
    }
    
    // Parameterized constructor to set maximum size of stack
    StackUsingArray(int stackCapacity)
    {
        top = -1;
        this.stackCapacity = stackCapacity;
        array = (T[]) new Object [stackCapacity];
    }
   
    /**
     *  To push data into the stack requires that stack should not be full
     *  @param data, data of type T that is to be inserted into the stack
     */
    @Override
    public boolean push(T data) 
    {  
        boolean result = false;
    	if(!(top == stackCapacity - 1))
        {   
            top++;
            array[top] = data;
            result = true;
        }
    	return result;
    }

    /**
     * To pop data from the stack
     * @return p
     */
    @Override
    public T pop() throws UnderflowException
    {
        if( isEmpty())
            throw new UnderflowException(UNDERFLOW_EXCEPTION_MESSAGE);
        else
        {
            T poppedData;
            poppedData = array[top];
            top--;
            return poppedData;
        }
    }

    /**
     * To check whether the stack is empty or not
     * @return  true, if the stack is empty otherwise false
     */
    @Override
    public boolean isEmpty() 
    {   
        return top == -1;
    }

    /**
     * To get the top element of the stack
     */
    @Override
    public T top() throws UnderflowException
    {
        if( isEmpty())
            throw new UnderflowException(UNDERFLOW_EXCEPTION_MESSAGE);
        else
            return array[top];
    }
}
