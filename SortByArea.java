package shapes;

import java.util.*;

public class SortByArea implements Comparator<Shape>
{
    /**
     * Compares area of 2 shapes
     */
    public int compare(Shape firstShape, Shape secondShape)
    {
        return Double.compare(firstShape.getArea(), secondShape.getArea());
    }
}