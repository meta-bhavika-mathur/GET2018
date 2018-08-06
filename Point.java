package shapes;

// Defines a point on the screen with x and y coordinates
public class Point 
{
    double xCoordinate;
    double yCoordinate;
    
    // Default constructor for point class
    Point()
    {
        xCoordinate = 0.0;
        yCoordinate = 0.0;
    }
    
    // Parameterized constructor for Point class
    Point(double x, double y)
    {
        xCoordinate = x;
        yCoordinate = y;
    }
}