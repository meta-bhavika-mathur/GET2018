package shapes;

import java.util.*;

public class Triangle implements Shape
{
	double height;
    double base;
    double sideWithPositiveSlope;
    double sideWithNegativeSlope;
    Point origin;
    Date timestamp;
    List<Double> parameterList;
    
    // Constructor for triangle
    public Triangle(Point point, List<Double> list)
    {
        origin = point;
        parameterList = list;
        height = parameterList.get(0);
        base = parameterList.get(1);
        sideWithPositiveSlope = parameterList.get(2);
        sideWithNegativeSlope = parameterList.get(3);
        timestamp = new Date();
    }
    
    /**
     * Test to find area of triangle.
     */
    @Override
    public double getArea() 
    {
        double s, area;
        
        s = ( base + sideWithPositiveSlope + sideWithNegativeSlope ) / 2;
        area = Math.sqrt( s * (s - base) * ( s - sideWithPositiveSlope) * (s - sideWithNegativeSlope));
        
        return area; 
    }
    
    /**
     * Test to find  perimeter of triangle.
     */
    @Override
    public double getPerimeter() {
    
        return base + sideWithPositiveSlope + sideWithNegativeSlope;
    }
    
    /**
     * To find whether given point is enclosed in the triangle.
     * @param point, a point on screen. 
     * @return true if the point is enclosed in the triangle, otherwise false.
     */
    @Override
    public boolean isPointEnclosed(Point point) 
    {
    	Point firstVertex = getFirstPoint();
        Point secondVertex = getSecondPoint();
        if( getArea() == (getAreaWithVertices(point, origin, firstVertex) +
                         getAreaWithVertices(point, origin, secondVertex) +
                         getAreaWithVertices(point, firstVertex, secondVertex)) )
            return true;
        return false;
    }
    
    /**
     * To find origin point of triangle.
     * @return origin, origin point of triangle.
     */
    @Override
    public Point getOrigin() 
    {
        return origin;
    }
    
    /**
     * To get shape type of triangle
     */
    @Override
    public ShapeType getShapeType() 
    {
        return ShapeType.TRIANGLE;
    }
    
    /**
     * To set or change time stamp i.e time of creation of triangle
     */
    @Override
    public void setTimestamp(Date timestamp) 
    {
            this.timestamp = timestamp;  
    }

    /**
     * To get time stamp for triangle
     */
    @Override
    public Date getTimestamp() 
    {
        return timestamp;
    }
    
    /**
     * Utility methods for isPointEnclosed method to find if a point enclosed within triangle
     */
    
    Point getFirstPoint()
    {
        double xCoordinate = Math.sqrt(Math.pow(sideWithPositiveSlope, 2) - Math.pow(height, 2));
        double yCoordinate = origin.yCoordinate + height;        
        return new Point(xCoordinate, yCoordinate);   
    }
    
    Point getSecondPoint()
    {
        return new Point((origin.xCoordinate + base), origin.yCoordinate);
    }
    
    double getAreaWithVertices(Point firstPoint, Point secondPoint, Point thirdPoint)
    {
        double area;
        area = Math.abs(((firstPoint.xCoordinate) * (secondPoint.yCoordinate - thirdPoint.yCoordinate) +
                		  secondPoint.xCoordinate * (thirdPoint.yCoordinate - firstPoint.yCoordinate) +
                		  thirdPoint.xCoordinate * (firstPoint.yCoordinate - secondPoint.yCoordinate))/ 2);
        return area;
    } 
}