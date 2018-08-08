package zoomanagement;

import java.util.Scanner;

public class ZooManagement 
{  
    public static void main(String args[])
    { 
        Zoo zoo = new Zoo(); // Create an object of zoo
        int choice;
        
        System.out.println("Choose an operation to perform:");
        do
        {
            System.out.println("1.Add a zone to the zoo.");
        	System.out.println("2.Add cage to a zone.");
            System.out.println("3.Add new animal to zoo.");
            System.out.println("4.Get animal information.");
            System.out.println("5.Remove animal from zoo.");
        
            Scanner in = new Scanner(System.in);
            choice = in.nextInt();
       
            switch(choice)
            {
            	case 1: System.out.println("Enter zone type(type of animal family):");
            			System.out.println("1.MAMMAL");
            			System.out.println("2.BIRD");
            			System.out.println("3.REPTILE");
            			int zoneType = in.nextInt();
            			zoo.createZone(zoneType);
            			break;
            			
            	case 2: System.out.println("Enter cage type(category of animal):");
            			System.out.println("1.LION");
            			System.out.println("2.TIGER");
            			System.out.println("3.PARROT");
            			System.out.println("4.PEACOCK");
            			int animalType = in.nextInt();
            			int cageType = in.nextInt();
            			boolean result = zoo.addNewCage(cageType);
            			break;            			
         
            	case 3: System.out.println("Choose number for animal type:");
                		System.out.println("1.LION");
                		System.out.println("2.TIGER");
                		System.out.println("3.PARROT");
                		System.out.println("4.PEACOCK");
                		int animalCode = in.nextInt();
                		
                        boolean getResult = zoo.addNewAnimalToZoo(animalCode);
                        if(getResult)
                        	System.out.println("Animal successfully added!");
                        else
                        	System.out.println("Animal cannot be added!");
                        break;
                        
            	case 4: System.out.println("Enter animal id:");
            			int id = in.nextInt();
            			zoo.displayAnimalData(id);
            			break;
                        
            	case 5: System.out.println("Enter animal id:");
            			int animalId = in.nextInt();
        				boolean output = zoo.removeAnimalFromZoo(animalId);
        				
        				if(output)
        					System.out.println("Animal successfully removed!");
        				else
        					System.out.println("Animal not removed!");
        				break;
        			
                default:System.out.println("Wrong choice!Try again");
            }
            System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
            choice = in.nextInt();
            
        }while(choice != 0);
    }
}