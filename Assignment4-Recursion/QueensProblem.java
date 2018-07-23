package assignment4;


public class QueensProblem {
    
    int[][] result;
    int N;

    /**
     * To initialize the result matrix
     * @param size, dimension of array requires that number of rows must be equal to number of columns and must be even.
     * @throws assertion error if size is 0.
     */
    QueensProblem(int size) 
    {
        N = size + 1;
        result = new int[N][N];
        
        //Initialize matrix
        for( int x = 0 ; x < N ; x++ )
            for(int y = 0 ; y < N ; y++)
              result[ x ][ y ] = 0;
    }
    
    /**
     * To find if a queen can be placed in k-th row and i-th column.
     * @param k, queen number also represents column number.
     * @param i, column number represents column.
     * @return true if a queen can be placed at k-th row and i-th column otherwise false.
     */
    boolean place(int k, int i)
    {
        for(int j = 1; j <= k - 1; j++)
        {
            if(( result[j][i] == 1) || ( Math.abs(result[j][ i ] - i) == Math.abs(j - k) ))
                return false;
        }
        return true;
    }
    
    /**
     * To find solution for n-queens problem.The n Queen is the problem of placing n chess queens on an
        n X n chess board so that no two queens attack each other i.e no two queens share the same row, column, or diagonal.
     * @param result, 2-D array that stores the solution for the problem. Contains 1 for the position where queen can be placed otherwise 0
     * @param startRow, current row from where solution is to be found, requires that possible positions for startRow - 1 rows have already be
        found.
     * @param dimensionOfMatrix, size of the matrix requires that number of rows is equal to number of columns and should be even
     * @return true, if n queens can be placed in n X n chess board i.e if solution to queens problem can be found.
     */
    boolean nQueens(int result[][], int startRow, int dimensionOfMatrix)
    {
            for (int i = 1; i <= dimensionOfMatrix ; i++)
            {
                if(place(startRow, i))
                {
                    result[ startRow ][ i ] = 1;
                    if( startRow == dimensionOfMatrix)
                    {
                        for( int x = 1 ; x <= dimensionOfMatrix ; x++ )
                        {
                            for(int y = 1 ; y <= dimensionOfMatrix ; y++)
                                System.out.println( result[ x ][ y ] + " " );
                            System.out.println();
                        }
                        return true;
                    }
                    }
                    else
                    {
                       return nQueens(result, startRow + 1, dimensionOfMatrix);
                    }
                }
                else
                {
                   for(int j = 1; j <= dimensionOfMatrix; j++)
                       result
                }
            }
            return false;
        }
}