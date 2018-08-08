package zoomanagement;

import java.util.*;

public class Zoo 
{
    List<Zone> zoneList;
    
    // Constructor for zoo, creates a new list of zones in a zoo
    public Zoo()
    {
        zoneList = new ArrayList<Zone>();
    }
    
    /**
     * To create a new zone based on zone type
     * @param zoneType, type of zone requires that it should be either MAMMAL, BIRD or REPTILE
     * @return true, if a new zone is successfully  created otherwise false
     */
    public boolean createZone(int zoneType)
    {
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Enter maximum allowed cages:");
    	int noOfCages = in.nextInt();
    	System.out.println("Does zone has park?");
    	boolean hasPark = in.nextBoolean();
    	System.out.println("Does zone has canteen?");
    	boolean hasCanteen = in.nextBoolean();
    	AnimalFamily animalFamily = AnimalFamily.values()[zoneType];
    	
    	Zone newZone = new Zone(animalFamily, noOfCages, hasPark, hasCanteen);
    	zoneList.add(newZone);
    	
    	return true;
    }
    
    /**
     * To add new cage to zone based on cage type
     * @param cageType, type of cage to be created to insert animal such that it should be either 1 or 2
     * for LION or TIGER and 3 for PARROT.
     */
    public boolean addNewCage(int cageType)
    {
    	Cage newCage = new Cage();
    	newCage.setCageInfo(cageType);
    	AnimalFamily animalFamily;
    	
    	if((cageType == 1) || (cageType == 2))
			animalFamily = AnimalFamily.MAMMAL;
		else
			animalFamily = AnimalFamily.BIRD;
    	
    	for(Zone zone : zoneList)
    	{
    		if((zone.getZonetype() == animalFamily) && zone.getMaximumCageLimit() > zone.getPresentNumberOfCages())
    			zone.addNewCageToZone(newCage);
    		return true;	
    	}
    	return false;
    }
    
    /**
     * To add new animal in the zoo, checks if zone and cage type is available in the zoo, if cage is not present create new
     * @return true, if animal can be successfully added in its zone, otherwise false;
     */
    public boolean addNewAnimalToZoo(int animalType)
    {
        AnimalType type =  AnimalType.values()[animalType];
        Animal newAnimal;
       
        switch(type.getAnimalTypeCode())
        {
        	case 1: Lion lion = getLionInformation();
        			newAnimal = lion;
        			break;
        	case 2: Tiger tiger = getTigerInformation();
        			newAnimal = tiger;
        			break;
        	case 3: Parrot parrot = getParrotInformation();
        			newAnimal = parrot;
        			break;
        	case 4: Peacock peacock = getPeacockInformation();
        			newAnimal = peacock;
        			break;
        	default:System.out.println("Invalid choice!");
        			return false;			
        }
    
        for( Iterator<Zone> i = zoneList.iterator(); i.hasNext(); )
        {
            Zone zoneName = (Zone)i.next();
            if( zoneName.animalFamily == newAnimal.getAnimalFamily() )
            {
                for( Iterator<Cage> j = zoneName.cageList.iterator(); j.hasNext(); )
                {
                    Cage cage = (Cage)j.next();
                    
                    if((cage.animalType == newAnimal.getAnimalType()) && (cage.currentNumberOfAnimals < cage.maxCapacity))
                    {
                        cage.addAnimalToCage(newAnimal);
                        return true;
                    }  
                }
                
                Cage newCage = new Cage(20, 0, newAnimal.animalType); // Create new cage if not present
                newCage.addAnimalToCage(newAnimal);   
                createZone(newAnimal.getAnimalFamily().getAnimalCode());
              
                return true;     
            }
        }    
        return false;
    } 
    
    /**
     * To remove animal from the zoo
     * @param id, id of the animal to be deleted
     * @return true, if the animal is successfully deleted otherwise false
     */
    public boolean removeAnimalFromZoo(int id)
    {
    	for(Zone zone: zoneList)
    	{
    		for(Cage cage: zone.cageList)
    		{
    			for(Animal animal: cage.animalList)
    			{
    				if(animal.getId() == id)
    				{
    					cage.removeAnimal(animal);
                        return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * To display all the information about an animal
     * @param id, id of the animal whose information is to be displayed
     */
    public boolean displayAnimalData(int id)
    {
    	for(Zone zone: zoneList)
    	{
    		for(Cage cage: zone.cageList)
    		{
    			for(Animal animal: cage.animalList)
    			{
    				if(animal.getId() == id)
    				{
    					animal.showDetails();
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * To get information related to lion before placing it in cage
     */
    public Lion getLionInformation()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name:");
        String name = in.next();
        System.out.println("Enter sound:");
        String sound = in.next();
        System.out.println("Enter weight:");
        double weight = in.nextDouble();
        Lion lion = new Lion(name,sound, weight, AnimalFamily.MAMMAL, AnimalType.LION, true, "White");
        
        return lion;
        
    }
    /*
     * *
     * To get information related to peacock before placing it in cage
     */
    public Peacock getPeacockInformation()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name:");
        String name = in.next();
        System.out.println("Enter sound:");
        String sound = in.next();
        System.out.println("Enter weight:");
        double weight = in.nextDouble();
        Peacock peacock = new Peacock(name,sound, weight, AnimalFamily.BIRD, AnimalType.PEACOCK, true, "White");
        
        return peacock;    
    }
    
    /**
     * To get information related to parrot before placing it in cage
     */
    public Parrot getParrotInformation()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name:");
        String name = in.next();
        System.out.println("Enter sound:");
        String sound = in.next();
        System.out.println("Enter weight:");
        Double weight = in.nextDouble();
        Parrot parrot = new Parrot(name, sound, weight, AnimalFamily.BIRD, AnimalType.PARROT, true, "Green");
        
        return parrot;    
    }
    
    /**
     * To get information related to tiger before placing it in cage
     */
    public Tiger getTigerInformation()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name:");
        String name = in.next();
        System.out.println("Enter sound:");
        String sound = in.next();
        System.out.println("Enter weight:");
        double weight = in.nextDouble();
        Tiger tiger = new Tiger(name,sound, weight, AnimalFamily.MAMMAL, AnimalType.TIGER, true, "Brown");
        
        return tiger;
    }
}
