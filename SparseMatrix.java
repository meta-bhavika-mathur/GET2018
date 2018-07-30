package assignment6;

public final class SparseMatrix {
	 
	final String ERROR_MESSAGE_SUM = "Cannot find sum!";
	final String ERROR_MESSAGE_MULTIPLY = "Cannot perform multiplication!";
	final String ERROR_MESSAGE_NOT_SYMMETRIC = "Can't be symmetric!";
	private int[][] sparse_matrix; 
    private int numRows;
    private int numCols;
    private int nonZeroElements;
    
    //Constructor for sparse matrix when number of rows and columns in matrix are provided
    public SparseMatrix(int numOfRows, int numOfColumns)
    {
        numRows = numOfRows;
        numCols = numOfColumns;
        nonZeroElements = numRows * numCols;
        sparse_matrix = new int[nonZeroElements][3];
    }
    
    // Constructor for sparse matrix when parameter passed is not a sparse matrix
    public SparseMatrix(int[][] non_sparse_matrix)
    {
        int size = 0;
        numRows = non_sparse_matrix.length;
        numCols = non_sparse_matrix[ 0 ].length;
        
        for(int i = 0; i < numRows; i++)
        {
            for ( int j = 0; j < numCols; j++)
            {
                if( non_sparse_matrix [i][j] != 0)
                    size++;
            }
        }
        
        nonZeroElements = size;
        sparse_matrix = new int[nonZeroElements][3];
        
        int k = 0;
        for(int i = 0; i < non_sparse_matrix.length; i++)
        {
            for ( int j = 0; j < non_sparse_matrix[ 0 ].length; j++)
            {
                if( non_sparse_matrix [i][j] != 0)
                {
                    sparse_matrix[k][0] = i;
                    sparse_matrix[k][1] = j;
                    sparse_matrix[k][2] = non_sparse_matrix[i][j];
                    k++;
                }
            }
        }
    }
    
    /**
     * To find number of rows in sparse matrix
     * @return numRows, number of rows of sparse matrix
     */
    public int findNumberOfRows()
    {
    	return numRows;
    }
    
    /**
     * To find number of columns in sparse matrix
     * @return numCols, number of columns in sparse matrix
     */
    public int findNumberOfColumns()
    {
    	return numCols;
    }
    
    /**
     * Utility method to convert sparse matrix stored without zeros into normal matrix containing zeros
     * @param withoutZeros, sparse matrix stored efficiently without zeros
     * @return withZeros, sparse matrix containing zeros
     */
    public int[][] convertToContainingZeros(int[][] withoutZeros)
    {
    	int rows, coloumns;
    	
    	int maxRow =  withoutZeros[withoutZeros.length - 1][0];
    	int maxCol = withoutZeros[0][1];
    	
    	for(int i = 1; i < withoutZeros.length; i++)
    	{
    		if( withoutZeros[i][1] > maxCol )
    			maxCol = withoutZeros[i][1];
    	}
    	rows = maxRow + 1;
    	coloumns = maxCol + 1;
    	
    	int withZeros[][] = new int[rows][coloumns];
    	
    	for(int i = 0; i < rows; i++)
    	{
    		for( int j = 0; j < coloumns; j++)
    		{
    			withZeros[i][j] = 0;
    		}
    	}
    	for ( int i = 0; i < withoutZeros.length; i++ )
    		withZeros[withoutZeros[i][0]][withoutZeros[i][1]] = withoutZeros[i][2];
    	
    	return withZeros;
    }

    /**
     * To add two sparse matrices, requires that number of rows and columns in one matrix must be equal to other
     * @param b, sparse matrix to be added to the calling matrix
     * @return sum, sparse matrix containing result of addition of 2 sparse matrices
     * @throws AssertionError
     */
    public int[][] addMatrices(SparseMatrix secondMatrix) throws AssertionError
    {
    	// if matrices don't have same dimensions throw error
        if (numRows != secondMatrix.numRows || numCols != secondMatrix.numCols) 
            throw new AssertionError(ERROR_MESSAGE_SUM);
        else 
        {
            SparseMatrix result = new SparseMatrix( numRows, numCols);
            int i,j,k;
            
            i = j = k = 0;
            while ( i < nonZeroElements && j < secondMatrix.nonZeroElements ) 
            {
                // If secondMatrix's row and column is smaller
                if (sparse_matrix[i][0] > secondMatrix.sparse_matrix[j][0] || 
                   (sparse_matrix[i][0] == secondMatrix.sparse_matrix[j][0] && 
                	sparse_matrix[i][1] > secondMatrix.sparse_matrix[j][1]))
                {
                	
                    result.sparse_matrix[k][0] = secondMatrix.sparse_matrix[j][0];
                    result.sparse_matrix[k][1] = secondMatrix.sparse_matrix[j][1];
                    result.sparse_matrix[k][2] = secondMatrix.sparse_matrix[j][2];
         
                    k++;
                    j++;
                }
                else if(sparse_matrix[i][0] < secondMatrix.sparse_matrix[j][0] || 
                	   (sparse_matrix[i][0] == secondMatrix.sparse_matrix[j][0] 
                	   && sparse_matrix[i][1] < secondMatrix.sparse_matrix[j][1]))
                {	
                	  result.sparse_matrix[k][0] = sparse_matrix[i][0];
                	  result.sparse_matrix[k][1] = sparse_matrix[i][1];
                	  result.sparse_matrix[k][2] = sparse_matrix[i][2];
                   
                	 k++;
                     i++;
                }
                else 
                {
                    // Add the values when row and column are same
                    int sum = sparse_matrix[i][2] + secondMatrix.sparse_matrix[j][2];
 
                    if (sum != 0)
                    {
                    	result.sparse_matrix[k][0] = sparse_matrix[i][0];
                   	  	result.sparse_matrix[k][1] = sparse_matrix[i][1];
                   	  	result.sparse_matrix[k][2] = sum;
                    }   
                    k++;
                    i++;
                    j++;
                }
            }
 
            // Inserting remaining elements
            while ( i < nonZeroElements )
            {
            	result.sparse_matrix[k][0] = sparse_matrix[i][0];
            	result.sparse_matrix[k][1] = sparse_matrix[i][1];
            	result.sparse_matrix[k][2] = sparse_matrix[i][2];
            	i++;
            	k++;
            }
            while ( j < secondMatrix.nonZeroElements )
            {
            	result.sparse_matrix[k][0] = secondMatrix.sparse_matrix[j][0];
            	result.sparse_matrix[k][1] = secondMatrix.sparse_matrix[j][1];
            	result.sparse_matrix[k][2] = secondMatrix.sparse_matrix[j][2];
            	j++;
            	k++;
            }
            result.nonZeroElements = k; 
            int[][] sum = new int[result.nonZeroElements][3];
            
            for(int x = 0; x < result.nonZeroElements; x++)
            	for( int y = 0; y < 3; y++)
            		sum[x][y] = result.sparse_matrix[x][y];
     
            return sum;
        }
    }
    
