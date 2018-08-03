package session2;

import Session1.StackUsingArray;

public class InfixToPostfix 
{
    /**
     * To convert an infix expression into postfix expression
     * @param infixExpression, an infix expression such that tokens are separated by whitespace
     * @return postfixExpression, resultant postfix expression 
     */
    public String convertInfixToPostfix( String infixExpression ) 
    {
    
            if ( infixExpression == null ) 
               return "Null Expression Recieved!";
            
            if( infixExpression.length() <= 2 )
                return infixExpression;
      
            String[] infixArray = infixExpression.split(" ");
            StackUsingArray<String> stack = new StackUsingArray<String>();
            String postfixExpression = new String();
            
            
            for (int index = 0; index < infixArray.length; index++) 
            {
                String currentItem = infixArray[index];
                
                if (EvaluatorUtility.isDigit( currentItem ))
                    postfixExpression = postfixExpression.concat( currentItem );
                else
                    if( currentItem.equals("(") )
                        stack.push(currentItem);
                    else
                        if( currentItem.equals(")") )
                        {
                            while ( !stack.isEmpty() && !stack.top().equals("(") )         
                            {
                                postfixExpression = postfixExpression.concat(stack.pop()); 
                            }
                            if(!stack.isEmpty() && stack.top().equals("("))
                                stack.pop();
                            else
                                return "Invalid Expression!";
                         }
                         else
                         {
                            while( !stack.isEmpty() && ( EvaluatorUtility.getPriority(currentItem) <= EvaluatorUtility.getPriority(stack.top())))
                            {
                                postfixExpression = postfixExpression.concat( stack.pop() ); 
                            }
                            stack.push(currentItem);
                         }
            }
            while ( !stack.isEmpty() )
            {
                postfixExpression = postfixExpression.concat( stack.pop() ); 
                System.out.println(postfixExpression);
            }
           return postfixExpression.toString();
   }           
}               