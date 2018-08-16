package shapes;

import java.util.*;

public class SortByPerimeter implements Comparator<Shape>{

    /**
     * Compares perimeter of 2 shapes
     */
    public int compare(Shape firstShape, Shape secondShape)
    {
        return Double.compare(firstShape.getPerimeter(), secondShape.getPerimeter());
    }
}