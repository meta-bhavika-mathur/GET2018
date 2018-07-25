package assignment5;

public final class Polynomial {

    int[] poly_array;
    
    Polynomial(int size_of_poly)
    {
        poly_array = new int[size_of_poly];
    }
    Polynomial(int[] array)
    {
        poly_array = array;
    }
    
    private int getPolynomialSize()
    {
        return poly_array.length;
    }
    
    private int getValue(int index)
    {
        return poly_array[ index ];
    }
    public float evaluatePolynomial(float variable)
    {
        int coefficient_value;
        float product, result;
        result = 0;
        
        int i = 0;
        while(i < poly_array.length)
        {
            coefficient_value = poly_array[ i ];
            product = 1;
            
            for(int j = 1; j <= poly_array[ i + 1]; j++)
                product *= variable;
            
            result = result + ( coefficient_value * product );
            
          i += 2;           
        }
        return result;    
    }
    
    public int findDegreeOfPolynomial()
    {
        int i = 1;
        int degree = poly_array[ i ];
        
        while (i < poly_array.length)
        {
            if(poly_array[ i ] > degree)
                degree = poly_array[ i ];
            i += 2;
        }
        return degree;
    }
    
    private int[][] convertToMatrix(Polynomial poly)
    {
        int size = poly.getPolynomialSize() / 2 ;
        int[][] p = new int[size][2];
        
        int i = 0;
        while( i< poly.getPolynomialSize() )
        {
            if( i % 2 == 0)
                p[i][0] = poly.getValue(i);
            else
                p[i][1] = poly.getValue(i);
            
            i ++;
        }
        
        return p;
    }
    
    public Polynomial addPolynomial(Polynomial first, Polynomial second)
    {
        int[][] first_poly = first.convertToMatrix(first);
        int[][] second_poly = second.convertToMatrix(second);
        
        int size_of_max = (first.getPolynomialSize() > second.getPolynomialSize()) ? first.getPolynomialSize() : second.getPolynomialSize();
        int size_of_min = (first.getPolynomialSize() < second.getPolynomialSize()) ? first.getPolynomialSize() : second.getPolynomialSize();
        int[][] sum_of_poly = new int[size_of_max + size_of_min][2];
        
        
        
        
    }
}

