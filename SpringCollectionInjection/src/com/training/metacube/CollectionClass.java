package com.training.metacube;

import java.util.*;

// A class to demonstrate spring collection injection containing List, Set and Map of colors
public class CollectionClass 
{
    List listOfColors;
    Set  setOfColors;
    Map  mapOfColors;

    // A setter method to set List
    public void setListOfColors(List listOfColors) 
    {
       this.listOfColors = listOfColors;
    }
    
    // Prints and returns all the elements of the list
    public List getListOfColors() 
    {
       System.out.println("List of colors is :"  + listOfColors);
       return listOfColors;
    }
    
    // A setter method to set a Set
    public void setSetOfColors(Set setOfColors) 
    {
       this.setOfColors = setOfColors;
    }
    
    // Prints and returns all the elements of the Set
    public Set getSetOfColors()
    {
       System.out.println("Set of colors is:"  + setOfColors);
       return setOfColors;
    }
    
    // A setter method to set Map elements
    public void setMapOfColors(Map mapOfColors)
    {
       this.mapOfColors = mapOfColors;
    }
    
    // Prints and returns all the elements of the Map
    public Map getMapOfColors()
    {
       System.out.println("Map elements are :"  + mapOfColors);
       return mapOfColors;
    }
}