package dsassignment5;

import java.util.*;

/*
 * A class to store words of a dictionary in sorted order, implemented using a Binary Search Tree. A binary search tree(BST) is a data structure that 
 * stores elements in sorted order. All words of a dictionary represented by nodes of the tree are arranged and stored in the form of tree structure.
 */
public class Dictionary implements DictionaryInterface
{
    final String NULL_POINTER_EXCEPTION_MESSAGE = "Null value recieved!";
    final String EMPTY_STRING_MESSSAGE = "Empty string recieved!";
    BinarySearchTree dictionaryTree;                    		// Binary Search Tree to store dictionary elements
    
    // Constructor for dictionary class
    public Dictionary() 
    {
        dictionaryTree = new BinarySearchTree();
    }
    
    /**
     * Constructor to create a dictionary with initial list of entries
     * @initialWordList, list of dictionary entries to be added to the dictionary at the time of creation
     */
    public Dictionary(String initialListOfWords) 
    {
        dictionaryTree = new BinarySearchTree(initialListOfWords);
    }

    /**
     * To add a new entry to the dictionary requires that key and value should not be null or empty
     * @param key, key representing word of dictionary
     * @param value, value representing meaning of the word 
     */
    @Override
    public boolean addNewEntry(String key, String value) throws NullPointerException
    {      
    	boolean result = false; 
    	try
    	{
    		if(key == null || value == null)
    			throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE); 
    		if(!( key.length() <= 0) && !( value.length() <= 0))
    	    	{
    			dictionaryTree.insert(key, value);
    		    	result = true;
    		}
    		return result;	
    	}
    	catch(Exception exception)
    	{
    		System.out.println(exception.getMessage());
    	}
        return result;
    }

    /**
     * To get value corresponding to a key in the dictionary
     * @param key, key whose corresponding value is to be obtained
     */
    @Override
    public String getValue(String key) throws NullPointerException, EmptyStringRecievedException
    {	
    	try
    	{
    		if(key == null)
    			throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    		if(key.length() <= 0)
    			throw new EmptyStringRecievedException(EMPTY_STRING_MESSSAGE);
    	}
    	catch(Exception exception)
    	{
    		System.out.println(exception.getMessage());
    	}
    	return dictionaryTree.searchValue(key); 	
    }

    /**
     * To get list of all entries in the dictionary
     * @return allEntries, list of all the entries in sorted order in the list
     */
    @Override
    public Map<String, String> getAllEntries() 
    {
        Map<String, String> allEntries = dictionaryTree.getAllEntriesOfDictionary();
        return allEntries;
    }

    /**
     * To get list of all the entries in the dictionary within the specified range
     * @param startKey, starting range of the entry
     * @param endKey, last key of the range
     */
    @Override
    public Map<String, String> getAllEntriesInGivenRange(String startKey, String endKey) throws NullPointerException, EmptyStringRecievedException
    {	
    	try
    	{
    		if(startKey == null || endKey == null)
    			throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    		if(startKey.length() <= 0 || endKey.length() <= 0)
    			throw new EmptyStringRecievedException(EMPTY_STRING_MESSSAGE);
    	}
    	catch(Exception exception)
    	{
    		System.out.println(exception.getMessage());
    	}
    	Map<String, String> entriesInRange = dictionaryTree.getAllEntriesInGivenRange(startKey, endKey);
        return entriesInRange;
    }

    /**
     * To delete entry from the dictionary whose value of key matches with the specified key as input
     */
    @Override
    public void deleteEntry(String key) throws NullPointerException, EmptyStringRecievedException
    {
    	try
    	{
    		if(key == null)
    			throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
    		if(key.length() <= 0)
    			throw new EmptyStringRecievedException(EMPTY_STRING_MESSSAGE); 	
    	}
    	catch(Exception exception)
    	{
    		System.out.println(exception.getMessage());
    	}  
    	dictionaryTree.deleteSpecifiedEntry(key);
    }
}
