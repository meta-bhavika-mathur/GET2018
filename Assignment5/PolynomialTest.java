
package assignment5;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest
{
    //Tests cases for evaluate()
    @Test
    public void testEvaluate()
    {
        int[] array = {1, 0, 4};
        Polynomial poly = new Polynomial(array);
        float expectedOutput = 17;
        assertEquals(expectedOutput, poly.evaluatePolynomial(2), 0);
    }
    
    @Test
    public void testEvaluateWithEmptyArray()
    {
        int[] inputArray = {};
        Polynomial poly = new Polynomial(inputArray);
        float expectedOutput = 0;
        assertEquals(expectedOutput, poly.evaluatePolynomial(2), 0);
    }
    
    @Test
    public void testEvaluateWithValueEqualsZero()
    {
        int[] inputArray = {1, 2, 3};
        Polynomial poly = new Polynomial(inputArray);
        float expectedOutput = 1;
        assertEquals(expectedOutput, poly.evaluatePolynomial(0), 0);
    }
    
  //Tests cases for degree()
    @Test
    public void testDegree()
    {
        int[] inputArray = {1, 0, 4};
        Polynomial poly = new Polynomial(inputArray);
        int expectedOutput = 2;
        assertEquals(expectedOutput, poly.getPolynomialDegree());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testDegreeWithEmptyArray()
    {
        int[] inputArray = {};
        Polynomial poly = new Polynomial(inputArray);
        poly.getPolynomialDegree();
    }
    
    //Tests cases for addPoly()
    @Test
    public void testAddPolynomial()
    {
        int[] array_first = {1, 0, 4};
        Polynomial polynomial_first = new Polynomial(array_first);
        int[] array_second = {3, 4, 0, 0, 5};
        Polynomial polynomial_second = new Polynomial(array_second);
        int[][] output_array = {{4, 0}, {4, 1}, {4, 2}, {5, 4}};
        
        Polynomial demo = new Polynomial(array_first);
        Polynomial result = demo.addPolynomial(polynomial_first, polynomial_second );
        
        
        int[][] result_array = result.getPolynomialArray();
        assertArrayEquals(output_array, result_array);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddPolyWithFirstArrayAsEmpty()
    {
        int[] inputArray1 = {};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {3, 4, 0, 0, 5};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.addPoly(poly2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddPolyWithSecondArrayAsEmpty()
    {
        int[] inputArray1 = {3, 4, 0, 0, 5};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.addPoly(poly2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddPolyWithBothArraysAsEmpty()
    {
        int[] inputArray1 = {};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.addPoly(poly2);
    }
    
  //Tests cases for multiplyPoly()
    @Test
    public void testMultiplyPoly()
    {
        int[] inputArray1 = {1, 0, 4};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {3, 4, 0, 0, 5};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        int[][] expectedOutput = {{3, 0},
                                  {4, 1}, 
                                  {12, 2},
                                  {16, 3},
                                  {5, 4},
                                  {20, 6}};
        
        Polynomial poly;
        poly = poly1.multiplyPoly(poly2);
        int[][] actualOutput = poly.getPolynomialArray();
        assertArrayEquals(expectedOutput, actualOutput);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMultiplyPolyWithFirstArrayAsEmpty()
    {
        int[] inputArray1 = {};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {3, 4, 0, 0, 5};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.multiplyPoly(poly2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMultiplyPolyWithSecondArrayAsEmpty()
    {
        int[] inputArray1 = {3, 4, 0, 0, 5};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.multiplyPoly(poly2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMultiplyPolyWithBothArraysAsEmpty()
    {
        int[] inputArray1 = {};
        Polynomial poly1 = new Polynomial(inputArray1);
        
        int[] inputArray2 = {};
        Polynomial poly2 = new Polynomial(inputArray2);
        
        poly1.multiplyPoly(poly2);
    }
}
