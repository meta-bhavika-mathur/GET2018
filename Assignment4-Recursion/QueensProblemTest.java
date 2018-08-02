package assignment4;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueensProblemTest {

	QueensProblem obj = new QueensProblem(4);
    int a[][] = new int[][]{{0, 1, 0, 0}, {0, 0, 0, 1}, {1, 0, 0, 0}, {0, 0, 1, 0}};
    
	
	@Test
	public void testQueens() {
			assertEquals( true , obj.nQueens(a, 1, 4));
	}

}
