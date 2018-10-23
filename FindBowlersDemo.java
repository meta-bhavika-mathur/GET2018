package numberofbowlers;

import java.util.List;

public class FindBowlersDemo 
{
	public static void main(String arg[])
	{			
		Node[] listOfBowlers = new Node[4];
		FindOrderOfBowlers findOrder = new FindOrderOfBowlers();

		Node firstBowler = new Node("A", 7);
		Node secondBowler = new Node("B", 3);
		Node thirdBowler = new Node("C", 4);
		Node fourthBowler = new Node("D", 1);

		listOfBowlers[0] = firstBowler;
		listOfBowlers[1] = secondBowler;
		listOfBowlers[2] = thirdBowler;
		listOfBowlers[3] = fourthBowler;

		int numberOfBowlers = 4;
		int totalBalls = 15;

		List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);        
		System.out.println("The order of bowlers is:" + namesInOrderActualList);        
	}
}
