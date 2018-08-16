package shapes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestRectangle {
	
	Point origin = new Point(15,10); 							 	// Set origin for rectangle
	List<Double> list = new ArrayList<Double>();			 		// Create a new list of parameters
    double length = 5.0; 										 	// Add length as parameter for shape rectangle 
    double breadth = 4.0; 											// Add breadth as parameter for shape rectangle 
	
    /**
	 * Test to find area of rectangle
	 */
    @Test
    public void testFindAreaOfRectangle()
    {
		 list.add(length);											 // Add parameters to list
		 list.add(breadth);
		 Shape shape = ShapeFactory.createShape(1, origin, list); 	 // 1 for creation of rectangle using factory
		 double area = shape.getArea();								 // Find area of rectangle
		 assertEquals( 20.0, area, 0 );
    }
    
    /**
     * Test to find perimeter of rectangle
     */
    @Test
    public void testFindPerimeterOfRectangle()
    {
    	 list.add(length);											 // Add parameters to list
    	 list.add(breadth);
    	 Shape shape = ShapeFactory.createShape(1, origin, list); 	 // 1 for creation of rectangle using factory
		 double perimeter = shape.getPerimeter();					 // Find perimeter of rectangle
		 assertEquals( 18.0, perimeter, 0 );
    }
    
    /**
     * Test to find origin of rectangle
     */
    @Test
    public void testFindOriginOfRectangle()
    {
    	 list.add(length);											 // Add parameters to list
   	 	 list.add(breadth);
		 Shape shape = ShapeFactory.createShape(1, origin, list); 	 // 1 for creation of rectangle using factory
		 Point actualOrigin = shape.getOrigin();					 // Find area of rectangle	
		 assertEquals( 15, actualOrigin.xCoordinate, 0 );
		 assertEquals( 10, actualOrigin.yCoordinate, 0 );
    }
    
    /**
     * Test to check isPointEnclosed when point lies within the rectangle
     */
    @Test
    public void testIsPointEnclosedWithinRectangle()
    {
        Point pointToCheck = new Point(16, 14);
        list.add(length);											 // Add parameters to list
  	 	list.add(breadth);
		Shape shape = ShapeFactory.createShape(1, origin, list); 	// 1 for creation of rectangle using factory 
        assertEquals(true, shape.isPointEnclosed(pointToCheck));
    }
    
    /**
     * Negative test to check isPointEnclosed when point lies outside the square
     */
    @Test
    public void testIsPointEnclosedWithinSquareWithPointOutside()
    {
        Point pointOutsideSquare = new Point(100, 210);
        list.add(length);											 // Add parameters to list
  	 	list.add(breadth);
		Shape shape = ShapeFactory.createShape(1, origin, list); 	// 1 for creation of rectangle using factory       
        assertEquals(false, shape.isPointEnclosed(pointOutsideSquare));
    }
    
    /**
     * Test to find type of given shape
     */
    @Test
    public void testGetShapeType()
    {
    	list.add(length);											 // Add parameters to list
  	 	list.add(breadth);
		Shape shape = ShapeFactory.createShape(1, origin, list); 	// 1 for creation of rectangle using factory       
        assertEquals(ShapeType.RECTANGLE, shape.getShapeType());
    }
}