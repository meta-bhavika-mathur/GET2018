package assignment5;

public final class Polynomial
{
    final String NULL_POINTER_EXCEPTION_MESSAGE = "Null pointer exception!";
    final String OUT_OF_BOUNDS_MESSSAGE = "Array index out of bounds exception!";
    private int[][] polynomial_array;
    
    // Parameterized constructor of  class Polynomial.
    Polynomial(int[] array) throws NullPointerException
    {
    	 if( array.length <= 0)
         	throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    	 
    	 polynomial_array = convertIntoTwoDimensional(array); 
    }
    
    /**
     * To evaluate the polynomial expression where value of variable is provided as input.
     * @param variable, value for variable of polynomial to evaluate the polynomial expression.
     * @return result, the result obtained after evaluating polynomial expression for given variable.
     */
    public float evaluatePolynomial(int variable) throws NullPointerException
    {
    	 if( polynomial_array.length <= 0)
          	throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    	
    	 float result = 0;
  
         for(int i = 0; i < polynomial_array.length; i++)
        	 result += polynomial_array[i][0] * (Math.pow(variable, polynomial_array[i][1]));           
    
         return result;
    }
    
    /**
     * To return degree of polynomial i.e the highest value of exponent in the expression.
     * @return highest power in the polynomial.
     */
    public int getPolynomialDegree() throws IndexOutOfBoundsException
    {
        if( polynomial_array.length == 0)
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSSAGE);
        
        return polynomial_array[ polynomial_array.length - 1  ][ 1 ];
    }
    
    /**
     * To add two polynomial expressions.
     * @param first, first polynomial as operand for addition operation.
     * @param second, first polynomial as operand for addition operation
     * @return, a polynomial expressions representing sum of given 2 polynomial expressions.
     */
    public Polynomial addPolynomials(Polynomial polynomial) 
    {
    	if( polynomial_array.length <= 0)
          	throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    	
    	int[] sum;
    	
        // Compare which polynomial has higher degree to initialize new matrix of that size
        if(getPolynomialDegree() >= polynomial.getPolynomialDegree() )
        {
        	sum = new int[getPolynomialDegree() + 1];
        }
        else
        {
        	sum = new int[polynomial.getPolynomialDegree() + 1];
        }
        // Copying all the elements of bigger polynomial array into new polynomial array
        for(int i = 0; i < polynomial_array.length; i++)
        {
        	sum[polynomial_array[i][1]] = polynomial_array[i][0];
        }
        // Performing sum
        for(int i = 0; i < polynomial.polynomial_array.length; i++)
        	sum[polynomial.polynomial_array[i][1]] += polynomial.polynomial_array[i][0];
       
        return new Polynomial(sum);
    }
    
    /**
     * To multiply two polynomial expressions.
     * @param first, first polynomial as operand for multiplication.
     * @param second, second polynomial as operand for multiplication.
     * @return result of multiplication of two polynomial expressions
     */
    public Polynomial multiplyPolynomials(Polynomial polynomial) throws NullPointerException
    {
    	if(( polynomial_array.length <= 0) || (polynomial.polynomial_array.length <= 0))
    		throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    	
    	int[] product_array = new int[ getPolynomialDegree() + polynomial.getPolynomialDegree() + 1 ];
    	
    	// Perform multiplication
        for(int i = 0; i < getPolynomialDegree(); i++)
        {
            for(int j=0; j < polynomial.getPolynomialDegree(); j++)
            {
            	product_array[polynomial_array[i][1] + polynomial.polynomial_array[j][1]] += 
            			polynomial_array[i][0] * polynomial.polynomial_array[j][0];
            }
        }
        return  new Polynomial(product_array);
    }
    
    /** Utility method to convert 1D Array into 2D Array, whose first column represent coefficient and 
 		their powers.
     * @param one_dimensional_array, 1D Array with coefficient stored at index equal to exponent.
     * @return 2D array with coefficient and exponent stored, requires that coefficient must not be zero.
     */
    private int[][] convertIntoTwoDimensional(int[] one_dimensional_array) throws NullPointerException
    {
    	int polynomial_size = 0;
    	
    	if( one_dimensional_array.length <= 0)
    		throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    
    	for( int i = 0; i < one_dimensional_array.length; i++ )
    	{
    		if( one_dimensional_array[i] != 0 )
    			polynomial_size++;
    	}
    
    	int[][] new_array = new int[polynomial_size][2];
    	int count = 0;
    
    	for(int i=0; i < one_dimensional_array.length; i++)
    	{
    		if(one_dimensional_array[i] != 0)
    		{
    			new_array[count][0] = one_dimensional_array[i];
    			new_array[count][1] = i;
    			count++;
    		}
    	}
    	return new_array;
    }

    /**
     * Utility method to get 2D array of polynomial with stored coefficients and their powers
     * @return polynomial in the form of 2D array.
     */
    int[][] getPolynomialArray() throws NullPointerException
    {
    	if( polynomial_array.length <= 0)
    		throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    	
    	return polynomial_array;
    }    
}
