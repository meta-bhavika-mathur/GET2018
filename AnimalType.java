package zoomanagement;

public enum AnimalType {

    LION(1), TIGER(2), PARROT(3), PEACOCK(4);
    
    private int animalTypeCode;
    
    AnimalType(int value)
    {
        animalTypeCode = value;
    }
    int getAnimalTypeCode()
    {
        return animalTypeCode;
    }
}
