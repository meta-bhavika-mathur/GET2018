package zoomanagement;

public class Reptile extends Animal {

    boolean isPoisonous;
    
    public Reptile(String animalName, String animalSound, double animalWeight, AnimalFamily family, AnimalType animalType, boolean isPoisonous)
    {
        super(animalName,animalSound,animalWeight,family,animalType);
        this.isPoisonous = isPoisonous;
    }
}
