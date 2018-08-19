package numberofbowlers;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TestFindOrderOfBowlers
{
    /**
     * To test the order of bowlers such that runs scored by opponent are minimum
     */
    @Test
    public void testResult()
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
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls);
        
        expectedListOfOrder.add("A");   
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("C");     
        
        assertEquals(expectedListOfOrder, namesInOrderActualList);    
    }
    
    /**
     * To test the order of bowlers such that runs scored by opponent are minimum with number of 
     * bowlers less than or equal to zero
     */
    @Test
    public void testResultWithNumberOfBowlersLessThanOne()
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
        
        int numberOfBowlers = 0;
        int totalBalls = 15;
        
        List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls); 
        String ERROR_MESSAGE = "Invalid Input!";
        expectedListOfOrder.add(ERROR_MESSAGE);
        
        assertEquals(expectedListOfOrder.get(0), namesInOrderActualList.get(0));    
    }
    
    /**
     * To test the order of bowlers such that runs scored by opponent are minimum with number of 
     * balls less than or equal to zero
     */
    @Test
    public void testResultWithNumberOfBallsLessThanOne()
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
        int totalBalls = 0;
        
        List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls); 
        String ERROR_MESSAGE = "Invalid Input!";
        expectedListOfOrder.add(ERROR_MESSAGE);
        
        assertEquals(expectedListOfOrder.get(0), namesInOrderActualList.get(0));    
    }
    
    /**
     * To test the order of bowlers such that runs scored by opponent are minimum with number of 
     * balls less than the number of bowlers
     */
    @Test
    public void testResultWithNumberOfBallsLessThanNumberOfBowlers()
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
        int totalBalls = 2;
        
        List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls); 
        String ERROR_MESSAGE = "Invalid Input!";
        expectedListOfOrder.add(ERROR_MESSAGE);
        
        assertEquals(expectedListOfOrder.get(0), namesInOrderActualList.get(0));    
    }
}
