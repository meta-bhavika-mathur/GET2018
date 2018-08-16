package zoomanagement;

public class Animal {
    
    static int animal_id = 1;
    String animal_name, animal_sound;
    double animal_weight;
    AnimalFamily family;
    AnimalType animalType;
   
   // Constructor for animal
   public Animal(String animal_name, String animal_sound, double animal_weight, AnimalFamily family, AnimalType animalType )
   {
       Animal.animal_id = animal_id + 1;
       this.animal_name = animal_name;
       this.animal_sound = animal_sound;
       this.animal_weight = animal_weight;
       this.family = family;
       this.animalType = animalType;    
   }
   
   // To get animal id
   public int getId()
   {
       return animal_id;
   }
   
   //  To get animal name
   public String getName()
   {
       return animal_name;
   }
   
   // To get animal family that should be either BIRD, REPTILE or MAMMAL
   public AnimalFamily getAnimalFamily()
   {
       return family;
   }
   
   // To  get sound of animal
   public String getSound()
   {
       return animal_sound;
   }
  
   // To get weight of animal
   public double getWeight()
   {
       return animal_weight;
   }
   
   // to get type of animal that can be PARROT, LION, TIGER, etc.
   public AnimalType getAnimalType()
   {
       return animalType;
   }
   
   // to set animal id
   public void setId(int id)
   {
       animal_id = id;
   }
   
   // To set animal name
   public void setName(String name)
   {
      animal_name = name;
   }
   
   // To set animal family
   public void setAnimalFamily(AnimalFamily family)
   {
       this.family = family;
   }
   
   // To set animal sound
   public void setSound(String sound)
   {
      animal_sound = sound;
   }
  
   // To set animal weight
   public void setWeight(double weight)
   {
       animal_weight = weight;
   }
   
   // To set animal type
   public void setAnimalType(AnimalType type)
   {
       animalType = type;
   }
   
   // To display animal details
   public void showDetails()
   {
	   System.out.println(animal_id);
	   System.out.println(animal_name);
	   System.out.println(animal_sound);
	   System.out.println(animal_weight);
	   System.out.println(family);
	   System.out.println(animalType); 
   } 
}
