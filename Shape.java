package shapes;

import java.util.Date;

public interface Shape 
{ 
    double getArea();
    double getPerimeter();
    Point getOrigin();
    boolean isPointEnclosed(Point point);
    ShapeType getShapeType();
    Date getTimestamp();
    void setTimestamp(Date timestamp);
}
