package shapes;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class TestShape {
	
	Screen screen = new Screen();

	// To test addition of square shape on the screen
	@Test
	public void testToAddSquareOnScreen() {
		
		Point origin = new Point(5,5);                            // Set origin for square
		List<Double> list = new ArrayList<Double>();              // Create a new list of parameters
        double side = 5.0;                                        // Add side as parameter for shape square 
        list.add(side);
        
		Shape shape = ShapeFactory.createShape(0, origin, list);  // 0 for creation of square
		double area = shape.getArea();
		screen.shapesOnScreen.add(shape);
		assertEquals( 25.0, area, 0 );
	}
	
	// To test addition of rectangle shape on the screen
	@Test
	public void testToAddRectangleOnScreen() {
		
		Point origin = new Point(5,5); 								// Set origin for rectangle
		List<Double> list = new ArrayList<Double>();
		double length = 5.0;
        double width = 4.0;
        list.add(length);
        list.add(width);
        
		Shape shape = ShapeFactory.createShape(1, origin, list);    // 1 for creation of rectangle
		double area = shape.getArea();
		screen.shapesOnScreen.add(shape);
		assertEquals( 20.0, area, 0 );
	}
	
	// To test addition of circle shape on the screen
	@Test
	public void testToAddCircleOnScreen() {
		
		Point origin = new Point(5,5); // Set origin for rectangle
		List<Double> list = new ArrayList<Double>();
		double radius = 14.0;
		
        list.add(radius);   
		Shape shape = ShapeFactory.createShape(3, origin, list); // 1 for creation of circle
		double area = shape.getArea();
		assertEquals( 615.44, area, 0 );	
	}
	
	// To test creation of a shape with invalid shape type that is shape that is not allowed on the screen
	@Test (expected = AssertionError.class)
	public void testToAddNotAllowedShapeOnScreen() {
		
		Point origin = new Point(5,5); // Set origin for rectangle
		List<Double> list = new ArrayList<Double>();
		double length = 5.0;
        double width = 4.0;
       
        list.add(length);
        list.add(width);		
		assertEquals( true, ShapeFactory.createShape(5, origin, list) );
	}
	
	// Test to delete a shape from screen
	@Test
	public void testDeleteShapeFromScreen ()
	{
	    Screen screen1 = new Screen();
	    
	    int shapeNumber = 0;
	    double radius = 4.0;                                      // Set radius of the shape to be deleted
	    Point originOfShape = new Point(5,5);                     // Set origin of shape to be deleted                                 
	    List<Double> listOfParameters = new ArrayList<Double>();  // Pass parameters for square
	    listOfParameters.add(radius);                             // Pass parameters for square
	    
	    Shape shape = ShapeFactory.createShape(shapeNumber, originOfShape, listOfParameters);
	    screen1.shapesOnScreen.add(shape);
	    assertEquals(true, screen1.deleteShapeFromScreen(shape));        
     }   
	
	// Test to delete all shapes of a particular kind from the screen
    @Test
    public void testDeleteshapesOfParticularType()
    {
       
        Screen screen1 = new Screen();
        
        int shapeNumber = 0;
        double side = 4.0;                                         // Set side of the square 
        Point originOfShape = new Point(5,5);                      // Set origin of square                            
        List<Double> listOfParameters = new ArrayList<Double>();   // Pass parameters for square
        listOfParameters.add(side);                               
        Shape shape = ShapeFactory.createShape(shapeNumber, originOfShape, listOfParameters);
        screen1.shapesOnScreen.add(shape);
        
        int shapeNumberOne = 1;
        double length = 4.0;                                       // Set length of rectangle
        double breadth = 2.0;									   // Set width of rectangle
        Point originOfShapeOne = new Point(10,10);                 // Set origin                                 
        List<Double> listOfParametersOne = new ArrayList<Double>();// Pass parameters for rectangle   
        listOfParametersOne.add(length);                           
        listOfParametersOne.add(breadth);   					      
        Shape shapeTwo = ShapeFactory.createShape(shapeNumberOne, originOfShapeOne, listOfParametersOne);
        screen1.shapesOnScreen.add(shapeTwo);
        
        screen1.deleteAllOfParticularType(ShapeType.SQUARE);
        assertEquals(1,  screen1.shapesOnScreen.size());      
     }      
}