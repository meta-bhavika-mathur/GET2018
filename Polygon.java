package shapes;

import java.util.*;

public class Polygon implements Shape 
{
    int numberOfSides;
    double sideLength;
    Point origin;
    Date timestamp;
    List<Double> parameterList;
   
    // Constructor for polygon
    public Polygon(Point point, List<Double> list)
    {
        origin = point;
        parameterList = list;
        numberOfSides = (parameterList.get(0)).intValue();
        sideLength = parameterList.get(1);
        timestamp = new Date();
    }
    
    /**
     * To find area of Polygon
     */
    @Override
    public double getArea() 
    {
        double perimeter = getPerimeter();
        double apothem = sideLength / ( 2 * Math.tan( 180 / numberOfSides ));
        
        return ( perimeter * apothem ) / 2;
    }

    /**
     * To find perimeter of polygon
     */
    @Override
    public double getPerimeter() 
    {
        return numberOfSides * sideLength ;
    }

    /**
     * To find whether given point is enclosed in the polygon.
     * @param point, a point on screen. 
     * @return true if the point is enclosed in the polygon, otherwise false.
     */
    @Override
    public boolean isPointEnclosed(Point point) 
    {
        return false;
    }

    /**
     * To find origin point of polygon.
     * @return origin, origin point of polygon.
     */
    @Override
    public Point getOrigin() 
    {
        return origin;
    }
    
    /**
     * To get shape type of polygon
     */
    @Override
    public ShapeType getShapeType()
    {
        return ShapeType.POLYGON;
    }
    
    /**
     * To get time stamp which is the time of creation of polygon
     */
    @Override
    public Date getTimestamp()
    {
        return timestamp;
    }
    
    /**
     * To get time stamp which is the time of creation of polygon
     */
    @Override
    public void setTimestamp(Date timestamp) 
    {
        this.timestamp = timestamp;  
    }
}