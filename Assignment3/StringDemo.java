package assignment3 ;

import java.util.* ;
class StringDemo
{	
    public static void main(String args[])
    {
	try
	{
            StringOperation new_obj=new StringOperation();
	    Scanner in=new Scanner(System.in);
	    int choice;

	    System.out.println("Choose an operation to perform:");
	    do
	    {
	        System.out.println("1.Find length of string");
	        System.out.println("2.Find reverse of a string");
	        System.out.println("3.Change case of letters in a string");
	        System.out.println("4.Compare 2 strings.");
	        System.out.println("5.Get last largest word of a string");
	        choice=in.nextInt();
	        switch(choice)
	        {
				case 1: System.out.println("Enter a string:");
				 	String s = in.next();
					s += in.nextLine();
					System.out.println("Length of first string:" + new_obj.getLength(s));
					 break;

				case 2: System.out.println("Enter a string:");
					s = in.next();
					s += in.nextLine();
					System.out.println("Reverse of first string:" + new_obj.reverseString(s));
					break;

				case 3: System.out.println("Enter a string:");
					s = in.next();
					s += in.nextLine();
					System.out.println("String with changed case of letters:" + new_obj.replaceCase(s));
					break;

				case 4: System.out.println("Enter first string:");
					s = in.next();
					s += in.nextLine();
					System.out.println("Enter second string:");
					String str = in.next();
					str += in.nextLine();
					System.out.println("Result of comparision:" + new_obj.compareStrings(s,str));
					break;

				case 5: System.out.println("Enter a string:");
					s = in.next();
					s += in.nextLine();
					System.out.println("Last largest word:" + new_obj.largestWord(s));
					break;

			        default: System.out.println("Wrong choice!Try again");	
			}
			System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
			choice=in.nextInt();

		}while(choice!=0);
	}
	catch(ArithmeticException e)
	{
		System.out.println("Error:" + e.getMessage());
	}
    }
}
