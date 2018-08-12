package stringprocessing;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestCountUniqueCharacters
{
	CountUniqueCharacters string = new CountUniqueCharacters();
	
	/**
	 * Test to find number of unique characters with spaces in input string
	 */
	@Test
	public void testFindNumberOfUniqueCharactersWithSpaces() 
	{
		String testString = "Hello Everyone";
		int actualOutput = string.findNumberOfUniqueCharacters(testString);
		int expectedOutput = 8;
		assertEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Test to find number of unique characters with many repeating characters
	 */
	@Test
	public void testFindNumberOfUniqueCharactersWithContinuousRepeatingCharacters() 
	{
		String testString = "abababababababa";
		int actualOutput = string.findNumberOfUniqueCharacters(testString);
		int expectedOutput = 2;
		assertEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Test to find number of unique characters with string in mixed cases
	 */
	@Test
	public void testFindNumberOfUniqueCharactersCaseWithMixedCaseCharacters() 
	{
		String testString = "abababAbabaBBaba";
		int actualOutput = string.findNumberOfUniqueCharacters(testString);
		int expectedOutput = 2;
		assertEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Test to find number of unique characters with empty string passed as input
	 */
	@Test
	public void testFindNumberOfUniqueCharactersWithEmptyString() 
	{
		String testString = "";
		int actualOutput = string.findNumberOfUniqueCharacters(testString);
		int expectedOutput = -1;
		assertEquals(expectedOutput, actualOutput);
	}
}