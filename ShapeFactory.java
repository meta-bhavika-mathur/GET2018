package shapes;

import java.util.*;

public class ShapeFactory 
{
    public static Shape createShape(int shapeType, Point point, List<Double> list) throws AssertionError
    {
        Shape shape = null;
        
        // If shape type code doesn't matches with the allowed shape types
        if(!(shapeType >= 0  && shapeType <= 4 )) 
    		throw new AssertionError("Invalid Shape Code!");
        
        switch(ShapeType.values()[shapeType])
        {
            case RECTANGLE: shape = new Rectangle(point, list);
                            break;

            case TRIANGLE:  shape = new Triangle(point, list);
                            break;
                           
            case CIRCLE:    shape = new Circle(point, list);
                            break;
                            
            case SQUARE:    shape = new Square(point, list);
                            break;
                            
            case POLYGON:   shape = new Polygon(point, list);
                            break;     
        }

       return shape;
    }
}