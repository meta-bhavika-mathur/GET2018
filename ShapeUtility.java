package shapes;

public class ShapeUtility {
    
    /**
     * Utility method to return distance between 2 points
     * @param firstPoint first point
     * @param secondPoint second point
     * @return distance between 2 points
     */
    double getDistance(Point firstPoint, Point secondPoint)
    {
        return (Math.sqrt((secondPoint.xCoordinate - firstPoint.xCoordinate) * 
                          (secondPoint.xCoordinate - firstPoint.xCoordinate) +
                          (secondPoint.yCoordinate - firstPoint.yCoordinate) * 
                          (secondPoint.yCoordinate - firstPoint.yCoordinate)));
    }

}