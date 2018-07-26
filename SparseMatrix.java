package assignment6;

public final class SparseMatrix {

    // private 
    int[][] sparse_matrix;
   //  private 
    int N;
    
    //Constructor for sparse matrix
    public SparseMatrix(int size)
    {
        N = size;
    }
    // Constructor for sparse matrix when parameter passed is not a sparse matrix
    public SparseMatrix(int[][] non_sparse_matrix)
    {
        int size = 0;
        
        for(int i = 0; i < non_sparse_matrix.length; i++)
        {
            for ( int j = 0; j < non_sparse_matrix[ i ].length; j++)
            {
                if( non_sparse_matrix [i][j] != 0)
                    size++;
            }
        }
        N = size;
        sparse_matrix = new int[3][N];
        
        int k = 0;
        for(int i = 0; i < non_sparse_matrix.length; i++)
        {
            for ( int j = 0; j < non_sparse_matrix[ 0 ].length; j++)
            {
                if( non_sparse_matrix [i][j] != 0)
                {
                    sparse_matrix[0][k] = i;
                    sparse_matrix[1][k] = j;
                    sparse_matrix[2][k] = non_sparse_matrix[i][j];
                    k++;
                }
            }
        }
    }
    
    // Method to convert a sparse matrix object into matrix type of class
    public int[][] convertObjectToMatrix()
    {
        return sparse_matrix;
    }
    
    // Method to convert a sparse matrix into object matrix type of class
    public SparseMatrix convertSparseToNonSparse()
    {
       // return sparse_matrix;
    }
    
    // To get number of non zero elements in sparse matrix
    public int getNumberOfNonZeroElements()
    {
        return N;
    }
    
    /**
     * To find transpose of given sparse matrix
     * @return transpose of a matrix i.e it should contain only non zero values
     */
    public int[][] getTranspose()
    {
        int temp_var;
        int[][] temp_sparse = new int[3][getNumberOfNonZeroElements()] ;
        int[][] transpose = new int[3][getNumberOfNonZeroElements()];
        
        for( int i = 0; i < 3; i++)
        {
            for(int j = 0; j < getNumberOfNonZeroElements(); j++)
                temp_sparse[i][j] = sparse_matrix[i][j];
        }
        
        for(int i = 0; i < temp_sparse[0].length; i++)
        {
            temp_var = temp_sparse[0][i];
            temp_sparse[0][i] = temp_sparse[1][i];
            temp_sparse[1][i] = temp_var;
        }
        transpose = temp_sparse;
        
        return transpose;  
    }
    
    /**
     * To find whether a matrix is symmetrical matrix, a matrix is symmetrical if its transpose is equal to the matrix itself.
     * @return true, if the matrix is a symmetrical matrix, otherwise false.
     */
   public boolean isSymmetricalMatrix()
   {
       int i,j;
       int[][] tranpose = getTranspose();
       
       if (transpose[0].length !)
     
       i = 0;
       boolean flag = true;
       
       while( i < getNumberOfNonZeroElements() && flag)
       {
           j = 0;
           while( (j < getNumberOfNonZeroElements()) && flag )
           {
               if( tranpose[i][j] != sparse_matrix[i][j])
                       flag = false;
               j++;
           }
           i++;
       }
       return flag;
   }
   
   public SparseMatrix findSum(SparseMatrix first)
   {
       
   }
       
    
}
