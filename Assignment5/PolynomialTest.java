package assignment5;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest {

    int[] test_array1 = new int[]{4,1,1,4,3,2};
    int[] test_array2 = new int[]{5,8,2,4,1,9};
    
    Polynomial first_poly = new Polynomial(test_array1);
    Polynomial second_poly = new Polynomial(test_array2);
    
    //Positive test cases for evaluate polynomial
    @Test
    public void testEvaluate1() {
        assertEquals(8, first_poly.evaluatePolynomial(1), 0);
    }
    
    @Test
    public void testEvaluate2() {
        assertEquals(36, first_poly.evaluatePolynomial(2), 0);
    }
    
    //Positive test cases for evaluate polynomial
    @Test
    public void testEvaluate3() {
        assertNotEquals(40, first_poly.evaluatePolynomial(3), 0);
    }
    
    @Test
    public void testEvaluate4() {
        assertNotEquals(35, first_poly.evaluatePolynomial(4), 0);
    }
    
    //Positive test cases for finding degree of a polynomial
    @Test
    public void testFindDegree1() {
        assertEquals(9, second_poly.findDegreeOfPolynomial(), 0);
    }
    
    @Test
    public void testFindDegree2() {
        assertEquals(4, first_poly.findDegreeOfPolynomial(), 0);
    }
    
   //Negative test cases for finding degree of a polynomial
    @Test
    public void testFindDegree3() {
        assertNotEquals(8, second_poly.findDegreeOfPolynomial(), 0);
    }
    
    @Test
    public void testFindDegree4() {
        assertNotEquals(1, first_poly.findDegreeOfPolynomial(), 0);
    }

}
