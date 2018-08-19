package virtualcommandprompt;

import java.util.*;

public class Directory 
{
    private String name;
    private final Date dateOfCreation;
    private List<Directory> listOfSubDirectories;
    
    // Constructor for Directory class
    public Directory(String name) 
    {
    	this.name = name;
    	this.dateOfCreation = new Date();        
        this.listOfSubDirectories = new ArrayList<Directory>();
    }
    
    /**
     * To return a list of sub directories present in a directory
     */
    public List<Directory> getListOfSubDirectories() 
    {
        return listOfSubDirectories;
    }
    
    /**
     * To set list of directories contained in a directory
     * @param listOfSubDirectories, a list of directories
     */
    public void setListOfSubDirectory(List<Directory> listOfSubDirectories) 
    {
        this.listOfSubDirectories = listOfSubDirectories;
    }
    
    /**
     * To get name of the directory
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * To return date of creation of the directory
     * @return
     */
    public Date getDateOfCreation() 
    {
        return dateOfCreation;
    } 
}