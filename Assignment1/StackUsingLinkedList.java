package bhavika;

import java.util.*;
class ListNode
{
	protected int data;
	protected ListNode next;
	public ListNode()
	{
		data=0;		
		next=null;
	}
	public ListNode(int value,ListNode node)
	{
		data=value;
		next=node;
	}
	public void setData(int value)
	{
		data=value;
	}
	public void setNextNode(ListNode node)
	{
		next=node;
	}
	public int getData()
	{
		return data;
	}
	public ListNode getNextNode()
	{
		return next;
	}
}
class LinkedStack
{
	protected ListNode top;
	public LinkedStack()
	{
		top=null;
	} 
	public void push(int data)
	{
		ListNode new_node=new ListNode(data,null);
		if(top==null)
			top=new_node;
		else
		{
			new_node.setNextNode(top);
			top=new_node;
		}
	}
	public int pop()
	{
		if(isEmpty())
			throw new NoSuchElementException("Stack Underflow!");
		else
		{
			int x; 
			ListNode nd=top;
			x=nd.getData();
			top=nd.getNextNode();
			return x;
		}
	}
	public int top()
	{
		ListNode n=top;
		return n.getData();
	}
	public boolean isEmpty()
	{
		return top==null;
	}
}
class StackUsingLinkedList
{
	public static void main(String args[])
	{
		Scanner in=new Scanner(System.in);
		LinkedStack stack1=new LinkedStack();
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
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
				choice=in.nextInt();
		
		}while(choice!=0);
	}
}









