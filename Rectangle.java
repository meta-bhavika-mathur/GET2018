package shapes;

import java.util.Date;
import java.util.List;

public class Rectangle implements Shape
{	
	double length;
	double breadth;
    Point origin;
    Date timestamp;
    List<Double> parameterList;
   
    // Constructor for shape rectangle
    public Rectangle(Point point, List<Double> list)
    {
        origin = point;
        parameterList = list;
        length = parameterList.get(0); 
        breadth = parameterList.get(1); 
        timestamp = new Date();
    }
    
    /**
     * To get area of rectangle defined as length * breadth
     */
    @Override
    public double getArea()
    {
        return length * breadth;
    }

    /**
     * To get peri meter of rectangle defined as 2 * ( length + breadth )
     */
    @Override
    public double getPerimeter() 
    {
        return 2 * (length + breadth);
    }
    
    /**
     * To find whether given point is enclosed in the rectangle.
     * @param point, a point on screen. 
     * @return true if the point is enclosed in the rectangle, otherwise false.
     */
    @Override
    public boolean isPointEnclosed(Point point) 
    {
        if((( point.xCoordinate >= origin.xCoordinate) && ( point.xCoordinate <= origin.xCoordinate + length)) &&
           ((point.yCoordinate >= origin.yCoordinate) && ( point.yCoordinate <= origin.yCoordinate + breadth)))
           return true;
        else
            return false; 
    }
    
    /**
     * To find origin point of rectangle.
     * @return origin, origin point of rectangle.
     */
    @Override
    public Point getOrigin()
    {
        return origin; 
    }

    /**
     * To get shape type of rectangle
     */
    @Override
    public ShapeType getShapeType() 
    {
        return ShapeType.RECTANGLE;
    }
    
    /**
     * To get time stamp i.e time of creation of rectangle
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