    /**
     * To find transpose of given sparse matrix
     * @return transpose of a matrix i.e it should contain only non zero values
     */
     public int[][] getTranspose()
     {
    	 SparseMatrix resultMatrix = new SparseMatrix(numCols, numRows);
         resultMatrix.nonZeroElements = nonZeroElements;
      
         // To count number of elements in each column
         int count[] = new int[numCols];
 
         // Initialize all to 0
         for (int i = 0; i < numCols; i++)
        	 count[i] = 0;
 
         for (int i = 0; i < nonZeroElements; i++)
        	 count[sparse_matrix[i][1]]++;
 
         int[] index = new int[numCols];
         index[0] = 0;
 
         // Initialize rest of the indices
         for (int i = 1; i < numCols; i++)
            index[i] = index[i - 1] + count[i - 1];
 
         for (int i = 0; i < nonZeroElements; i++) 
         {
            int row = index[sparse_matrix[i][1]]++;
            resultMatrix.sparse_matrix[row][0] = sparse_matrix[i][1];
            resultMatrix.sparse_matrix[row][1] = sparse_matrix[i][0];
            resultMatrix.sparse_matrix[row][2] = sparse_matrix[i][2];
        }
         
         int[][] sum = new int[resultMatrix.nonZeroElements][3];
         // Copy elements into a new matrix
         for(int x = 0; x < resultMatrix.nonZeroElements; x++)
         	for( int y = 0; y < 3; y++)
         		sum[x][y] = resultMatrix.sparse_matrix[x][y];
  
         return sum;
     }
     
     /**
      * To check whether given sparse matrix is symmetric or not, symmetric matrix a matrix that
        is equal to its transpose
      * @return, true if the matrix is symmetric otherwise false
      * @throws AssertionError
      */
     boolean isSymmetric() throws AssertionError
     {
         if(numRows != numCols)
        	 throw new AssertionError("ERROR_MESSAGE_NOT_SYMMETRIC");
         
         int[][] transposeMatrix = getTranspose();
         
         for(int i = 0; i < transposeMatrix.length; i++)
         {
             if(!( transposeMatrix[i][0] == sparse_matrix[i][0] 
                     && transposeMatrix[i][1] == sparse_matrix[i][1]
                             && transposeMatrix[i][2] == sparse_matrix[i][2]))
             {
                 return false;
             }
         }
         return true;
     }
     
    /**
     * To multiply 2 sparse matrices, requires that number of columns in first matrix must be equal 
       to number of rows in second matrix
     * @param b, sparse matrix as second argument for multiplication
     * @return, matrix containing result of multiplication
     */
    public int[][] multiplyMatrices(SparseMatrix second) throws AssertionError
    {
    	// Throw error if number of columns in first matrix not equals number of rows in second
        if (numCols != second.numRows) 
        		throw new AssertionError(ERROR_MESSAGE_MULTIPLY);
 
        int[][] product = new int[numRows][second.numCols];
        
        for(int i = 0; i < sparse_matrix.length; i++)
        {
            for(int j = 0; j < second.sparse_matrix.length; j++)
            {
                if( sparse_matrix[i][2] == second.sparse_matrix[j][1])
                {
                	product[sparse_matrix[i][1]][second.sparse_matrix[j][2]] +=  
                            sparse_matrix[i][0] * second.sparse_matrix[j][0];
                	
                }
            }
        }
        SparseMatrix productMatrix = new SparseMatrix(product);
        int[][] productResult = new int[ productMatrix.nonZeroElements ][ 3 ];
        
        // Copy elements into a new matrix
        for(int x = 0; x < productMatrix.nonZeroElements; x++)
        {
        	for( int y = 0; y < 3; y++)
        	{
        		productResult[x][y] = productMatrix.sparse_matrix[x][y];
        		System.out.println(productResult[x][y]);
        	}
        }
        return productResult;
     }
}
    