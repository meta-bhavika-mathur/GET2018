package shapes;

import java.util.*;

public class Circle implements Shape {
    
    final double PI = 3.14;
    double radius;
    Point origin;
    Date timestamp;
    List<Double> parameterList;
    
    // Constructor for circle class
    public Circle( Point point, List<Double> list )
    {
        origin = point;
        parameterList = list;
        radius = parameterList.get(0);
        timestamp = new Date();
    }
    
    /**
     * To get area of circle defined as 3.14 * radius * radius.
     */
    @Override
    public double getArea() 
    {
        return PI * radius * radius;
    }

    /**
     * To get circumference of circle defined as 2 * 3.14 * radius.
     */
    @Override
    public double getPerimeter() 
    {
        return 2 * PI * radius ;
    }
    
    /**
     * To find whether given point is enclosed in the circle.
     * @param point, a point on screen. 
     * @return true if the point is enclosed in the circle, otherwise false.
     */
    @Override
    public boolean isPointEnclosed(Point point) 
    {
        if (((origin.xCoordinate - point.xCoordinate) * (origin.xCoordinate - point.xCoordinate) +
                (origin.yCoordinate - point.yCoordinate) * (origin.yCoordinate - point.yCoordinate)) <= radius * radius )
                return true;
            else
                return false;
    }

    /**
     * To find origin point of circle.
     * @return origin, origin point of circle.
     */  
    @Override
    public Point getOrigin()
    {
        return origin;
    }

    /**
     * To get shape type of circle
     */
    @Override
    public ShapeType getShapeType() 
    { 
        return ShapeType.CIRCLE;
    }

    /**
     * To get time stamp which is the time of creation of the circle
     */
    @Override
    public Date getTimestamp()
    {
        return timestamp;
    }

    /**
     * To set or change time stamp for circle
     */
    @Override
    public void setTimestamp(Date timestamp) 
    {
            this.timestamp = timestamp;  
    }
}