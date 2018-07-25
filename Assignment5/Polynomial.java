package assignment5;

public final class Polynomial
{
    private int[][] polynomial_array;
    
    /** Utility method to convert 1D Array into 2D Array, whose first column represent coefficient and 
     	their powers.
     * @param one_dimensional_array, 1D Array with coefficient stored at index equal to exponent.
     * @return 2D array with coefficient and exponent stored, requires that coefficient must not be zero.
     */
    private int[][] convertIntoTwoDimensional(int[] one_dimensional_array)
    {
        int polynomial_size = 0;
        
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
    
   
    // Parameterized constructor of  class Polynomial.
    Polynomial(int[] array)
    {
        polynomial_array = convertIntoTwoDimensional(array); 
    }
    
    /**
     * To get 2D array of polynomial with stored coefficients and their powers
     * @return polynomial in the form of 2D array.
     */
    int[][] getPolynomialArray()
    {
        return polynomial_array;
    }
    
    /**
     * To evaluate the polynomial expression where value of variable is provided as input.
     * @param variable, value for variable of polynomial to evaluate the polynomial expression.
     * @return result, the result obtained after evaluating polynomial expression for given variable.
     */
    float evaluatePolynomial(int variable)
    {
        float result = 0;
        
        for(int i = 0; i < polynomial_array.length; i++)
        	result += polynomial_array[i][0] * (Math.pow(variable, polynomial_array[i][1]));           
    
        return result;
    }
    
    /**
     * To return degree of polynomial i.e the highest value of exponent in the expression.
     * @return highest power in the polynomial.
     */
    int getPolynomialDegree() throws IndexOutOfBoundsException
    {
        if( polynomial_array.length == 0)
            throw new IndexOutOfBoundsException();
        
        return polynomial_array[ polynomial_array.length - 1  ][ 1 ];
    }
    
    /**
     * To add two polynomial expressions.
     * @param first, first polynomial as operand for addition operation.
     * @param second, first polynomial as operand for addition operation
     * @return, a polynomial expressions representing sum of given 2 polynomial expressions.
     */
    Polynomial addPolynomial(Polynomial first, Polynomial second)
    {
        int[] sum;
        int max;
        
        // Find polynomial with highest degree
        if( first.getPolynomialDegree() >= second.getPolynomialDegree() )
        	max = first.getPolynomialDegree();	
        else
        	max = first.getPolynomialDegree();
        
        sum = new int[ max + 1 ];
    	
        for(int i = 0; i < polynomial_array.length; i++)
        {
        	sum[ polynomial_array[i][1] ] = polynomial_array[i][0];
        }
   
        for(int i=0; i < second.polynomial_array.length; i++)
        {
        	sum[ second.polynomial_array[i][1] ] += second.polynomial_array[i][0];
        }
        return new Polynomial(sum);
    }
    
    /**
     * To multiply two polynomial expressions.
     * @param first, first polynomial as operand for multiplication.
     * @param second, second polynomial as operand for multiplication.
     * @return result of multiplication of two polynomial expressions
     */
    Polynomial multiplyPolynomial(Polynomial first, Polynomial second)
    {
        int[] product_array = new int[ first.getPolynomialDegree() + second.getPolynomialDegree() + 1 ];
        for(int i = 0; i < first.getPolynomialDegree(); i++)
        {
            for(int j=0; j < second.getPolynomialDegree(); j++)
            {
            	product_array[first.polynomial_array[i][1] + second.polynomial_array[j][1]] += 
            			first.polynomial_array[i][0] * second.polynomial_array[j][0];
            }
        }
        return  new Polynomial(product_array);
    }
}
