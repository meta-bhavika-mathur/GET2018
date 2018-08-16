package zoomanagement;

public class Bird extends Animal{

    boolean canFly;
    
    public Bird(String animalName, String animalSound, double animalWeight, AnimalFamily family, AnimalType animalType, boolean canFly)
    {
        super(animalName,animalSound,animalWeight,family,animalType);
        this.canFly = canFly;
    }
}
