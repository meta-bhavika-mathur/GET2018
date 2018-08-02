package assignment4;

import static org.junit.Assert.*;
import org.junit.Test;

public class MathOperationTest {

    MathOperation obj = new MathOperation();
    
    //Positive test cases for LCM
    @Test
    public void lcmTest1() {
        assertEquals(24, obj.findLeastCommonMultiple(8,3));
    }

    @Test
    public void lcmTest2() {
        assertEquals(30, obj.findLeastCommonMultiple(15,10));
    }

    //Negative test cases for LCM
    @Test
    public void lcmTest3() {
        assertNotEquals(14, obj.findLeastCommonMultiple(4,2));
    }
    @Test
    public void lcmTest4() {
        assertNotEquals(8, obj.findLeastCommonMultiple(15,10));
    }
    
    @Test (expected = AssertionError.class)
    public void lcmFirstNumSmall() {
        obj.leastCommonMultiple(2,4);
    }
    
    //When first argument is less than second
    @Test (expected = AssertionError.class)
    public void lcmFirstNumSmall1() {
        assertEquals(4, obj.findLeastCommonMultiple(2,4));
    }
    
    //When an argument is less than second
    @Test (expected = AssertionError.class)
    public void lcmNumNegative() {
        assertEquals(2, obj.hcf(-2,4));
    }
    
    
    //Positive test cases for HCF
    @Test
    public void hcfTest1() {
        assertEquals(5, obj.findHighestCommonFactor(15, 10));
    }

    @Test
    public void hcfTest2() {
        assertEquals(2, obj.findHighestCommonFactor(4, 2));
    }
    
    //Negative test cases for HCF
    @Test
    public void hcfTest3() {
        assertNotEquals(15, obj.findHighestCommonFactor(15, 10));
    }

    @Test
    public void hcfTest4() {
        assertNotEquals(3, obj.findHighestCommonFactor(6, 2));
    }
    
    //When first argument is less than second
    @Test (expected = AssertionError.class)
    public void hcfFirstNumSmall1() {
        assertEquals(2, obj.findHighestCommonFactor(2,4));
    }
    
    //When an argument is less than second
    @Test (expected = AssertionError.class)
    public void hcfNumNegative() {
        assertEquals(2, obj.findHighestCommonFactor(-2,4));
    }
    
}   
