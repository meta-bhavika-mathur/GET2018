package session2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEvaluatePostfixExpression 
{

    // Test case one
    @Test
    public void testEvaluatePostfixfExpressionTestCaseOne() 
    {
        String postFixExression = "3 2 * 2 ^ 5 3 - 8 4 / * -";
        int result = EvaluatePostfixExpression. evaluatePostfix(postFixExression);
        assertEquals(32, result);
    }  
    
    // Test case two
    @Test
    public void testEvaluatePostfixfExpressionTestCaseTwo() 
    {
        String postFixExression = "9 3 / 5 + 7 2 - *";
        int result = EvaluatePostfixExpression. evaluatePostfix(postFixExression);
        assertEquals(40, result);
    }  
    
    // Test case three
    @Test
    public void testEvaluatePostfixfExpressionTestCaseThree() 
    {
        String postFixExression = "5 2 1 - - 3 1 4 + + *";
        int result = EvaluatePostfixExpression. evaluatePostfix(postFixExression);
        assertEquals(-48, result);
    }  
    
}