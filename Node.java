package numberofbowlers;

public class Node
{
	String data;
	public int priority;
	
	//Constructor for node of a priority queue
	public Node(String data, int priority)
	{
		this.data = data;
		this.priority = priority;
	}
	/**
	 * To set data for element of priority queue
	 * @param data, string to specify name of job or element to be inserted in the priority queue
	 */
	public void setData(String data)
	{
		this.data = data;
	}
	
	/**
	 * To set priority for element of priority queue
	 * @param priority, an integer number to specify priority of job or element to be inserted in the 
	 * priority queue.
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	/**
	 * To get data or name of element in priority queue.
	 * @return data, data of element.
	 */
	public String getData()
	{
		return data;
	}
	
	/**
	 * To get priority of element in priority queue.
	 * @return priority, priority of element.
	 */
	public int getPriority()
	{
		return priority;
	}
	
	// To decrement number of balls in a bowlers quota
	public void decrementNumberOfBalls()
	{
            if(priority != 0)
	        priority--;
	}
}
