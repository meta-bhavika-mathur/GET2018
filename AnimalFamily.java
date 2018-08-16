package zoomanagement;

public enum AnimalFamily {

    MAMMAL(1), BIRD(2), REPTILE(3);

    private int animalFamilyCode;
    
    AnimalFamily(int value)
    {
        animalFamilyCode = value;
    }
    int getAnimalCode()
    {
        return animalFamilyCode;
    }
    

  
}
