package assignment1;

import static org.junit.Assert.*;
import org.junit.Test;

public class HexCalculatorTest {
	
	HexCalculator obj = new HexCalculator();

	/**
	 * Test Cases
	 */
	
	// Positive test cases for conversion of hexadecimal to decimal number
	@Test
	public void testHexaToDecimal1() {
		assertEquals(10, obj.hexaToDecimal("A"));
	}
	
	@Test
	public void testHexaToDecimal2() {
		assertEquals(254, obj.hexaToDecimal("FE"));
	}
	
	// Negative test cases for conversion of hexadecimal to decimal number
	@Test
	public void testHexaToDecimal3() {
		assertNotEquals(15, obj.hexaToDecimal("A"));
	}
	
	@Test
	public void testHexaToDecimal4() {
		assertNotEquals(215, obj.hexaToDecimal("19"));
	}
	
	
	// Positive test cases for conversion of decimal to hexadecimal number
	@Test
	public void testDeciToHexa1() {
		assertEquals("B", obj.decimalToHexa(11));
	}
		
	@Test
	public void testDeciToHexa2() {
		assertEquals("50", obj.decimalToHexa(80));
	}
		
	// Negative test cases for conversion of decimal to hexadecimal number
	@Test
	public void testDeciToHexa3() {
		assertNotEquals("52", obj.decimalToHexa(81));
	}
		
	@Test
	public void testDeciToHexa4() {
		assertNotEquals("215", obj.decimalToHexa(93));
	}
		
	// Positive test cases for addition of 2  hexadecimal number
	@Test
	public void testAddHexa1() {
		assertEquals("B5", obj.add("AA" , "B"));
	}
		
	@Test
	public void testAddHexa2() {
		assertEquals("CE7", obj.add("A","CDD"));
	}
		
	// Negative test cases for addition of 2  hexadecimal number
	@Test
	public void testAddHexa3() {
		assertNotEquals("52", obj.add("C","AA"));
	}
		
	@Test
	public void testAddHexa4() {
		assertNotEquals("21", obj.add("9","B"));
	}
	
	// Positive test cases for subtraction of 2  hexadecimal number
	@Test
	public void testSubtractHexa1() {
		assertEquals("F0F", obj.subtract("F19" , "A"));
	}
		
	@Test
	public void testSubtractHexa2() {
		assertEquals("A69", obj.subtract("A75","C"));
	}
		
	// Negative test cases for subtraction of 2  hexadecimal number
	@Test
	public void testSubtractHexa3() {
		assertNotEquals("52", obj.subtract("C","AA"));
	}
		
	@Test
	public void testSubtractHexa4() {
		assertNotEquals("21", obj.subtract("9","B"));
	}
	
	// Positive test cases for multiplication of 2  hexadecimal numbers
	@Test
	public void testMultiplyHexa1() {
		assertEquals("FA", obj.multiply("A" , "19"));
	}
		
	@Test
	public void testMultiplyHexa2() {
		assertEquals("78", obj.multiply("A","C"));
	}
		
	// Negative test cases for multiplication of 2  hexadecimal numbers
	@Test
	public void testMultiplyHexa3() {
		assertNotEquals("52", obj.multiply("C","AA"));
	}
		
	@Test
	public void testMultiplyHexa4() {
		assertNotEquals("21", obj.multiply("9","B"));
	}
	
	
	// Positive test cases for multiplication of 2  hexadecimal numbers
	@Test
	public void testDivideHexa1() {
		assertEquals("3", obj.divide("19" , "8"));
	}
		
	@Test
	public void testDivideHexa2() {
		assertEquals("3", obj.divide("C","4"));
	}
		
	// Negative test cases for multiplication of 2  hexadecimal numbers
	@Test
	public void testDivideHexa3() {
		assertNotEquals("52", obj.divide("C","AA"));
	}
		
	@Test
	public void testDivideHexa4() {
		assertNotEquals("21", obj.divide("9","B"));	
	}
	
	// Positive test cases for comparing equality of 2  hexadecimal numbers
	@Test
	public void testCompareEqual1() {
		assertEquals(true, obj.compareEqualTo("A", "A"));
	}
		
	@Test
	public void testCompareEqual2() {
		assertEquals(true, obj.compareEqualTo("0FF", "FF"));
	}
		
	// Negative test cases for comparing equality of 2 hexadecimal numbers
	@Test
	public void testCompareEqual3() {
		assertNotEquals(true, obj.compareEqualTo("ED", "FF"));
	}
		
	@Test
	public void testCompareEqual4() {
		assertNotEquals(true, obj.compareEqualTo("FF", "EEE"));
	}
	
	// Positive test cases for comparing if a number is greater than other hexadecimal number
	@Test
	public void testGreaterThan1() {
		assertEquals(true, obj.compareGreaterThan("13", "8"));
	}
		
	@Test
	public void testGreaterThan2() {
		assertEquals(true, obj.compareGreaterThan("0FF", "AF"));
	}
		
	// Negative test cases for comparing if a number is greater than other hexadecimal number
	@Test
	public void testGreaterThan3() {
		assertNotEquals(true, obj.compareGreaterThan("ED", "FF"));
	}
		
	@Test
	public void testGreaterThan4() {
		assertNotEquals(true, obj.compareGreaterThan("FF", "EEE"));
	}
	
	// Positive test cases for comparing if a number is less than other hexadecimal number
	@Test
	public void testLessThan1() {
		assertEquals(true, obj.compareLessThan("DD", "FF"));
	}
		
	@Test
	public void testLessThan2() {
		assertEquals(true, obj.compareLessThan("0FF", "EEE"));
	}
		
	// Negative test cases for comparing if a number is less than other hexadecimal number
	@Test
	public void testLessThan3() {
		assertNotEquals(true, obj.compareLessThan("FFF", "FF"));
	}
		
	@Test
	public void testLessThan4() {
		assertNotEquals(true, obj.compareLessThan("CCF", "CCA"));
	}		
}
