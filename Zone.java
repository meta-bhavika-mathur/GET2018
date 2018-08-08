package zoomanagement;

import java.util.*;

public class Zone {
    
    AnimalFamily animalFamily;
    int maxCageLimit;
    boolean hasPark;
    boolean hasCanteen;
    List<Cage> cageList;
    
    public Zone(AnimalFamily category, int maxSize, boolean hasPark, boolean hasCanteen)
    {
    	animalFamily = category;
        maxSize = maxCageLimit;
        this.hasPark = hasPark;
        this.hasCanteen = hasCanteen;
        cageList = new ArrayList<Cage>(); 
    }
    
    public AnimalFamily getZonetype()
    {
        return animalFamily;
    }
    
    public int getMaximumCageLimit()
    {
    	return maxCageLimit;
    }
    
    public int getPresentNumberOfCages()
    {
    	return cageList.size();
    }
    
    public void addNewCageToZone(Cage cage)
    {
    	cageList.add(cage);
    }
}
