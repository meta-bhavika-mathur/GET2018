package zoomanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cage {
    
    int maxCapacity;
    int currentNumberOfAnimals;
    AnimalType animalType;
    public List<Animal> animalList;
    
    public Cage()
    {
    	
    }
    
    // Constructor for Cage
    public Cage(int maxCapacity, int currentCapacity, AnimalType animalType)
    {
        this.maxCapacity = maxCapacity;
        this.currentNumberOfAnimals = currentCapacity;
        this.animalType = animalType;
        animalList = new ArrayList<Animal>();
    }
    
    public void setCageInfo(int cageType)
    {
    	Scanner in = new Scanner(System.in);
    	System.out.println("Enter maximum capacity:");
    	maxCapacity = in.nextInt();
    	currentNumberOfAnimals = 0;
    	this.animalType = AnimalType.values()[cageType];
    	animalList = new ArrayList<Animal>();
    	
    }
    
    // Set maximum number of animals allowed in this cage
    public void setMaxAnimalLimit(int limit)
    {
        maxCapacity = limit;
    }
    
    // Get maximum number of animals allowed in this cage
    public int getMaxAnimalLimit()
    {
        return maxCapacity;
    }
    
    // Set number of animals present in the cage
    public void setnumberOfAnimals(int numberOfAnimals)
    {
    	currentNumberOfAnimals = numberOfAnimals;
    }
    
    // Get number of animals present in the cage
    public int getnumberOfAnimals()
    {
        return currentNumberOfAnimals;
    }
    
    // Set type of animal allowed in this cage
    public void setAnimalType(AnimalType type)
    {
        animalType = type;
    }
    
    // Get type of animal allowed in this cage
    public AnimalType getAnimalType()
    {
        return animalType;
    }
    
    /**
     * To add animal to the cage
     */
    public void addAnimalToCage(Animal animal)
    {
        animalList.add(animal);
        currentNumberOfAnimals++;
    }
    
    /**
     * To remove animal from the cage
     */
    public void removeAnimal(Animal animal)
    {
    	animalList.remove(animal);
    	currentNumberOfAnimals--;;
    }   
}
