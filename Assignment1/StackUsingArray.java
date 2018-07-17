package bhavika;

import java.util.*;

class Stack
{
	static final int MAX=50;
	int top;
	int array[]=new int[MAX];
	Stack()
	{
		top=-1;
	}
	public void push(int data)
	{
		if(top==MAX-1)
			System.out.println("Stack Overflow!");
		else
		{	
			top++;
			array[top]=data;
		}
	}
	public int pop()
	{
		if(isEmpty())
			throw new NoSuchElementException("Stack Underflow!");
		else
		{
			int popped_data;
			popped_data=array[top];
			top--;
			return popped_data;
		}
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
class StackUsingArray
{
	public static void main(String args[])
	{
		Stack stack1=new Stack();
		Scanner in=new Scanner(System.in);
		int choice;
		
		System.out.println("Choose an operation to perform:");
		do
		{
				System.out.println("1.Push");
				System.out.println("2.Pop");
				System.out.println("3.IsEmpty");
				System.out.println("4.Top");
				choice=in.nextInt();
				switch(choice)
				{
					case 1:System.out.println("Enter element to push:");
						   stack1.push(in.nextInt());
						   break;
					case 2:try{
							System.out.println("Popped element is:"+stack1.pop());
							
						}catch(Exception e)
						{
							System.out.println("Error:"+e.getMessage());
						}
						break;
					case 3:System.out.println("Stack Empty:"+stack1.isEmpty());
							break;
					case 4:System.out.println("Top element is:"+stack1.top());
							break;
			
					default:System.out.println("Wrong choice!Try again");
							break;
					
				}
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for No");
				choice=in.nextInt();
		
		}while(choice!=0);
	}
}










