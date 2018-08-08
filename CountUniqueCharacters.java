package stringprocessing;

import java.util.*;

//A class to store the strings along with the number of unique characters present in them
public class CountUniqueCharacters 
{
	Map<String, Integer> listOfProcessedStrings;
	
	// Constructor for CountUniqueCharacters class
	public CountUniqueCharacters()
	{
		listOfProcessedStrings = new HashMap<String, Integer>();
	}
	
	/**
	 * To find number of unique characters in a string requires that the input string should not be empty or null
	 * @param string, input string in which the number of unique characters have to be found
	 * @return uniqueCharacters, containing number of unique characters in input string
	 */
	public int findNumberOfUniqueCharacters(String string)
	{
		int uniqueCharacters = 0;
		
		// Return -1 if invalid string
		if(string.length() <= 0)
			return -1;
		
		String stringToProcess = string.toLowerCase(); // Convert string to lower case
		
		/* If list contains some already processed strings and current input string is already processed, i.e
		 * found in the list of processed strings, then return the number of unique characters stored 
		 * corresponding to that string.
		 */
		if(!listOfProcessedStrings.isEmpty() && listOfProcessedStrings.containsKey(stringToProcess))
			return listOfProcessedStrings.get(stringToProcess);
		else
		{
			Set<Character> setOfCharacters = new HashSet<Character>();
			
			// Store each character on by one in the set
			for(int i = 0; i < stringToProcess.length(); i++)
			{
				/* Count unique characters, by excluding spaces. Add each character in set one by one.
				 * If successfully added, number of unique characters is incremented otherwise not. 
				 */
				if(stringToProcess.charAt(i)!= ' ' && setOfCharacters.add(stringToProcess.charAt(i)))
					uniqueCharacters++;
			}
			listOfProcessedStrings.put(string, uniqueCharacters); // Store currently processed string & number of 
																 //  unique characters
			return uniqueCharacters;
		}
	}
}