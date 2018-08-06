package shapes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TestTriangle {
	
	Point origin = new Point(5,10); 							 	// Set origin for triangle
	List<Double> list = new ArrayList<Double>();			 		// Create a new list of parameters
	double height = 4.0;                                            // Add height as parameter for shape triangle
    double base = 5.0;									            // Add first side as parameter for shape triangle 
    double sideWithPositiveSlope = 4.0; 							// Add second side as parameter for shape triangle 
    double sideWithNegativeSlope = 3.0; 							// Add third side as parameter for shape triangle 
	
    /**
	 * Test to find area of triangle
	 */
    @Test
    public void testFindAreaOfTriangle()
    {
    	 list.add(height);	 
    	 list.add(base);											// Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
		 double area = shape.getArea();								// Find area of triangle
	
		 assertEquals( 6.0, area, 0 );
    }
    
    /**
     * Test to find perimeter of triangle
     */
    @Test
    public void testFindPerimeterOfTriangle()
    {	
    	 list.add(height);	 
    	 list.add(base);										    // Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
		 double perimeter = shape.getPerimeter();					// Find perimeter of triangle
	
		 assertEquals( 12.0, perimeter, 0 );
    }
    
    /**
     * Test to find origin of triangle
     */
    @Test
    public void testFindOriginOfTriangle()
    {
   	 	 list.add(height);	 
   	 	 list.add(base);										    // Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
		 Point actualOrigin = shape.getOrigin();					// Find area of triangle
	
		 assertEquals( 5, actualOrigin.xCoordinate, 0 );
		 assertEquals( 10, actualOrigin.yCoordinate, 0 );
    }
    
    /**
     * Test to check isPointEnclosed when point lies within the triangle
     */
    @Test
    public void testIsPointEnclosedWithinTriangle()
    {
    	 Point pointToCheck = new Point(6, 12);

    	 list.add(height);	 
    	 list.add(base);										    // Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
       
         assertEquals(true, shape.isPointEnclosed(pointToCheck));
    }
    
    /**
     * Negative test to check isPointEnclosed when point lies outside the triangle
     */
    @Test
    public void testIsPointEnclosedWithinTriangleWithPointOutside()
    {
         Point pointOutsideSquare = new Point(100, 210);

    	 list.add(height);	 
    	 list.add(base);										    // Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
       
        assertEquals(false, shape.isPointEnclosed(pointOutsideSquare));
    }
    
    /**
     * Test to find type of given shape
     */
    @Test
    public void testGetShapeType()
    {
   	     list.add(height);	 
   	     list.add(base);										    // Add parameters to list
		 list.add(sideWithPositiveSlope);
		 list.add(sideWithNegativeSlope);
		 Shape shape = ShapeFactory.createShape(2, origin, list); 	// 2 for creation of triangle using factory
       
		 assertEquals(ShapeType.TRIANGLE, shape.getShapeType());
    }
}