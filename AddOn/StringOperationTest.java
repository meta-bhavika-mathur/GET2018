package Assignment2;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringOperationTest {
	
	StringOperation obj = new StringOperation();
	
	// Positive cases for finding length of string
	@org.junit.Test
	public void getLengthTest1() {
			assertEquals(5, obj.getLength("Hello"));
	}
	
	@org.junit.Test
	public void getLengthTest2() {
			assertEquals(12, obj.getLength("Big Universe"));
	}
	
	// Negative cases for finding length of string
	@org.junit.Test
	public void getLengthTest3() {
			assertNotEquals(5, obj.getLength("Good Morning"));
	}
		
	@org.junit.Test
	public void getLengthTest4() {
			assertNotEquals(12, obj.getLength("Universe"));
	}
	
	// Positive cases for reversing a string
	@org.junit.Test
	public void stringReverseTest1() {
			assertEquals("olleH", obj.reverseString("Hello"));
	}
	
	@org.junit.Test
	public void stringReverseTest2() {
			assertEquals("daB dna dooG", obj.reverseString("Good and Bad"));
	}
	
	// Negative test cases for reversing a string
	@org.junit.Test
	public void stringReverseTest3() {
			assertNotEquals("ooG", obj.reverseString("Good"));
	}
		
	@org.junit.Test
	public void stringReverseTest4() {
			assertNotEquals("daB dna dooG", obj.reverseString("Bad"));
	}
	
	// Positive cases for comparing 2 strings
	@org.junit.Test
	public void stringComparisionTest1() {
			assertEquals(1, obj.compareStrings("Hello", "Hello"));
	}
	
	@org.junit.Test
	public void stringComparisionTest2() {
			assertEquals(0, obj.compareStrings("Good", "good"));
	}
	
	// Negative test cases for comparing 2 strings
	@org.junit.Test
	public void stringComparisionTest3() {
			assertNotEquals(1, obj.compareStrings("Good", "good"));
	}
		
	@org.junit.Test
	public void stringComparisionTest4() {
			assertNotEquals(0, obj.compareStrings("HELLO", "HELLO"));
	}
	
	// Positive cases for reversing case of characters in a string
	@org.junit.Test
	public void stringReplaceCaseTest1() {
			assertEquals("hello", obj.replaceCase("HELLO"));
	}
	
	@org.junit.Test
	public void stringReplaceCaseTest2() {
			assertEquals("gOOd", obj.replaceCase("GooD"));
	}
	
	// Negative test cases for reversing case of characters in a string
	@org.junit.Test
	public void stringReplaceCaseTest3() {
			assertNotEquals("Good", obj.replaceCase("Good"));
	}
		
	@org.junit.Test
	public void stringReplaceCaseTest4() {
			assertNotEquals("hello", obj.replaceCase("HElLO"));
	}
	
	// Positive cases for find largest word in a string
	@org.junit.Test
	public void findfLargestTest1() {
			assertEquals("Hello", obj.largestWord("Hello Hi"));
	}
	
	@org.junit.Test
	public void findfLargestTest2() {
			assertEquals("Apples", obj.largestWord("Apples are red!"));
	}
	
	// Negative test cases for reversing case of characters in a string
	@org.junit.Test
	public void findfLargestTest3() {
			assertNotEquals("by", obj.largestWord("bye bye"));
	}
		
	@org.junit.Test
	public void findfLargestTest4() {
			assertNotEquals("are", obj.largestWord("Mangoes are yellow!"));
	}
	
	//Testing for null string
	
	@org.junit.Test
	public void nullStringTest2() {
		assertEquals("OLLEH", obj.reverseString(""));
	}
}
