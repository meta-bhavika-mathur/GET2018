package shapes;

import java.util.*;

public class SortByTimestamp implements Comparator<Shape>{
    
    /**
     * Compares time stamp of 2 shapes
     */
    public int compare(Shape firstShape, Shape secondShape)
    {
        Date timestamp1 = firstShape.getTimestamp();
        Date timestamp2 = secondShape.getTimestamp();
        return timestamp1.compareTo(timestamp2);
    }

}
