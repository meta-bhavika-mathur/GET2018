package assignment3;

class ArrOperation
{
    final String error_message = "Array is empty!";
    
    /**
    * To count number of clumps in an array, which are a series of 2 or more adjacent elements of the same value.
    * @param array, integer array requires that it shouldn't be empty
    * @return count, contains the number of clumps found in given array
    * @throws Assertion Error
    */
    public int countClumps(int array[]) throws AssertionError
    {
        if( array.length < 1)
            throw new AssertionError(error_message);
        
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
            {
                i++;
            }
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
            throw new AssertionError(error_message);
        
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
            throw new AssertionError(error_message);
        
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
        if( array.length < 1)
            throw new AssertionError(error_message);
        
        int[] a = array;
        int i = 0;
        int last_pos = 0;
        int flag,j,last_i = 0;
    
        while(i < a.length)
        {
            if(a[i] == x)
            {
                j = last_pos;
                flag = 1;
                while ((j < a.length) && (flag == 1))
                {
                    if((a[j] == y) && (j != i+1))
                    {
                        int temp = a[j];
                        a[j] = a[i +1];
                        a[i + 1] = temp;
                        
                        last_i = i + 1;
                        last_pos = j + 1;
                        i = i + 2;
                        flag = 0;
                    }
                    else
                        j++;
                }
            }
            i++;
        }
        return a;
    }
}
