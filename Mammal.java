package zoomanagement;

public class Mammal extends Animal {
    
    boolean hasFurs;
    
    public Mammal( String animalName, String animalSound, double animalWeight, AnimalFamily family, AnimalType animalType, boolean hasFurs )
    {
        super(animalName,animalSound,animalWeight,family,animalType);
        this.hasFurs = hasFurs;
    }
}
 