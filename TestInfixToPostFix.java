package session2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestInfixToPostFix 
{

    // Test case one
    @Test
    public void testInfixToPrefixTestCaseOne() 
    {
        InfixToPostfix object = new InfixToPostfix();
        String infixExpression = "3 + 5";
        String expectedPostfixExpression = "35+";
        
        assertEquals( expectedPostfixExpression, object.convertInfixToPostfix( infixExpression) );
    }

    // Test case two
    @Test
    public void testInfixToPrefixTestCaseTwo() 
    {
        InfixToPostfix object = new InfixToPostfix();
        String infixExpression = "12 + 60 – 23";
        String expectedPostfixExpression = "1260+23–";
        
        assertEquals( expectedPostfixExpression, object.convertInfixToPostfix( infixExpression) );
    }
    
    // Test case three
    @Test
    public void testInfixToPrefixTestCaseThree()
    {
        InfixToPostfix object = new InfixToPostfix();
        String infixExpression = "( 5 + 8 ) * ( 1 – 4 )";
        String expectedPostfixExpression = "58+14–*";
        
        assertEquals( expectedPostfixExpression, object.convertInfixToPostfix(infixExpression) );
    }
}