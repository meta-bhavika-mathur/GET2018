package assignment5;

public final class IntSet {

        final private String ERROR_MESSAGE1 = "Null pointer exception!";
        final private String ERROR_MESSAGE2 = "Size of subset should be less than or equal to superset!";
        final private String ERROR_MESSAGE3 = "Set can only contain values between 1-1000!";
        final private int[] integer_array;
        
        // Constructor for initializing size of Integer Set
        public IntSet(int size) throws NullPointerException
        { 
            if(size <= 0)
                throw new NullPointerException("ERROR_MESSAGE1");
            
            integer_array = new int[size];
        }
        
        // Constructor for initializing Integer Set
        public IntSet(int[] array) throws NullPointerException, AssertionError
        {  
            // Check if array is empty
            if(array.length == 0)
                throw new NullPointerException("ERROR_MESSAGE1");
            
            // Check if value of set contains values outside the defined range i.e 1-1000
            for( int i = 0; i < array.length; i++)
            {
                if(!( array[ i ] >= 1 && array[i] <= 1000)
                    throw new AssertionError(ERROR_MESSAGE3);
            }
                   
            integer_array = array;
        }
        
        /**
        * To get data stored at position 'index' from integer array
        * @param index, index of element in array
        * @return value at position specified by 'index'
        */
       
        private int getData(int index) throws ArrayIndexOutOfBoundsException
        {
            if(integer_array.length == 0)
                throw new ArrayIndexOutOfBoundsException("ERROR_MESSAGE1");
            
            return integer_array[ index ];
        }
        
        /**
         * Utility method to perform binary search on an array, requires input array must be sorted
         * @param lwr, lower index of the array in which the value is to be searched
         * @param upr, upper index of the array in which the value is to be searched
         * @param search_value, the value to search
         * @return, index/position of element in array if found otherwise -1
         */
        public int binarySearch(int lwr, int upr, int search_value)
        {
            if (lwr <= upr)
            {
                int mid = ( lwr + upr ) /  2;
                
                if( integer_array[mid] == search_value)
                    return mid;
                if( search_value < integer_array[mid])
                    return binarySearch(lwr, mid - 1, search_value);
                if( search_value > integer_array[mid])
                    return binarySearch(mid + 1, upr, search_value);
            }
            return -1;   
        }
        
        /**
        * Utility method to sort elements of input array using selection sort
        * @return sorted array
        */
        public int[] selectionSort()
        {
            int[] array = new int[integer_array.length];
            array = integer_array;
            
            // One by one move boundary of unsorted subarray
            for (int i = 0; i < array.length-1; i++)
            {
                // Find the index of minimum element in unsorted array
                int min_idx = i;
                
                for (int j = i+1; j < array.length; j++)
                    if (array[j] < array[min_idx])
                        min_idx = j;
     
                // Swap the found minimum element with the first element
                if( min_idx != i)
                { 
                    int temp = array[min_idx];
                    array[min_idx] = array[i];
                    array[i] = temp;
                }
            }
            return array;
        }
        
        /**
         * To check whether the input value is a member of given set
         * @param check_value, value to be check for requires that it should range between 1-1000 only
         * @return true if input value is a member of the set otherwise false
         */
        public boolean isMember(int check_value) throws AssertionError
        {
            // If values doesn't lie in the range 1-100 throw error
            if(! (check_value >= 1 && check_value <= 1000))
                throw new AssertionError(ERROR_MESSAGE3);
            
            for(int i = 0; i <= integer_array.length - 1; i++)
            {
                if(integer_array[ i ] == check_value)
                    return true;
            }
            return false;
        }
        
        /**
         * To get size of the set
         * @return size of the set
         */
        public int getSize()
        {
            return integer_array.length;
        }
        
        // Get array from set
        public int[] convertSetToArray()
        {
            int a[] = new int[getSize()];
            for( int i = 0; i < getSize(); i++)
            {
                a[i] = getData(i);
            }
            return a;
        }
        
        /**
         * To check whether a set in a subset of another set i.e all the elements of input set must be present in the set in comparison
         * @param s, set to check for subset
         * @return true if the given set is a subset of the calling set
         * @throws AssertionError, if the length of parameter subset is greater than the set object in reference
         */
        public boolean isSubSet(IntSet s) throws AssertionError
        {
            // Throw error if size of s is greater than calling set
            if( integer_array.length < s.getSize())
                throw new AssertionError("ERROR_MESSAGE2");
            
            int i;
            int j;
            int flag, count;
            i = count = 0;
            flag = 1;
            
            while( i < s.getSize() && flag == 1 )
            {
                j = 0;
                while( j < integer_array.length )
                {
                    if( integer_array[ j ] == s.getData(i) )
                    {
                        count++;
                        break;
                    }
                    else
                        j++;
                }
                if (j == integer_array.length)
                    flag = 0;
                i++;
            }
            if( count == s.getSize())
                return true;
            else 
                return false;
        }
        
        /**
         * To find union of 2 set, union contains all the elements of both the sets.
         * @param first_set, first operand to perform union operation
         * @param second_set, second operand to perform union operation
         * @return union_result, a set containing union of first_set and second_set
         */
        public IntSet getUnion(IntSet first_set, IntSet second_set)
        {
            
            IntSet big_set,small_set;
            
            int[] first = first_set.selectionSort();
            int[] second = second_set.selectionSort();
           
            // To find bigger and smaller set among the 2 sets
            if (first.length >= second.length)
            {
                 big_set = new IntSet(first);
                 small_set = new IntSet(second);    
            }
            else
            {
                big_set = new IntSet(second);
                small_set = new IntSet(first);; 
            }
            
            // Copy elements of max set into a bigger set first for union
            int size = big_set.getSize() + small_set.getSize();
            int array_union[] = new int[ size ];
            
            // Initialize array elements
            for( int i = 0; i < size; i++)
                array_union[ i ] = 0;
            
            // Copy elements of both the arrays one by one in new array
            int i, j, k;
            i = j = k = 0;
            
            while( i < big_set.getSize() && j < small_set.getSize())
            {
                if( small_set.getData(j) < big_set.getData(i))  
                {
                    array_union[ k ] = small_set.getData(j);
                    k++;
                    j++;
                }
                else
                    if( small_set.getData(j) > big_set.getData(i))  
                    {
                        array_union[ k ] = big_set.getData(i);
                        k++;
                        i++;
                    }
                    else
                    {
                        array_union[ k ] = big_set.getData(i);
                        i++;
                        j++;
                        k++;
                    }
            }
            
            // If any set remains
            if( i != big_set.getSize())
            {
                for( int x = i; x < big_set.getSize(); x++ )
                {
                    array_union[ k ] = big_set.getData(x);
                    k++;
                }
            }
            
            int[] final_array = new int[ k ];
           
            //Copy union elements into final array
            for(int x = 0; x < k; x++)
            {
                final_array[ x ] = array_union[ x ];
            }
          
            IntSet union_result = new IntSet(final_array);
            return union_result;
        } 
        
        /**
         * To find compliment of a set, compliment of a set contains all the elements of union except those that are present in that set.
           It is assumed that universal set contains all the integers from 1 to 1000.
         * @return complement_set, set containing compliment of the set.
         */
        public IntSet getComplement()
        {
            int[] universal_array = new int[1000];
            int k = 0;
            
            for (int i = 1; i <= 1000; i++)
            {
                if(!isMember( i ))
                {
                    universal_array[ k ] = i;
                    k++;
                }
            }
            
            int[] complement_array = new int[ k ];
                
            for(int j = 0; j < k; j++)
                complement_array[j] = universal_array[ j ];
            
            IntSet complement_set = new IntSet(complement_array);
            return complement_set; 
        }
}
