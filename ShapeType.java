package shapes;

// Enumeration specifying allowed shape types on screen with their respective codes
public enum ShapeType {
    
    SQUARE(0), RECTANGLE(1), TRIANGLE(2), CIRCLE(3), POLYGON(4);
    
    private int shapeTypeCode;
    
    ShapeType(int value)
    {
        shapeTypeCode = value;
    }
    
    int getShapeCode()
    {
        return shapeTypeCode;
    }

}