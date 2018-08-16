package zoomanagement;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestCage 
{
	  Cage cage = new Cage(5, 2, AnimalType.LION);

	  @Test
	  public void testGetCageType()
	  {
	      String expectedOutput = "LION";
	      AnimalType actualAnimalType = cage.getAnimalType();
	      assertEquals(expectedOutput, actualAnimalType);
	  }
}
