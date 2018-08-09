package dsassignment5;

import java.io.File;
import java.io.FileReader;
import java.util.*;;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONInput
{
    JSONObject jsonObject;
    Dictionary newDictionary;
    Map<String, String> allEntries = new HashMap<String, String>();
    
    /**
     * Initializes JSONBSTDictionary and sets jsonObject
     * @param fileName name of .json file where data is kept
     */
    public JSONInput(String fileName)
    {
        newDictionary = new Dictionary();
        File file = null;
        try 
        {
            String location = "C:/Users/User21/workspace/DAtaStructuresAsignment-5/src/dsassignment5";
            file = new File(location, "/" + fileName);

            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(new FileReader(file));
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
    
    /**
     * Adds data from json file
     */
    public void add()
    {
        JSONArray inputArray = (JSONArray) jsonObject.get("addData");
        
        for(int i = 0; i < inputArray.size(); i++)
        {
            JSONArray entry = (JSONArray) inputArray.get(i);
            String key = (String) entry.get(0);
            String value = (String) entry.get(1);
            allEntries.put(key, value);
        }
    }
}
