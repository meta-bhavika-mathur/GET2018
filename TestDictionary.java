package dsassignment5;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TestDictionary 
{
    Dictionary dictionary = new Dictionary();
      
    /**
     * Test to add new entry to the dictionary
     */
    @Test
    public void testToAddNewEntry()
    {
        boolean firstResult = dictionary.addNewEntry("banana", "A fruit");
        boolean secondResult = dictionary.addNewEntry("lion", "An animal ");
        boolean thirdResult = dictionary.addNewEntry("apple", "A fruit ");
        
        assertEquals(true, firstResult);
        assertEquals(true, secondResult);
        assertEquals(true, thirdResult);
    }
    
    /**
     * Test to add new entry to the dictionary with empty key or value
     */
    @Test
    public void testToAddNewEntryWithEmptyKeyOrValue()
    {
        boolean firstResult = dictionary.addNewEntry("", "A fruit");
        boolean secondResult = dictionary.addNewEntry("lion", "");
        
        assertEquals(false, firstResult);
        assertEquals(false, secondResult);
    }
    
    /**
     * Test to add new entry to the dictionary with null key or value
     */
    @Test 
    public void testToAddNewEntryWithNullKey()
    {
        String str = null;
    	boolean firstResult = dictionary.addNewEntry(str, "A fruit");    
    	assertEquals(false, firstResult);
    }
    

    /**
     * Test to add new entry to the dictionary with null value
     */
    @Test
    public void testToAddNewEntryWithNullValue()
    {
        String string = null;
        boolean firstResult = dictionary.addNewEntry("lion", string);    
        assertEquals(false, firstResult);
    }
    
    /**
     * Test to get value corresponding to a key in dictionary
     */
    @Test
    public void testGetValueForKey()
    {
        boolean firstResult = dictionary.addNewEntry("banana", "A fruit");
        boolean secondResult = dictionary.addNewEntry("lion", "An animal");
        boolean thirdResult = dictionary.addNewEntry("apple", "A fruit ");
        
        try
        {
        	assertEquals("A fruit", dictionary.getValue("banana"));
        	assertEquals("An animal", dictionary.getValue("lion"));
        }
        catch(Exception exception){
        }     
    }
    
    /**
     * Test to get value corresponding to a key in dictionary when length of key is zero
     */
    @Test
    public void testGetValueForKeyWithLengthOfKeyZero()
    {
    	 try
         {
    		 boolean firstResult = dictionary.addNewEntry("banana", "A fruit");   
    		 assertEquals("", dictionary.getValue(""));
         }
    	 catch(Exception exception){
         }
    }
   
    /**
     * Test to get value corresponding to a key in dictionary when input key contains null
     */
    @Test
    public void testGetValueForKeyWithNullKey()
    {
        String  string = null;
    	boolean firstResult = dictionary.addNewEntry("banana", "A fruit");
    	try
    	{
    		assertEquals("banana", dictionary.getValue(string));
    	}
    	catch(Exception exception){
    	}      
    }
    
    /**
     * Test to get all the entries in the dictionary stored in sorted order
     */
    @Test
    public void testGetAllEntriesInDictionary()
    {
        dictionary.addNewEntry("banana", "A fruit");
        dictionary.addNewEntry("lion", "An animal");
        dictionary.addNewEntry("apple", "A fruit ");
        dictionary.addNewEntry("table", "A furniture");
        dictionary.addNewEntry("sparrow", "A kind of bird");
        
        Map<String, String> actualList = new LinkedHashMap<String, String>();
        actualList = dictionary.getAllEntries();
        
        List<String> expectedList = new ArrayList<String>();
        expectedList.add("apple");
        expectedList.add("banana");
        expectedList.add("lion");
        expectedList.add("sparrow");
        expectedList.add("table");

        Iterator iterator = actualList.entrySet().iterator();
        
        int i = 0;
        while(iterator.hasNext())
        {
           Map.Entry record = (Map.Entry)iterator.next();
           assertEquals(expectedList.get(i), (String)record.getKey());
           i++;
        }  
    } 
    
    /**
     * To find all the entries in the given range of keys
     */
    @Test
    public void testGetAllEntriesInGivenRange()
    {
        dictionary.addNewEntry("banana", "A fruit");
        dictionary.addNewEntry("lion", "An animal");
        dictionary.addNewEntry("apple", "A fruit ");
        dictionary.addNewEntry("table", "A furniture");
        dictionary.addNewEntry("sparrow", "A kind of bird");
        
        Map<String, String> actualList = new LinkedHashMap<String, String>();
        
        try
        {
        	actualList = dictionary.getAllEntriesInGivenRange("banana", "sparrow" );
        	List<String> expectedList = new ArrayList<String>();
            expectedList.add("banana");
            expectedList.add("lion");
            expectedList.add("sparrow");

            Iterator iterator = actualList.entrySet().iterator();
            
            int i = 0;
            while(iterator.hasNext())
            {
               Map.Entry record = (Map.Entry)iterator.next();
               assertEquals(expectedList.get(i), (String)record.getKey());
               i++;
            }  
        }
        catch(Exception exception){
    	}    
    }
    
    /**
     * Test to delete entry in the dictionary
     */
    @Test
    public void testDeleteSpecifiedEntry()
    {
        dictionary.addNewEntry("banana", "A fruit");
        dictionary.addNewEntry("lion", "An animal");
        dictionary.addNewEntry("apple", "A fruit ");
        dictionary.addNewEntry("table", "A furniture");
        dictionary.addNewEntry("sparrow", "A kind of bird");
        
        Map<String, String> actualList = new LinkedHashMap<String, String>();        
        try
        {
        	dictionary.deleteEntry("banana");
        	
        	List<String> expectedList = new ArrayList<String>();
            expectedList.add("apple");
            expectedList.add("lion");
            expectedList.add("sparrow");
            expectedList.add("table");

            Iterator iterator = actualList.entrySet().iterator();
            
            int i = 0;
            while(iterator.hasNext())
            {
               Map.Entry record = (Map.Entry)iterator.next();
               assertEquals(expectedList.get(i), (String)record.getKey());
               i++;
            }  
        }
        catch(Exception exception){
    	}    
    }
    
    /**
     * Test to delete entry in the dictionary when length of key provided is zero
     */
    @Test
    public void testDeleteSpecifiedEntryWithZeroKeyLength()
    {
        dictionary.addNewEntry("banana", "A fruit");
        dictionary.addNewEntry("lion", "An animal");
        dictionary.addNewEntry("apple", "A fruit ");
        
        Map<String, String> actualList = new LinkedHashMap<String, String>();        
        try
        {
        	dictionary.deleteEntry("");
        	
        	List<String> expectedList = new ArrayList<String>();
            expectedList.add("apple");
            expectedList.add("lion");
            expectedList.add("sparrow");

            Iterator iterator = actualList.entrySet().iterator();
            
            int i = 0;
            while(iterator.hasNext())
            {
               Map.Entry record = (Map.Entry)iterator.next();
               assertEquals(expectedList.get(i), (String)record.getKey());
               i++;
            }  
        }
        catch(Exception exception){
    	}    
    }
    
    /**
     * Test to delete entry in the dictionary when length of key provided is zero
     */
    @Test
    public void testDeleteSpecifiedEntryWithNullKeyAsInput()
    {
        dictionary.addNewEntry("banana", "A fruit");
        dictionary.addNewEntry("lion", "An animal");
        dictionary.addNewEntry("apple", "A fruit ");
        
        Map<String, String> actualList = new LinkedHashMap<String, String>();        
        try
        {
        	dictionary.deleteEntry("");
        	
        	List<String> expectedList = new ArrayList<String>();
            expectedList.add("apple");
            expectedList.add("lion");
            expectedList.add("sparrow");

            Iterator iterator = actualList.entrySet().iterator();
            
            int i = 0;
            while(iterator.hasNext())
            {
               Map.Entry record = (Map.Entry)iterator.next();
               assertEquals(expectedList.get(i), (String)record.getKey());
               i++;
            }  
        }
        catch(Exception exception){
    	}    
    }   
}