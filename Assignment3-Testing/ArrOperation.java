package assignment3;

class ArrayOperation
{
    final String ERROR_MESSAGE = "Array is empty!";
    
    /**
    * To count number of clumps in an array, which are a series of 2 or more adjacent elements of the same value.
    * @param array, integer array requires that it shouldn't be empty
    * @return count, contains the number of clumps found in given array
    * @throws Assertion Error
    */
    public int countClumps(int array[]) throws AssertionError
    {
        if( array.length < 1)
            throw new AssertionError(ERROR_MESSAGE);
        
        int count = 0;
        int i = 0;
        while ( i < array.length - 1)
        {
            if( array[i] == array[i + 1])
            {
                if ( i == 0)
                    count ++;
                else
                    if(array[i] != array[ i - 1])
                        count++;
                i++;
            } 
            else
                i++;
         }
         return count;
     }
   
   /** To find size of largest mirror section in an array, which is a group of contiguous elements such that somewhere in the array,
    *  the same group appears in reverse order.
    *  @param array, array of integers, requires that it shouldn't be empty
    *  @return max_size, contains size of largest mirror section found.
    *  @throws Assertion Error in case array is empty
    */
    public int maxMirror(int array[]) throws AssertionError
    {
        if( array.length < 1)
            throw new AssertionError(ERROR_MESSAGE);
        
        int current_size,start,end;
        int max_size = 0;
        
        for(int i = 0 ; i < array.length ; i++)
        {
            for(int j = array.length - 1 ; j >= 0;  j--)
            {
                current_size = 0;
                start = i;
                end = j;
                while ((start < array.length && end >= 0) && (array[i] == array[j]))
                {
                    start++;
                    end--;
                    current_size++;
                }
                if (current_size > max_size)
                    max_size = current_size;
            }
        }
        return max_size;
     }

   /** 
    * To return the index if there is a place to split the input array so that the sum of the numbers on one side is equal 
      to the sum of the numbers on the other side else return -1.
    * @param array, array of integers requires that it shouldn't be empty.
    * @return j, index from which array can be split otherwise -1.
    * @throws Assertion Error
    */
    public int splitArray(int array[]) throws AssertionError
    {
        if( array.length < 1)
            throw new AssertionError(ERROR_MESSAGE);
        
        int total_sum = 0;
        
        for(int i = 0; i < array.length; i++)
        {
            total_sum += array[i];
        }
        
        int sum_from_end = 0;
        int j = array.length - 1;
        boolean flag = true;

        while((j > 0) && (flag))
        {
            sum_from_end += array[j];

            if(total_sum - sum_from_end == sum_from_end)
                flag = false;
            else
                j--;
        }
        if(flag)
            return -1;
        else
            return j;
    }

    /**
    * To return an array that contains exactly the same numbers as the input array, but rearranged so that every x 
      is immediately followed by a y.
    * @param x, value in array whose position remains fixed.
    * @param y, value in array which can be move within array.
    * @return array, array of integers requires that it shouldn't be empty, number of x should be equal to y, no 2 adjacent x must
       be present and x shouldn't occur at the last index of array.
    * @throws AssertionError
    */
    public int[] fixXY(int[] array, int x, int y) throws AssertionError
    {
        int[] inputArray = array;
        
        if(inputArray.length == 0)
            throw new AssertionError(ERROR_MESSAGE);
        
        if(inputArray[inputArray.length - 1] == x)
            throw new AssertionError(x + "cannot be at last");
        
        if(!checkingEqualOccurrence(inputArray, x, y))
            throw new AssertionError("occurrences of " + x + " and " + y +" are not equal.");

        int indexOfY = -1;
        for(int i = 0; i < inputArray.length; i++)
        {
            if(inputArray[ i ] == y)
            {
                indexOfY = i;
                break;
            }
        }
        for(int i = 0; i< inputArray.length - 1; i++)
        {
            if(inputArray[ i ] == x)
            {
                i++;
                if(inputArray[ i ] == x)
                {
                    throw new AssertionError("2 " + x + " cannot be adjacent.");
                }
                int temporaryIndexOfY = indexOfY;
                for(int j = indexOfY + 1; j < inputArray.length; j++)
                {
                    if(inputArray[j] == y)
                    {
                        indexOfY = j;
                        break;
                    }
                }
                inputArray[temporaryIndexOfY] = inputArray[i];
                inputArray[i] = y;
            }
        }
        return inputArray;
    }
    
    /**
     * Checks if 2 elements have equal number of occurrences in an array
     * @param inputArray, list of numbers
     * @param firstElement, first number
     * @param secondElement, second number
     * @return true if occurrence of both elements are equal, otherwise false
     */
    public static boolean checkingEqualOccurrence(int[] inputArray, 
            int firstElement, int secondElement)
    {
        int occurrenceOfFirstElement = 0, occurrenceOfSecondElement = 0;
        for(int i = 0; i < inputArray.length; i++)
        {
            if(inputArray[i] == firstElement)
                occurrenceOfFirstElement += 1;
            
            if(inputArray[i] == secondElement)
                occurrenceOfSecondElement += 1;
        }
        if (occurrenceOfFirstElement == occurrenceOfSecondElement)
            return true;
        
        return false;
    }
}
