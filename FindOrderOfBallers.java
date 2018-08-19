package numberofbowlers;

import java.util.*;

// Class to find the order or sequence of bowlers so that runs scored by the opponent cricketer are minimized
public class FindOrderOfBowlers
{
    List<String> nameOfBallers;
    int maximumQuota, quota;
    String name;
    Node maxBowler; 
    PriorityQueue bowlerList;
    
    // Constructor for FindOrderOfBalls
    public FindOrderOfBowlers()
    {    
        nameOfBallers = new ArrayList<String>();
        maxBowler = null;
        bowlerList = new PriorityQueue(4);
    }
    
    /**
     * To find the order of bowlers such that the runs scored by the opponent are minimized
     * @param numberOfBowlers, total number of bowlers
     * @param totalBalls, total number of balls
     * @param listOfBallers, list of bowlers 
     * @return nameOfBallers, list of names of bowlers in order in which they will bowl
     */
    public List<String> findOrderOfBowling(int numberOfBowlers, int totalBalls, Node[] listOfBallers)
    {          
         if((numberOfBowlers <= 0 || totalBalls <= 0) || (numberOfBowlers > totalBalls))
         {
        	 String string = "Invalid Input!";
        	 nameOfBallers.add(string);
        	 return nameOfBallers;
         }
              
         bowlerList.insertElement(listOfBallers[0].getData(), listOfBallers[0].getPriority());
         bowlerList.insertElement(listOfBallers[1].getData(), listOfBallers[1].getPriority());
 		 bowlerList.insertElement(listOfBallers[2].getData(), listOfBallers[2].getPriority());
 		 bowlerList.insertElement(listOfBallers[3].getData(), listOfBallers[3].getPriority());
 	       
         bowlerList.printHeap();

         for(int i = 0 ; i < totalBalls; i++ )
         {
        	 maxBowler = bowlerList.getElementWithMaximumPriority();
        	 nameOfBallers.add(maxBowler.getData());
        	 maxBowler.decrementNumberOfBalls();
        	 bowlerList.maxHeapify(1);
         }
        return nameOfBallers; 
     }     
}
