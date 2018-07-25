package assignment5;

public class Demo {
    
    public static void main(String a[])
    {
        IntSet s1 = new IntSet( new int[]{ 1,2,4,5, 7, 1000} );
        
        IntSet s2 = s1.getCompliment();
        
        
        for( int i = 0; i < s2.getSize(); i++)
        {
            System.out.println(s2.getData(i));
        }
    }

}
