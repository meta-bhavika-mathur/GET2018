package shapes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestCircle {

	Point origin = new Point(25,100); 							 	// Set origin for circle
	List<Double> list = new ArrayList<Double>();			 		// Create a new list of parameters
    double radius = 14.0; 										 	// Add radius as parameter for shape circle 
	
	/**
	 * Test to find area of circle
	 */
    @Test
    public void testFindAreaOfCircle()
    {
		 list.add(radius);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(3, origin, list); 	 // 3 for creation of circle using factory
		 double area = shape.getArea();								 // Find area of circle
	
		 assertEquals( 615.44, area, 0 );
    }
    
    /**
     * Test to find circumference of circle
     */
    @Test
    public void testFindCircumferenceOfCircle()
    {
    	 list.add(radius);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(3, origin, list); 	 // 3 for creation of circle using factory
		 double perimeter = shape.getPerimeter();					 // Find circumference of circle
	
		 assertEquals( 87.92, perimeter, 0 );
    }
    
    /**
     * Test to find origin of circle
     */
    @Test
    public void testFindOriginOfCircle()
    {
    	 list.add(radius);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(3, origin, list); 	 // 3 for creation of circle using factory
		 Point actualOrigin = shape.getOrigin();					 // Get origin of circle
	
		 assertEquals( 25, actualOrigin.xCoordinate, 0 );
		 assertEquals( 100, actualOrigin.yCoordinate, 0 );
    }
    
    /**
     * Test to check isPointEnclosed when point lies within the circle
     */
    @Test
    public void testIsPointEnclosedWithinCircle()
    {
        Point pointToCheck = new Point(26, 101);
        list.add(radius);											 // Add parameters to list
		Shape shape = ShapeFactory.createShape(3, origin, list); 	// 3 for creation of square using factory
       
        assertEquals(true, shape.isPointEnclosed(pointToCheck));
    }
    
    /**
     * Negative test to check isPointEnclosed when point lies outside the circle
     */
    @Test
    public void testIsPointEnclosedWithinCircleWithPointOutside()
    {
        Point pointOutsideSquare = new Point(93, 108);
        list.add(radius);											 // Add parameters to list
		Shape shape = ShapeFactory.createShape(3, origin, list); 	// 3 for creation of square using factory
        
		assertEquals(false, shape.isPointEnclosed(pointOutsideSquare));
    }
    
    /**
     * Test to find type of given shape
     */
    @Test
    public void testGetShapeType()
    {
    	list.add(radius);											 // Add parameters to list
		Shape shape = ShapeFactory.createShape(3, origin, list); 	// 3 for creation of square using factory
       
        assertEquals(ShapeType.CIRCLE, shape.getShapeType());
    }
}