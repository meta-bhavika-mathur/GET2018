package session2;

import Session1.StackUsingArray;

public class EvaluatePostfixExpression 
{
   
    /**
     * To evaluate a postfix expression requires that tokens are separated by white spaces in input postfix expression
     * @param result, result obtained after evaluation of postfix expression
     * @return result, result of evaluation of expression, else 0
     */
    public static int evaluatePostfix( String postfixExpression ) 
    {
    
            if ( postfixExpression == null ) 
               return 0;
            
            String[] arrayOfPostFixTokens = postfixExpression.split(" ");
            StackUsingArray<String> stack = new StackUsingArray<String>();
            int result, firstOperand, secondOperand;
            
            for (int index = 0; index < arrayOfPostFixTokens.length; index++) 
            {
                if (EvaluatorUtility.isDigit(arrayOfPostFixTokens[index])) 
                {
                    stack.push(arrayOfPostFixTokens[index]);
                }
                else 
                    if (EvaluatorUtility.isArithmeticOperator(arrayOfPostFixTokens[index]))
                    {
                        secondOperand = Integer.parseInt(stack.pop());
                        firstOperand = Integer.parseInt(stack.pop());
                        stack.push("" + evaluateExpression(firstOperand, secondOperand, arrayOfPostFixTokens[index]));
                    }        
                    else 
                       return 0;
             }
            
            result = Integer.parseInt(stack.pop());
            if (stack.isEmpty())
                return result;
            return 0;
    }
    
    /**
     * To evaluate a binary expression based on operator
     * @param firstOperand, first operand
     * @param secondOperand, second operand
     * @param operator, operator to evaluate expression 
     * @throws AssertionError if invalid operator is received
     */
    public static int evaluateExpression(int firstOperand, int secondOperand, String operator) throws AssertionError 
    {
       
        if ("+".equals(operator)) 
            return firstOperand + secondOperand;
        
        else if ("-".equals(operator)) 
            return firstOperand - secondOperand;
        
        else if ("*".equals(operator)) 
            return firstOperand * secondOperand;
        
        else if ("/".equals(operator))
            return firstOperand / secondOperand;
        
        else if ("%".equals(operator))
            return firstOperand % secondOperand;
        
        else if ("^".equals(operator))
            return (int) Math.pow(firstOperand, secondOperand);
        else 
            throw new AssertionError("Wrong operator!");
    }
}
