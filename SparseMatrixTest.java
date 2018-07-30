package assignment6;

import static org.junit.Assert.*;

import org.junit.Test;

public class SparseMatrixTest {

	// Test to perform addition of 2 sparse matrices
	@Test
	public void testToFindSumOfSparseMatrices() {
		
		int[][] first = new int[][]{{0, 10, 0, 12}, {0, 0, 0, 0}, {0, 0, 5, 0}, {15, 12, 0, 0}};  
		int[][] second = new int[][]{{0, 0, 8, 0}, {0, 0, 0, 23}, {0,0,9,0}, {20, 25, 0, 0}}; 
		int[][] expectedOutput = new int[][]{{0, 1, 10}, {0, 2, 8}, {0, 3, 12}, {1, 3, 23}, {2, 2, 14},
										 	 {3, 0, 35}, {3, 1, 37}};
		       
		SparseMatrix firstMatrix = new SparseMatrix(first);
		SparseMatrix secondMatrix = new SparseMatrix(second);
		assertArrayEquals( expectedOutput, firstMatrix.addMatrices(secondMatrix));
	}
	
	// Test to perform addition when matrices have different dimensions
	@Test (expected = AssertionError.class)
	public void testToFindSumWithUnequalDimensions() {
		
		int[][] first = new int[][]{{0, 10, 0, 12}, {0, 0, 0, 0}, {0, 0, 5, 0}};  
		int[][] second = new int[][]{{0, 0, 8, 0}, {0, 0, 0, 23}, {0,0,9,0}, {20, 25, 0, 0}}; 
		int[][] expectedOutput = new int[][]{{0, 1, 10}, {0, 2, 8}, {0, 3, 12}, {1, 3, 23}, {2, 2, 14},
										 	 {3, 0, 35}, {3, 1, 37}};
		       
		SparseMatrix firstMatrix = new SparseMatrix(first);
		SparseMatrix secondMatrix = new SparseMatrix(second);
		assertArrayEquals( expectedOutput, firstMatrix.addMatrices(secondMatrix));
	
	}
	
	// Test to find transpose of a sparse matrix
	@Test
	public void testToFindTranspose() {
		
		int[][] first = new int[][]{{0, 10, 20}, {0, 3, 0}, {1, 0, 1}};  
		int[][] expectedOutput = new int[][]{{0, 2, 1}, {1, 0, 10}, {1, 1, 3}, {2, 0, 20}, {2, 2, 1}};
		       
		SparseMatrix firstMatrix = new SparseMatrix(first);
	
		assertArrayEquals( expectedOutput, firstMatrix.getTranspose());
	}
	
	// Test to find if a matrix is symmetric 
	@Test
	public void testIsMatrixSymmetric() {
		
		int[][] first = new int[][]{{0, 2, 3}, {2, 0, 0}, {3, 0, 0}}; 
		SparseMatrix firstMatrix = new SparseMatrix(first);
	
		assertEquals( true, firstMatrix.isSymmetric());
	}
	
	// Test to find if a matrix is symmetric 
	@Test
	public void testIsSymmetricWithNonSymmetric() {
		
		int[][] first = new int[][]{{0, 10, 20}, {0, 3, 0}, {1, 0, 1}}; 
		SparseMatrix firstMatrix = new SparseMatrix(first);
	
		assertEquals( false, firstMatrix.isSymmetric());
	}
	
	// Test to perform addition of 2 sparse matrices
	@Test
	public void testToMultiplyMatrix() {
		
		int[][] first = new int[][]{{0, 10, 12}, {1, 0, 3}};  
		int[][] second = new int[][]{{2, 0, 8}, {5, 1, 0}};  
		int[][] expectedOutput = new int[][]{{0, 0, 96}, {0, 1, 10}, {1, 0, 18}, {1, 1, 5}}; 
										 	
		       
		SparseMatrix firstMatrix = new SparseMatrix(first);
		SparseMatrix secondMatrix = new SparseMatrix(second);
		assertArrayEquals( expectedOutput, firstMatrix.multiplyMatrices(secondMatrix));
	}
	
	// Test to perform addition of 2 sparse matrices
	@Test
	public void testToMultiplyMatrices() {
		
		int[][] first = new int[][]{{0, 10, 0, 12}, {0, 0, 0, 0}, {0, 0, 5, 0}, {15, 12, 0, 0}};  
		int[][] second = new int[][]{{0, 0, 8, 0}, {0, 0, 0, 23}, {0,0,9,0}, {20, 25, 0, 0}}; 
		int[][] expectedOutput = new int[][]{{0, 0, 240}, {0, 1, 300}, {0, 3, 230}, {2, 2, 45},
											 {3, 2, 120}, {3, 3, 276}};
										 	
		       
		SparseMatrix firstMatrix = new SparseMatrix(first);
		SparseMatrix secondMatrix = new SparseMatrix(second);
		assertArrayEquals( expectedOutput, firstMatrix.multiplyMatrices(secondMatrix));
	}
}
