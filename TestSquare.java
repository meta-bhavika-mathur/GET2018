package shapes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestSquare {
	
	Point origin = new Point(5,10); 							 	// Set origin for square
	List<Double> list = new ArrayList<Double>();			 		// Create a new list of parameters
    double side = 5.0; 										 		// Add side as parameter for shape square 
	
	/**
	 * Test to find area of square
	 */
    @Test
    public void testFindAreaOfSquare()
    {
		 list.add(side);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(0, origin, list); 	 // 0 for creation of square using factory
		 double area = shape.getArea();								 // Find area of square
	
		 assertEquals( 25.0, area, 0 );
    }
    
    /**
     * Test to find perimeter of square
     */
    @Test
    public void testFindPerimeterOfSquare()
    {
    	 list.add(side);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(0, origin, list); 	 // 0 for creation of square using factory
		 double perimeter = shape.getPerimeter();					 // Find perimeter of square
	
		 assertEquals( 20.0, perimeter, 0 );
    }
    
    /**
     * Test to find origin of square
     */
    @Test
    public void testFindOriginOfSquare()
    {
    	 list.add(side);											 // Add parameters to list
		 Shape shape = ShapeFactory.createShape(0, origin, list); 	 // 0 for creation of square using factory
		 Point actualOrigin = shape.getOrigin();					 // Get origin of square
	
		 assertEquals( 5, actualOrigin.xCoordinate, 0 );
		 assertEquals( 10, actualOrigin.yCoordinate, 0 );
    }
    
    /**
     * Test to check isPointEnclosed when point lies within the square
     */
    @Test
    public void testIsPointEnclosedWithinSquare()
    {
        Point pointToCheck = new Point(6, 10);
        list.add(side);											 	// Add parameters to list
		Shape shape = ShapeFactory.createShape(0, origin, list); 	// 0 for creation of square using factory
       
        assertEquals(true, shape.isPointEnclosed(pointToCheck));
    }
    
    /**
     * Negative test to check isPointEnclosed when point lies outside the square
     */
    @Test
    public void testIsPointEnclosedWithinSquareWithPointOutside()
    {
        Point pointOutsideSquare = new Point(1000, 340);
        list.add(side);											 	// Add parameters to list
		Shape shape = ShapeFactory.createShape(0, origin, list); 	// 0 for creation of square using factory
       
        assertEquals(false, shape.isPointEnclosed(pointOutsideSquare));
    }
    
    /**
     * Test to find type of given shape
     */
    @Test
    public void testGetShapeType()
    {
        list.add(side);											 	// Add parameters to list
		Shape shape = ShapeFactory.createShape(0, origin, list); 	// 0 for creation of square using factory
       
        assertEquals(ShapeType.SQUARE, shape.getShapeType());
    }
}