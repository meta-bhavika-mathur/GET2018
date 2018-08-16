package shapes;

import java.util.*;

public class Square implements Shape 
{
    double side;
    Point origin;
    Date timestamp;
    List<Double> parameterList;
   
    // Constructor for square
    public Square(Point point, List<Double> list)
    {
        origin = point;
        parameterList = list;
        side = parameterList.get(0);  
        timestamp = new Date();
    }
    
    /**
     * To get area of square defined as side * side
     */
    @Override
    public double getArea()
    {
        return side * side;
    }

    /**
     * To get perimeter of square defined as 4 * side
     */
    @Override
    public double getPerimeter() 
    {
        return 4 * side;
    }
    
    /**
     * To find whether given point is enclosed in the square.
     * @param point, a point on screen. 
     * @return true if the point is enclosed in the square, otherwise false.
     */
    @Override
    public boolean isPointEnclosed(Point point) 
    {
        if((( point.xCoordinate >= origin.xCoordinate) && ( point.xCoordinate <= origin.xCoordinate + side)) &&
           ((point.yCoordinate >= origin.yCoordinate) && ( point.yCoordinate <= origin.yCoordinate + side)))
           return true;
        else
            return false; 
    }
    
    /**
     * To find origin point of square.
     * @return origin, origin point of square.
     */
    @Override
    public Point getOrigin()
    {
        return origin; 
    }

    /**
     * To get shape type of square
     */
    @Override
    public ShapeType getShapeType() 
    {
        return ShapeType.SQUARE;
    }
    
    /**
     * To get time stamp i.e time of creation of square
     */
    @Override
    public Date getTimestamp() 
    {
        return timestamp;
    }
    
    /**
     * To set or change time stamp for square
     */
    @Override
    public void setTimestamp(Date timestamp) 
    {
            this.timestamp = timestamp;  
    }
}