package dsassignment5;

import java.util.*;

public class BSTDemo 
{
    public static void main(String[] args) 
    {
      
        Dictionary newDictionary = new Dictionary();
        
        newDictionary.addNewEntry("Pomegranate", "A fruit");
        newDictionary.addNewEntry("Banana", "A ");
        newDictionary.addNewEntry("Grapes", "ADGSD ");
        
        Map<String, String> resultWithAllEntries = new LinkedHashMap<String, String>();
        resultWithAllEntries = newDictionary.getAllEntries();
        
        Iterator trav = resultWithAllEntries.entrySet().iterator();
        
        while(trav.hasNext())
        {
           Map.Entry record = (Map.Entry)trav.next();  //will give next (Key, Value) pair
        
           System.out.println(record.getKey() + " " + record.getValue());
        }
        
        
     //   newDictionary.deleteEntry("Banana");
        
        Map<String, String> resultWithAllEntriesdelete = new LinkedHashMap<String, String>();
        resultWithAllEntriesdelete = newDictionary.getAllEntries();
        
        Iterator travr = resultWithAllEntries.entrySet().iterator();
        
        while(travr.hasNext())
        {
           Map.Entry record = (Map.Entry)travr.next();  //will give next (Key, Value) pair
        
           System.out.println(record.getKey() + " " + record.getValue());
        }
        
       

    
    }
}























