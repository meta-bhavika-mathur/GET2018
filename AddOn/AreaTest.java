package assignment2;

import static org.junit.Assert.*;

import org.junit.Test;

public class AreaTest {

	Area obj = new Area();

	// Positive Test Cases
	
	// Positive test cases for computation of area of rectangle
	@Test
	public void testAreaOfrectangle1() {
		assertEquals(6.0, obj.areaOfRectangle(2.0, 3.0), 0);
	}
	@Test
	public void testAreaOfrectangle2() {
		assertEquals(1500, obj.areaOfRectangle(50, 30), 0);
	}
	
	// Positive test cases for computation of area of circle
	@Test
	public void testAreaOfCircle1() {
		assertEquals(154, obj.areaOfCircle(7), 0);
	}
	@Test
	public void testAreaOfCircle2() {
		assertEquals(616, obj.areaOfCircle(14), 0);
	}
	
	// Positive test cases for computation of area of square
	@Test
	public void testAreaOfSquare1() {
		assertEquals(49, obj.areaOfSquare(7), 0);
	}
	@Test
	public void testAreaOfSquare2() {
		assertEquals(2500, obj.areaOfSquare(50), 0);
	}
	
	// Positive test cases for computation of area of triangle
	@Test
	public void testAreaOfTriangle1() {
		assertEquals(5, obj.areaOfTriangle(5, 2), 0);
	}
	@Test
	public void testAreaOfTriangle2() {
		assertEquals(150, obj.areaOfTriangle(10, 30), 0);
	}
	
	// Negative Test Cases
	
	// Negative test cases for computation of area of rectangle
	@Test
	public void testAreaOfrectangle3() {
		assertNotEquals(4.00, obj.areaOfRectangle(2.50, 2.00), 0);
	}
	@Test
	public void testAreaOfrectangle4() {
		assertNotEquals(250, obj.areaOfRectangle(50.00, 30.00), 0);
	}
	
	// Negative test cases for computation of area of circle
	@Test
	public void testAreaOfCircle3() {
		assertNotEquals(23.00, obj.areaOfCircle(7.0), 0);
	}
	@Test
	public void testAreaOfCircle4() {
		assertNotEquals(600.00, obj.areaOfCircle(14.0), 0);
	}
	
	//  Negative test cases for computation of area of square
	@Test
	public void testAreaOfSquare3() {
		assertNotEquals(42.00, obj.areaOfSquare(7.0), 0);
	}
	@Test
	public void testAreaOfSquare4() {
		assertNotEquals(500.00, obj.areaOfSquare(50.0), 0);
	}
	
	// Negative test cases for computation of area of triangle
	@Test
	public void testAreaOfTriangle3() {
		assertNotEquals(55.00, obj.areaOfTriangle(5.0, 2.0), 0);
	}
	@Test
	public void testAreaOfTriangle4() {
		assertNotEquals(250.00, obj.areaOfTriangle(10.0, 30.0), 0);
	}
	

}
