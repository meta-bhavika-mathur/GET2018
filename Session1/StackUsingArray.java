package Session1;

import java.util.NoSuchElementException;

public class StackUsingArray<T> implements Stack<T>{

    public final static int MAX_SIZE_OF_ARRAY = 5;
    public int stackSize;
    public int top;
    T[] array;
    
    // Constructor for stack
    StackUsingArray()
    {
        top = -1;
        array = (T[]) new Object [MAX_SIZE_OF_ARRAY];
    }
    
    // Parmeterized constructor to set maximum size of stack
    StackUsingArray(int sizeOfStack)
    {
        top = -1;
        array = (T[]) new Object [sizeOfStack];
    }
   
    /**
     *  To push data into the stack requires that stack should not be full
     *  @param data, data of type T that is to be inserted into the stack
     */
    @Override
    public boolean push(T data) 
    {  
        if(top == MAX_SIZE_OF_ARRAY - 1)
            return false;
        else
        {   
            top++;
            array[top] = data;
            return true;
        }
    }

    /**
     * To pop data from the stack
     * @return p
     */
    @Override
    public T pop() throws ArrayIndexOutOfBoundsException
    {
        if( isEmpty())
            throw new ArrayIndexOutOfBoundsException("Stack Underflow!");
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
    public T top() throws ArrayIndexOutOfBoundsException
    {
        if( isEmpty())
            throw new ArrayIndexOutOfBoundsException("Stack Underflow!");
        else
            return array[top];
    }
}