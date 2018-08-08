package zoomanagement;

public class Tiger extends Mammal
{
	String color;
    
    public Tiger( String animalName, String animalSound, double animalWeight, AnimalFamily family, AnimalType animalType, boolean hasFurs, String color )
    {
        super(animalName,animalSound,animalWeight,family,animalType,hasFurs);
        this.color = color;
    }

}
