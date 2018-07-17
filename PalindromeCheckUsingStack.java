import java.util.*;
class Stack
{
	static final int MAX=50;
	int top;
	char array[]=new char[MAX];
	Stack()
	{
		top=-1;
	}
	public void push(char data)
	{
		if(top==MAX-1)
			System.out.println("Stack Overflow!");
		else
		{	
			top++;
			array[top]=data;
		}
	}
	public char pop()
	{
		char popped_data;
		popped_data=array[top];
		top--;
		return popped_data;
	}
	public int top()
	{
		return array[top];
	}
	public boolean isEmpty()
	{
		return top==-1;
	}
}
class PalindromeCheckUsingStack
{
	public static void main(String args[])
	{
		String string_to_chk="";
		Stack stack1=new Stack();		
		System.out.println("Enter a string:");
			//Read string to check
		Scanner in=new Scanner(System.in);        
		string_to_chk=in.nextLine();
		
			//Push characters of string into stack
		for(int i=0;i<string_to_chk.length();i++)
			stack1.push(string_to_chk.charAt(i));  
		
		String rev="";
		while(!stack1.isEmpty())
			rev=rev+stack1.pop();
			//Check whether 2 strings are equal
		
		if(string_to_chk.equals(rev))
			System.out.println("Palindrome!");
		else
			System.out.println("Not a palindrome!");
		}
}











