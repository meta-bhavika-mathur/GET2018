package assignment1;

interface Numbers<T>
{
    T add(T firstNumber,T secondNumber);
    T subtract(T firstNumber,T secondNumber);
    T multiply(T firstNumber,T secondNumber);
    T divide(T firstNumber,T secondNumber);
    boolean compareEqualTo(T firstNumber,T secondNumber);
    boolean compareGreaterThan(T firstNumber,T secondNumber);
    boolean compareLessThan(T firstNumber,T secondNumber);
}

class HexCalculator implements Numbers<String>
{
    /**
     * To convert hexadecimal number into decimal.
     * @param hexa_num, hexadecimal number in string form.
     * @return decimal_num, number converted into hexadecimal form.
     */
    int hexaToDecimal(String hexa_num)
    {
        int decimal_num = 0;
        String digits = "0123456789ABCDEF";
        hexa_num = hexa_num.toUpperCase();
       
        for (int i = 0; i < hexa_num.length(); i++)
        {
            char c = hexa_num.charAt(i);
            int d = digits.indexOf(c);
            decimal_num = 16 * decimal_num + d;
        }
      
        return decimal_num;
    }
    
    /**
     * To convert decimal number into hexadecimal.
     * @param decimal_number, decimal number to convert.
     * @return hexadecimal_num, number converted into hexadecimal, return as string.
     */
    String decimalToHexa(int decimal_number)
    {
        String hexadecimal_num="";
        char hex[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        int remainder,deci_num;
        
        deci_num = decimal_number;
        
         while(deci_num>0)
         {
             remainder = deci_num%16;
             hexadecimal_num = hex[remainder] + hexadecimal_num;
             deci_num = deci_num/16;
         }
         return hexadecimal_num;
    }
    
    /**To perform addition of 2 hexadecimal numbers by first converting them into decimal, performing 
       sum and then returning sum converted into hexadecimal number.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, second hexadecimal number as operand.
     */
    public String add(String first_number,String second_number)
    {
         int first_num,second_num;
             
         first_num = hexaToDecimal(first_number);
         second_num = hexaToDecimal(second_number);
         int sum = first_num + second_num;
         return decimalToHexa(sum);
    }
    
    /**To perform subtraction of 2 hexadecimal numbers by first converting them into decimal, finding 
       difference between them and then returning difference converted into hexadecimal number.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, second hexadecimal number as operand.
     */
    public String subtract(String first_number,String second_number)
    {
             int first_num,second_num;
             first_num = hexaToDecimal(first_number);
             second_num = hexaToDecimal(second_number);
            
             return decimalToHexa(first_num - second_num);
    }
    
    /**To perform multiplication of 2 hexadecimal numbers by first converting them into decimal, finding 
       their product and then returning product converted into hexadecimal number.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, second hexadecimal number as operand.
     */
    public String multiply(String first_number,String second_number)
    {
             int first_num,second_num;
             first_num = hexaToDecimal(first_number);
             second_num = hexaToDecimal(second_number);
             
             return decimalToHexa(first_num*second_num);
             
    }
    
    /**To perform division of 2 hexadecimal numbers by first converting them into decimal, dividing them 
       and then returning the result converted into hexadecimal number.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, second hexadecimal number as operand.
     */
    public String divide(String first_number,String second_number)
    {
             int first_num,second_num;
             first_num = hexaToDecimal(first_number);
             second_num = hexaToDecimal(second_number);
           
             return decimalToHexa(first_num / second_num);
             
    }
    
    /**
     * To compare to hexadecimal numbers for equality.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, first hexadecimal number as operand.
     * @return result, true if both the numbers are equal otherwise false.
     */
    public boolean compareEqualTo(String first_number,String second_number)
    {
            String num1,num2,num3,num4;
            boolean result = false;
            
            num1 = removeLeadingZeros(first_number);
            num2 = removeLeadingZeros(second_number);
            
            num3 = num1.toUpperCase();
            num4 = num2.toUpperCase();
            
            if(num3.equals(num4))
            {
                result = true;
            }
            return result;
    }
    
    /**
     * To compare if first hexadecimal number is greater than second.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, first hexadecimal number as operand.
     * @return result, true if first number as operand is greater than second number, otherwise false.
     */
    public boolean compareGreaterThan(String first_number,String second_number)
    {
            String num1,num2,num3,num4;
            boolean result = false;
            
            num1 = removeLeadingZeros(first_number);
            System.out.println(num1);
            num2 = removeLeadingZeros(second_number);
            System.out.println(num2);
            
            num3 = num1.toUpperCase();
            System.out.println(num3);
            num4 = num2.toUpperCase();
            System.out.println(num4);
        
            if(num3.length() > num4.length())
            {
                System.out.println("test1");
                result = true;
            }
            else
            {
                if(num3.length() == num4.length())
                {
                    System.out.println("test2");
                    int i = 0;
                    while(i <= num3.length()-1)
                    {
                        System.out.println("test3");
                        if(num3.charAt(i) > num4.charAt(i))
                        {
                            System.out.println("test4");
                            result = true;
                            break;
                        }
                        i++;
                    }
                
                }
            }
            return result;
    }
    
    /**
     * To compare if first hexadecimal number is smaller than second.
     * @param first_number, first hexadecimal number as operand.
     * @param second_number, first hexadecimal number as operand.
     * @return result, true if first number as operand is smaller than second number, otherwise false.
     */
    public boolean compareLessThan(String first_number,String second_number)
    {
        String num1,num2,num3,num4;
        boolean result = false;
        
        num1 = removeLeadingZeros(first_number);
        num2 = removeLeadingZeros(second_number);
        
        num3 = num1.toUpperCase();
        num4 = num2.toUpperCase();
    
        if(num3.length() < num4.length())
        {
            result = true;
        }
        else
        {
            if(num3.length() == num4.length())
            {
                int i=0;
                while(i <= num3.length()-1)
                {
                    if(num3.charAt(i) < num4.charAt(i))
                    {
                        result = true;
                        break;
                    }
                    i++;
                }
            }
        }
        return result;
    }
    
    /**
     * To remove leading zeros (if any) in hexadecimal number provided as input.
     * @param num, hexadecimal number.
     * @return new_substring, hexadecimal number without any leading zeros.
     */
    public String removeLeadingZeros(String num)
    {
            int position,i;
            i = 0;
    
            while(i < num.length())
            {
                if(num.charAt(i) == '0')
                    i++;
                else
                    break;
            }
            position = i;
            String new_substring = num.substring(position, num.length()-1);
            return new_substring;
    }
}
