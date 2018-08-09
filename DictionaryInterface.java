package dsassignment5;

import java.util.*;

// Interface defining methods for implementing a dictionary
public interface DictionaryInterface
{
	// To add a new entry to a dictionary
	boolean addNewEntry(String key, String value)throws NullPointerException;                     
   
    // To delete a entry from a dictionary
    void deleteEntry(String key) throws NullPointerException, EmptyStringRecievedException;                                       
    
    // To get value corresponding to key
    String getValue(String key) throws NullPointerException, EmptyStringRecievedException;                                     
    
    // To get all the entries in  the dictionary
    Map<String, String> getAllEntries() throws NullPointerException, EmptyStringRecievedException;                               
   
    // To get entries in specified range from the dictionary    
    Map<String, String> getAllEntriesInGivenRange(String startKey,
    		String endKey)throws NullPointerException,EmptyStringRecievedException;
}