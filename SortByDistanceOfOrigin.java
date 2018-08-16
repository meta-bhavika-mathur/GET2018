package shapes;

import java.util.*;

public class SortByDistanceOfOrigin implements Comparator<Shape>
{
    /**
     * Compares 2 shapes on the basis of distances between origin of screen and shape
     */
    public int compare(Shape firstShape, Shape secondShape)
    {
        ShapeUtility utility = new ShapeUtility();
        Point screenOrigin = new Point(0, 0);
        double distance1 = utility.getDistance(screenOrigin, firstShape.getOrigin());
        double distance2 = utility.getDistance(screenOrigin, secondShape.getOrigin());
        return Double.compare(distance1, distance2);
    }
}