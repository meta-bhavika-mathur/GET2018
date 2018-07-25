package assignment2;

import java.util.*;

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

class HexCalc1 implements Numbers<String>
{
	int hexaToDecimal(String hexa_num)
	{
		int decimal_num=0;
		String digits = "0123456789ABCDEF";
        hexa_num = hexa_num.toUpperCase();
       
        for (int i = 0; i < hexa_num.length(); i++)
        {
            char c = hexa_num.charAt(i);
            int d = digits.indexOf(c);
            decimal_num = 16*decimal_num + d;
        }
      
		return decimal_num;
	}
	String decimalToHexa(int decimal_number)
	{
		String hexadecimal_num="";
		char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		int remainder,deci_num;
		
		deci_num=decimal_number;
		
		 while(deci_num>0)
	     {
	            remainder = deci_num%16;
	            hexadecimal_num = hex[remainder] + hexadecimal_num;
	            deci_num = deci_num/16;
	     }
		 return hexadecimal_num;
	}
	public String add(String first_number,String second_number)
	{
		     int first_num,second_num;
		     first_num=hexaToDecimal(first_number);
		     second_num=hexaToDecimal(second_number);
		     int sum=first_num+second_num;
		     return decimalToHexa(sum);
		     
	}
	public String subtract(String first_number,String second_number)
	{
		     int first_num,second_num;
		     first_num = hexaToDecimal(first_number);
		     second_num = hexaToDecimal(second_number);
		     int subtract = first_num-second_num;
		     return decimalToHexa(subtract);
		     
	}
	public String multiply(String first_number,String second_number)
	{
		     int first_num,second_num;
		     first_num = hexaToDecimal(first_number);
		     second_num = hexaToDecimal(second_number);
		     int product = first_num*second_num;
		     return decimalToHexa(product);
		     
	}
	public String divide(String first_number,String second_number)
	{
		     int first_num,second_num;
		     first_num = hexaToDecimal(first_number);
		     second_num = hexaToDecimal(second_number);
		     int division = first_num/second_num;
		     return decimalToHexa(division);
		     
	}
	public boolean compareEqualTo(String first_number,String second_number)
	{
			String num1,num2,num3,num4;
			boolean result = false;
			
			num1 = removeLeadingZeros(first_number);
			num2 = removeLeadingZeros(second_number);
			
			num3=num1.toUpperCase();
			num4=num2.toUpperCase();
			
			if(num3.equals(num4))
			{
				result=true;
			}
			return result;
			
	}
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
					int i=0;
					while(i <= num3.length()-1)
					{
						System.out.println("test3");
						if(num3.charAt(i)>num4.charAt(i))
						{
							System.out.println("test4");
							result=true;
							break;
						}
						i++;
					}
				
				}
			}
			return result;
	}
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
					if(num3.charAt(i)<num4.charAt(i))
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
	public String removeLeadingZeros(String num)
	{
			int position,i;
			i=0;
	
			while(i<num.length())
			{
				if(num.charAt(i)=='0')
					i++;
				else
					break;
			}
			position = i;
			String new_substring = num.substring(position, num.length()-1);
			return new_substring;
	}
}
					
class HexCalc
{
	public static void main(String args[])
	{
		HexCalc1 hexa_obj = new HexCalc1();
		Scanner in = new Scanner(System.in);
		int choice;
		System.out.println("Choose an operation to perform:");
		do
		{
				System.out.println("1.Decimal to Hexadecimal conversion.");
				System.out.println("2.Hexadecimal to Decimal conversion.");
				System.out.println("3.Add 2 hexadecimal numbers");
				System.out.println("4.Subtract 2 hexadecimal numbers");
				System.out.println("5.Multiply 2 hexadecimal numbers");
				System.out.println("6.Divide 2 hexadecimal numbers");
				System.out.println("7.Compare equality for 2 hexadecimal numbers");
				System.out.println("8.Compare \"is greater than\" for 2 hexadecimal numbers");
				System.out.println("9.Compare \"is less than\" for 2 hexadecimal numbers");
				
				choice=in.nextInt();
				switch(choice)
				{
					case 1: System.out.println("Enter a decimal number:");
						    int decimal_num=in.nextInt();
							System.out.println("Decimal to Hexadecimal of "+ decimal_num + " is:" + hexa_obj.decimalToHexa(decimal_num));
						   	break;
						   	
					case 2:System.out.println("Enter a hexadecimal number:");
							String hex=in.next();
							System.out.println("Hexadecimal to decimal of "+ hex + " is:" + hexa_obj.hexaToDecimal(hex));
							break;
							
					case 3: System.out.println("Enter first number:");
							String s1=in.next();
							System.out.println("Enter second number:");
							String s2=in.next();
							System.out.println("Result of addition:"+ hexa_obj.add(s1,s2));
							break;
							
					case 4:System.out.println("Enter first number:");
							String s3=in.next();
							System.out.println("Enter second number:");
							String s4=in.next();
							System.out.println("Result of subtraction:"+ hexa_obj.subtract(s3,s4));
							break;
							
					case 5:System.out.println("Enter first number:");
							String s5=in.next();
							System.out.println("Enter second number:");
							String s6=in.next();
							System.out.println("Result of multiplication:"+ hexa_obj.multiply(s5,s6));
							break;
							
					case 6:System.out.println("Enter first number:");
					 		String s7=in.next();
							System.out.println("Enter second number:");
							String s8=in.next();
							System.out.println("Result of division:"+ hexa_obj.divide(s7,s8));
							break;
							
					case 7:System.out.println("Enter first number:");
						   String s9=in.next();
						   System.out.println("Enter second number:");
						   String s10=in.next();
						   System.out.println("Result of division:"+ hexa_obj.compareEqualTo(s9,s10));
						   break;
						   
					case 8:System.out.println("Enter first number:");
					   		String s11=in.next();
					   		System.out.println("Enter second number:");
					   		String s12=in.next();
					   		System.out.println("Result of comparision:"+ hexa_obj.compareGreaterThan(s11,s12));
					   		break;
							
					case 9:System.out.println("Enter first number:");
			   			   String s13=in.next();
			   			   System.out.println("Enter second number:");
			   			   String s14=in.next();
			   			   System.out.println("Result of comparision:"+ hexa_obj.compareLessThan(s13,s14));
			   			   break;
			
					default:System.out.println("Wrong choice!Try again");		
				}
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
				choice=in.nextInt();
		
		}while(choice!=0);
	}
}