package assignment6;

import static org.junit.Assert.*;

import org.junit.Test;

public class SparseMatrixTest {

    @Test
    public void testGetTranspose() {
        
        int[][] array_non_sparse = new int[][]{{3,0},{0,0},{0,0},{5,5}};
        SparseMatrix input = new SparseMatrix(array_non_sparse);
        
        int[][] expected_output = new int[][]{{3, 0, 0, 5}, {0, 0, 0, 5}};
        SparseMatrix output = new SparseMatrix(expected_output);
        int[][] output_sparse = output.convertObjectToMatrix();
        
        int[][] actual_output = input.getTranspose();
        
        assertArrayEquals(output_sparse, actual_output);
    }
    
    // To test whether a matrix is symmetrical or not
    @Test
    public void testIsSymmetrical() {
        
        int[][] array_non_sparse = new int[][]{{3, 0, 0},{0, 0, 1},{0, 1, 0}};
        SparseMatrix input = new SparseMatrix(array_non_sparse);
     
        assertEquals(true, input.isSymmetricalMatrix());
    }

}
