package shapes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestScreen {

	Screen screen = new Screen();
	
	/**
	 * Test to add a shape on screen
	 */
	@Test
	public void testAddNewShapeToScreen() {

		// Create a shape square
		Point origin = new Point(5,5);                            	  // Set origin for square
		List<Double> list = new ArrayList<Double>();              	  // Create a new list of parameters
        double side = 5.0;                                        	  // Add side as parameter for shape square 
        list.add(side);   										 	  // Add to list
		Shape shape = ShapeFactory.createShape(0, origin, list); 	  // 0 for creation of square
		
		screen.shapesOnScreen.add(shape);						  	  // Add shape to screen
		assertEquals( 1, screen.shapesOnScreen.size());			  	  // Check if size of list is 1 after addition of shape
	}
	
	/**
	 * Test to a delete shape from the screen
	 */
	@Test
	public void testDeleteShapeFromScreen() {
		
		// Create a first shape square
		Point origin = new Point(5,5);                           		// Set origin for square
		List<Double> list = new ArrayList<Double>();              		// Create a new list of parameters
        double side = 5.0;                                        		// Add side as parameter for shape square 
        list.add(side);   										  		// Add to list
		Shape shape = ShapeFactory.createShape(0, origin, list);  		// 0 for creation of square
		screen.shapesOnScreen.add(shape);						  		// Add shape to screen
		
		// Create a second shape square
		Point secondOrigin = new Point(5,5);                             // Set origin for square
		List<Double> secondList = new ArrayList<Double>();               // Create a new list of parameters
        double secondSide = 5.0;                                         // Add side as parameter for shape square 
        secondList.add(secondSide);   									 // Add to list
		Shape secondShape = ShapeFactory.createShape(0, secondOrigin, secondList);  // 0 for creation of square
		screen.shapesOnScreen.add(secondShape);						  	 // Add shape to screen
		
		// Delete shape from screen
		assertEquals( true, screen.deleteShapeFromScreen( secondShape ));		
	}
	
	/**
	 *  Test to delete all shapes of a specified type from the screen
	 */
	@Test
	public void testDeleteAllOfParticularType() {
		
		// Create a first shape square
		Point origin = new Point(5,5);                           		// Set origin for square
		List<Double> list = new ArrayList<Double>();              		// Create a new list of parameters
        double side = 5.0;                                        		// Add side as parameter for shape square 
        list.add(side);   										  		// Add to list
		Shape square = ShapeFactory.createShape(0, origin, list);  		// 0 for creation of square
		screen.shapesOnScreen.add(square);						  		// Add shape to screen
		
		// Create a second shape square
		Point secondOrigin = new Point(15,5);                            // Set origin for square
		List<Double> secondList = new ArrayList<Double>();               // Create a new list of parameters
        double secondSide = 5.0;                                         // Add side as parameter for shape square 
        secondList.add(secondSide);   								     // Add to list
		Shape secondSquare = ShapeFactory.createShape(0, secondOrigin, secondList); // 0 for creation of square
		screen.shapesOnScreen.add(secondSquare);						 // Add shape to screen
		
		// Create a third shape circle
		Point thirdOrigin = new Point(30,40);                             // Set origin for circle
		List<Double> thirdList = new ArrayList<Double>();                 // Create a new list of parameters
        double radius = 14.0;                                             // Add radius as parameter for shape circle 
        thirdList.add(radius);   										  // Add to list
		Shape circle = ShapeFactory.createShape(3, thirdOrigin, thirdList);  // 3 for creation of circle
		screen.shapesOnScreen.add(circle);						  	      // Add shape to screen
		
		// Check to test that shapes are added
		assertEquals(3, screen.shapesOnScreen.size());
		
		// Delete squares from screen
		screen.deleteAllOfParticularType( ShapeType.SQUARE );
		
		// Size of list is reduced after deletion
		assertEquals(1, screen.shapesOnScreen.size());
	}
	
	/**
	 * Test to get a list of all the shapes enclosing a given point
	 */
	@Test
	public void testGetListOfShapesEnclosingGivenPoint() {
		
		// Create a first shape square
		Point origin = new Point(25, 100);                              
		List<Double> list = new ArrayList<Double>();              		
        double side = 5.0;                                        		
        list.add(side);   										  		
		Shape square = ShapeFactory.createShape(0, origin, list);  		    // 0 for creation of square
		screen.shapesOnScreen.add(square);						  		
		
		// Create a second shape square
		Point secondOrigin = new Point(500,500);                            
		List<Double> secondList = new ArrayList<Double>();               
        double secondSide = 5.0;                                         
        secondList.add(secondSide);   								    
		Shape secondSquare = ShapeFactory.createShape(0, secondOrigin, secondList); 
		screen.shapesOnScreen.add(secondSquare);					
		
		// Create a third shape circle
		Point thirdOrigin = new Point(25,100);                             
		List<Double> thirdList = new ArrayList<Double>();                
        double radius = 14.0;                                            
        thirdList.add(radius);   										  
		Shape circle = ShapeFactory.createShape(3, thirdOrigin, thirdList);  // 3 for creation of circle
		screen.shapesOnScreen.add(circle);		
		
		// Point to check for shapes enclosing it
		Point point =  new Point(26, 101);
		List<Shape> shapesEnclosingPoint = screen.getListOfShapesEnclosingGivenPoint(point);
		
		// Expected shapes in the list
		List<Shape> expectedList = new ArrayList<Shape>();
		expectedList.add(square);
		expectedList.add(circle);
		
		assertEquals( expectedList, shapesEnclosingPoint );	
	}
	
	/**
	 * To test sorting of shapes on screen on the basis of area of shapes
	 */
	@Test
    public void testSortShapeWithArea()
    {
      //  Screen screen = new Screen();
        
        Point originOfSquare = new Point(1,1);
        List<Double> listOne = new ArrayList<Double>();
        listOne.add(2.0);
        Shape square = ShapeFactory.createShape(0, originOfSquare, listOne); 
        
        Point originOfCircle = new Point(1,1);
        List<Double> listTwo = new ArrayList<Double>();
        listTwo.add(2.0);
        Shape circle = ShapeFactory.createShape(3, originOfCircle, listTwo);
        
        Point orignOfTriangle = new Point(1,1);
        List<Double> listThree = new ArrayList<Double>();
        listThree.add(4.0);
        listThree.add(3.0);
        listThree.add(4.0);
        listThree.add(5.0);
        Shape triangle = ShapeFactory.createShape(2, orignOfTriangle, listThree);
        
        screen.addNewShapeToScreen(square);
        screen.addNewShapeToScreen(circle);
        screen.addNewShapeToScreen(triangle);
        
        screen.sortShapesOnCriteria(SortCriteria.AREA); // Sort list on the basis of area
        
        assertEquals(4.0, screen.shapesOnScreen.get(0).getArea(), 0);
        assertEquals(6.0, screen.shapesOnScreen.get(1).getArea(), 0);
        assertEquals(12.56, screen.shapesOnScreen.get(2).getArea(), 0);
       
    }
    
    @Test
    public void testSortShapeWithPerimeter()
    {
    	Point originOfSquare = new Point(1,1);
        List<Double> listOne = new ArrayList<Double>();
        listOne.add(2.0);
        Shape square = ShapeFactory.createShape(0, originOfSquare, listOne); 
        
        Point originOfCircle = new Point(1,1);
        List<Double> listTwo = new ArrayList<Double>();
        listTwo.add(2.0);
        Shape circle = ShapeFactory.createShape(3, originOfCircle, listTwo);
        
        Point orignOfTriangle = new Point(1,1);
        List<Double> listThree = new ArrayList<Double>();
        listThree.add(4.0);
        listThree.add(3.0);
        listThree.add(4.0);
        listThree.add(5.0);
        Shape triangle = ShapeFactory.createShape(2, orignOfTriangle, listThree);
        
        screen.addNewShapeToScreen(square);
        screen.addNewShapeToScreen(circle);
        screen.addNewShapeToScreen(triangle);
        
        screen.sortShapesOnCriteria(SortCriteria.PERIMETER); // Sort list on the basis of perimeter
        
        assertEquals(8.0, screen.shapesOnScreen.get(0).getPerimeter(), 0);
        assertEquals(12.0, screen.shapesOnScreen.get(1).getPerimeter(), 0);
        assertEquals(12.56, screen.shapesOnScreen.get(2).getPerimeter(), 0);
    }
    
    @Test
    public void testSortShapeWithTimestamp()
    {
    	Point originOfSquare = new Point(1,1);
        List<Double> listOne = new ArrayList<Double>();
        listOne.add(2.0);
        Shape square = ShapeFactory.createShape(0, originOfSquare, listOne); 
        
        Point originOfCircle = new Point(1,1);
        List<Double> listTwo = new ArrayList<Double>();
        listTwo.add(2.0);
        Shape circle = ShapeFactory.createShape(3, originOfCircle, listTwo);
        
        Point orignOfTriangle = new Point(1,1);
        List<Double> listThree = new ArrayList<Double>();
        listThree.add(4.0);
        listThree.add(3.0);
        listThree.add(4.0);
        listThree.add(5.0);
        Shape triangle = ShapeFactory.createShape(2, orignOfTriangle, listThree);
        
        screen.addNewShapeToScreen(square);
        screen.addNewShapeToScreen(circle);
        screen.addNewShapeToScreen(triangle);
        
        screen.sortShapesOnCriteria(SortCriteria.TIMESTAMP);
    }
    
    @Test
    public void testSortShapeWithOriginDistance()
    {
    	Point originOfSquare = new Point(2,1);
        List<Double> listOne = new ArrayList<Double>();
        listOne.add(2.0);
        Shape square = ShapeFactory.createShape(0, originOfSquare, listOne); 
        
        Point originOfCircle = new Point(3,1);
        List<Double> listTwo = new ArrayList<Double>();
        listTwo.add(2.0);
        Shape circle = ShapeFactory.createShape(3, originOfCircle, listTwo);
        
        Point orignOfTriangle = new Point(1,2);
        List<Double> listThree = new ArrayList<Double>();
        listThree.add(4.0);
        listThree.add(3.0);
        listThree.add(4.0);
        listThree.add(5.0);
        Shape triangle = ShapeFactory.createShape(2, orignOfTriangle, listThree);
        
        screen.addNewShapeToScreen(square);
        screen.addNewShapeToScreen(circle);
        screen.addNewShapeToScreen(triangle);
        
        screen.sortShapesOnCriteria(SortCriteria.ORIGIN_DISTANCE);
        
        assertEquals(2.0, screen.shapesOnScreen.get(0).getOrigin().xCoordinate, 0);
        assertEquals(1.0, screen.shapesOnScreen.get(0).getOrigin().yCoordinate, 0);
        assertEquals(1.0, screen.shapesOnScreen.get(1).getOrigin().xCoordinate, 0);
        assertEquals(2.0, screen.shapesOnScreen.get(1).getOrigin().yCoordinate, 0);
        assertEquals(3.0, screen.shapesOnScreen.get(2).getOrigin().xCoordinate, 0);
        assertEquals(1.0, screen.shapesOnScreen.get(2).getOrigin().yCoordinate, 0);
    
    }
}