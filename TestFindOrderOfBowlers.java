package bowler;

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
        List<Bowler> listOfBowlers = new ArrayList<Bowler>();
        FindOrderOfBallers findOrder = new FindOrderOfBallers();
        
        Bowler firstBowler = new Bowler("A", 7);
        Bowler secondBowler = new Bowler("B", 3);
        Bowler thirdBowler = new Bowler("C", 4);
        Bowler fourthBowler = new Bowler("D", 1);
        
        listOfBowlers.add(firstBowler);
        listOfBowlers.add(secondBowler);
        listOfBowlers.add(thirdBowler);
        listOfBowlers.add(fourthBowler);
        
        int numberOfBowlers = 4;
        int totalBalls = 15;
        
        List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls);
        
        expectedListOfOrder.add("A");   
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("A");     
        expectedListOfOrder.add("D");     
        expectedListOfOrder.add("C");     
        expectedListOfOrder.add("B");     
        expectedListOfOrder.add("A");     
        
        assertEquals(expectedListOfOrder, namesInOrderActualList);    
    }
    
	/**
     * To test the order of bowlers such that runs scored by opponent are minimum with number of 
     * bowlers less than or equal to zero
     */
    @Test
    public void testResultWithNumberOfBowlersLessThanOne()
    {
        List<Bowler> listOfBowlers = new ArrayList<Bowler>();
        FindOrderOfBallers findOrder = new FindOrderOfBallers();
        
        Bowler firstBowler = new Bowler("A", 7);
        Bowler secondBowler = new Bowler("B", 3);
        Bowler thirdBowler = new Bowler("C", 4);
        Bowler fourthBowler = new Bowler("D", 1);
        
        listOfBowlers.add(firstBowler);
        listOfBowlers.add(secondBowler);
        listOfBowlers.add(thirdBowler);
        listOfBowlers.add(fourthBowler);
        
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
        List<Bowler> listOfBowlers = new ArrayList<Bowler>();
        FindOrderOfBallers findOrder = new FindOrderOfBallers();
        
        Bowler firstBowler = new Bowler("A", 7);
        Bowler secondBowler = new Bowler("B", 3);
        Bowler thirdBowler = new Bowler("C", 4);
        Bowler fourthBowler = new Bowler("D", 1);
        
        listOfBowlers.add(firstBowler);
        listOfBowlers.add(secondBowler);
        listOfBowlers.add(thirdBowler);
        listOfBowlers.add(fourthBowler);
        
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
        List<Bowler> listOfBowlers = new ArrayList<Bowler>();
        FindOrderOfBallers findOrder = new FindOrderOfBallers();
        
        Bowler firstBowler = new Bowler("A", 7);
        Bowler secondBowler = new Bowler("B", 3);
        Bowler thirdBowler = new Bowler("C", 4);
        Bowler fourthBowler = new Bowler("D", 1);
        
        listOfBowlers.add(firstBowler);
        listOfBowlers.add(secondBowler);
        listOfBowlers.add(thirdBowler);
        listOfBowlers.add(fourthBowler);
        
        int numberOfBowlers = 4;
        int totalBalls = 2;
        
        List<String> namesInOrderActualList = findOrder.findOrderOfBowling(numberOfBowlers, totalBalls, listOfBowlers);
        List<String> expectedListOfOrder = new ArrayList<String>(totalBalls); 
        String ERROR_MESSAGE = "Invalid Input!";
        expectedListOfOrder.add(ERROR_MESSAGE);
        
        assertEquals(expectedListOfOrder.get(0), namesInOrderActualList.get(0));    
    }
}