package zoomanagement;

public class Peacock extends Bird {

	String color;
    
    public Peacock( String animalName, String animalSound, double animalWeight, AnimalFamily family, AnimalType animalType, boolean canFly, String color )
    {
        super(animalName,animalSound,animalWeight,family,animalType,canFly);
        this.color = color;
    }

}
