package assignment5;

import static org.junit.Assert.*;
import org.junit.Test;

public class PolynomialTest
{
    // Test to evaluate polynomial when correct arguments are passed
    @Test
    public void testEvaluatePolynomial()
    {
        int[] array = {1, 0, 4};
        Polynomial polynomial = new Polynomial(array);
        float expected_value = 17;
        assertEquals(expected_value, polynomial.evaluatePolynomial(2), 0);
    }
    
    // Test to evaluate polynomial when value of variable passed is zero.
    @Test
    public void testEvaluatePolynomialWithZeroParameter()
    {
        int[] array = {1, 2, 3};
        Polynomial polynomial = new Polynomial(array);
        float expected_value = 1;
        assertEquals(expected_value, polynomial.evaluatePolynomial(0), 0);
    }
    
    // Test to evaluate polynomial when empty array is referenced with evaluate polynomial
    @Test (expected = NullPointerException.class)
    public void testEvaluatePolynomialWithEmptyArray()
    {
        int[] array = {};
        Polynomial polynomial = new Polynomial(array);
        float expected_value = 0;
        assertEquals(expected_value, polynomial.evaluatePolynomial(2), 0);
    }
    
    // Test to find degree of polynomial with valid input
    @Test
    public void testFindDegree()
    {
        int[] array = {1, 0, 4};
        Polynomial polynomial = new Polynomial(array);
        int expected_output = 2;
        assertEquals(expected_output, polynomial.getPolynomialDegree());
    }
    
    // Test to find degree when empty array is referenced
    @Test(expected = NullPointerException.class)
    public void testDegreeWithEmptyArray()
    {
        int[] array = {};
        Polynomial polynomial = new Polynomial(array);
        
        polynomial.getPolynomialDegree();
    }
    
    // Test case for addition of 2 polynomials with valid inputs
    @Test
    public void testAddPolynomials()
    {
        int[] first_array = {1, 0, 4};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {3, 4, 0, 0, 5};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        int[][] expected_output = {{4, 0},
                                  {4, 1}, 
                                  {4, 2}, 
                                  {5, 4}};
        
        Polynomial result;
        result = first_polynomial.addPolynomials(second_polynomial);
        int[][] actual_output = result.getPolynomialArray();
        
        assertArrayEquals(expected_output, actual_output);
    }
    
    // Test case for addition of 2 polynomials with first array empty as input
    @Test(expected = NullPointerException.class)
    public void testAddPolynomialsWithFirstArrayAsEmpty()
    {
        int[] first_array = {};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {3, 4, 0, 0, 5};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        first_polynomial.addPolynomials(second_polynomial);
    }
    
    //Tests case for addition of 2 polynomials with second array empty as input
    @Test(expected = NullPointerException.class)
    public void testAddPolynomialsWithSecondArrayAsEmpty()
    {
        int[] first_array = {3, 4, 0, 0, 5};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        first_polynomial.addPolynomials(second_polynomial);
    }
    
    // Test case for multiplication of 2 polynomials with valid input value 
    @Test
    public void testMultiplyPolynomials()
    {
        int[] first_array = {1, 0, 4};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {3, 4, 0, 0, 5};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        int[][] expected_output = {{3, 0},
                                  {4, 1}, 
                                  {12, 2},
                                  {16, 3},
                                  {5, 4},
                                  {20, 6}};
        
        Polynomial result;
        result = first_polynomial.multiplyPolynomials(second_polynomial);
        int[][] actual_output = result.getPolynomialArray();
        assertArrayEquals(expected_output, actual_output);
    }
    
    // Tests case for multiplication of 2 polynomials with one input as empty
    @Test(expected = NullPointerException.class)
    public void testMultiplyPolynomialsWithFirstArrayAsEmpty()
    {
        int[] first_array = {3, 4, 0, 0, 5};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        first_polynomial.multiplyPolynomials(second_polynomial);
    }
    
    // Tests case for multiplication of 2 polynomials with both input as empty
    @Test(expected = NullPointerException.class)
    public void testMultiplyPolynomialsWithSecondArrayAsEmpty()
    {
        int[] first_array = {};
        Polynomial first_polynomial = new Polynomial(first_array);
        
        int[] second_array = {};
        Polynomial second_polynomial = new Polynomial(second_array);
        
        first_polynomial.multiplyPolynomials(second_polynomial);
    }
}
