package session2;

public class EvaluatorUtility 
{
    
    /**
     * To check whether the given token of infix expression is an arithmetic operator or not
     * @param value, string containing either digit or operand
     * @return true, if the given token is an arithmetic operator otherwise false
     */
    public static boolean isArithmeticOperator( String value ) 
    {
        if ( value.length() != 1 )
            return false;
        else
        {
            char operator = value.charAt(0);
            if ( operator == '+' || operator == '-' || operator == '*' || operator == '/' || operator == '^' || operator == '%' )
                return true;
            else 
                return false;    
        }
      
    }
    
    /**
     * To find priority of the given operator with priorities set according to the rules of precedence of operators. The operator with higher 
     * priority is assigned a higher number.
     * @param operator
     * @return
     */
    public static int getPriority( String operator ) 
    {
        if ("||".equals(operator)) 
                return 1;
        else 
            if ("&&".equals(operator)) 
                return 2;
        else 
            if ("!=".equals(operator) || "==".equals(operator)) 
                return 3;
        else 
            if (">=".equals(operator) || "<=".equals(operator) || "<".equals(operator) || ">".equals(operator) ) 
                return 4;
        else 
            if ("+".equals(operator) || "-".equals(operator)) 
                return 5;
        else 
            if ("*".equals(operator) || "%".equals(operator) || "/".equals(operator)) 
                return 6;
        else
                return -1;
    }

    /**
     * to check whether the token of a infix expression is a digit or not 
     * @param value, string containing either digit or operand
     * @return true, if the given token is a digit or a series of digits, otherwise false
     */
     public static boolean isDigit(String token) 
     {
         for ( int index = 0; index < token.length(); index++ ) 
             if (!((token.charAt(index) >= '0') && (token.charAt(index) <= '9')))
                 return false;
         return true;
     }
     
     /**
      * To check whether the given token is alphabet or digit
      * @param value, token to check
      * @return true if token is an alphabet or series of alphabet, otherwise false
      */
     public static boolean isAlphabet(String value) 
     {
         String valueInUpperCase = value;
         
         for (int index = 0; index < valueInUpperCase.length(); index++) 
             if (!Character.isAlphabetic(value.charAt(index))))
                 return false;
         return true;
     }
}
