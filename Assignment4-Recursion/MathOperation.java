package assignment4;

public class MathOperation {
    
    final String error_message1 = "first number should be greater than or equal to second!";
    final String error_message2 = "Input value should be positive!";
   
    /**
     * To find HCF (Highest Common Factor of 2 Numbers), requires that first number must be greater than or equal to the other number.
     * @param first, larger number, requires that it should be positive.
     * @param second, smaller number, requires that it should be positive.
     */
    int hcf(int first, int second) throws AssertionError
    {
        if (first < second)
            throw new AssertionError(error_message1);
        if ( first < 0 || second < 0)
            throw new AssertionError(error_message2);
        else
        {
            if( second == 0)
                return first;
            else
                return hcf(second, first % second);
        }
    }
    
    /**
     * To find LCM (Least Common Multiple) of 2 numbers, requires that first number is greater than other.
     * @param first_number, requires that it should be positive.
     * @param second_number, requires that it should be positive.
     * @return
     */
    int lcm(int first_number, int second_number) throws AssertionError
    {
        if(first_number < second_number)
            throw new AssertionError(error_message1);
        if ( first_number < 0 || second_number < 0)
            throw new AssertionError(error_message2);
        
       return ( first_number * second_number) / hcf(first_number, second_number);
    }
}