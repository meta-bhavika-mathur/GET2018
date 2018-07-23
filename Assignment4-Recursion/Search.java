package assignment4;

public class Search {
    
    final String ERROR_MESSAGE = "Element Not Found!";
    
    /**
     * Linear search searches for an element in an array by comparing each element of the array for the target value 
       until a match is found or until all the elements have been searched
     * @param a array to search
     * @param search_value value to search for
     * @return index i such that a[i]=search_value if value if present in array otherwise -1
     */
    public int linearSearch(int a[] , int search_value) throws AssertionError
    {
        int[] b;
        if( a.length < 1)
            throw new AssertionError(ERROR_MESSAGE);
        
        if(a[a.length - 1] == search_value)
        {
            return a.length-1;
        }
       
        b = new int[a.length - 1];
        for (int i = 0; i <= a.length - 2 ; i++)
            b[i] = a[i];
                
        return linearSearch(b, search_value);
    }
    
    /**
     * Binary search finds the position of a target value within a sorted array search compares the target value to the 
       middle element of the array. If they are not equal,and the search continues on the remaining half.
     * @param an array to search, requires that array must be sorted.
     * @param search_value value to search for.
     * @return index i such that a[i]=search_value if value if present in array otherwise -1.
     */
    public int binarySearch(int array[], int lwr, int upr, int search_value) throws NullPointerException
    {
        if( array.length < 1)
            throw new NullPointerException(ERROR_MESSAGE);
        if (lwr <= upr)
        {
            int mid = ( lwr + upr ) /  2;
            
            if( array[mid] == search_value)
                return mid;
            if( search_value < array[mid])
                return binarySearch(array, lwr, mid - 1, search_value);
            if( search_value > array[mid])
                return binarySearch(array, mid + 1, upr, search_value);
        }
        return -1;  
    }
}
