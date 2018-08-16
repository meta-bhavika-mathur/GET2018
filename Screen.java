package shapes;

import java.util.*; 

public class Screen {
    
    public double maximumCoordinateForX;
    public double maximumCoordinateForY;
    public double xOrigin;
    public double yOrigin;
    List<Shape> shapesOnScreen;
    
    // Constructor for screen class
    public Screen()
    {
    	shapesOnScreen = new ArrayList<Shape>();
        maximumCoordinateForX = 1024;
        maximumCoordinateForY = 768;
        xOrigin = 0;
        yOrigin = 0;
    }
    
    // Parameterized constructor to set screen dimensions
    public Screen( double xMax, double yMAx )
    {
    	shapesOnScreen = new ArrayList<Shape>(); 
        maximumCoordinateForX = xMax;
        maximumCoordinateForY = yMAx;
        xOrigin = 0;
        yOrigin = 0;
    }
    
    /**
     * To add a shape on the screen, requires that shape must be either SQUARE, RECTANGLE, CICLE, 
     * TRIANGLE  or POLYGON
     * @param shape that is to be added to the screen
     */
    public void addNewShapeToScreen(Shape shape)
    {
    	shapesOnScreen.add(shape);
    }
    
    /**
     * To delete a shape from the screen
     * @param shape, shape which is to be deleted from the screen
     */
    public boolean deleteShapeFromScreen(Shape shape)
    {
        boolean flag = false;
       
        for( Iterator<Shape> i = shapesOnScreen.iterator(); i.hasNext(); )
        {
            Shape getShape = (Shape)i.next();
            
            if( getShape == shape )
            {
                shapesOnScreen.remove(getShape);
                flag = true;
                break;
            }  
        }
        return flag;
    }
    
    /**
     * To delete all shape of a particular shape type from screen
     * @param shapeType, type of shapes to be deleted
     */
    public void deleteAllOfParticularType(ShapeType shapeType)
    {      
        List<Shape> temporaryList = new ArrayList<Shape>();
        
        for(Shape shape: shapesOnScreen)
        {
            if(shape.getShapeType() ==  shapeType)
                temporaryList.add(shape);
        }
        
        for(Shape shape: temporaryList)
        	shapesOnScreen.remove(shape);
    }
    
    /**
     * To get list of all the shapes enclosing a given point
     * @param point, a point on screen
     * @return listOfShapes, a list of shapes containing given point
     */
    public List<Shape> getListOfShapesEnclosingGivenPoint(Point point)
    {
        List<Shape> listOfShapes = new ArrayList<Shape>();
        
        for( Shape shape: shapesOnScreen )
        {   
            if( shape.isPointEnclosed(point) )
                listOfShapes.add(shape);
        }     
        return listOfShapes;
    }
    
    /**
     * To sort screen objects based on given criteria
     * @param criteria, criteria on which list of shapes requires to be sorted
     * @return sortedList, sorted list based on given criteria
     */
    void sortShapesOnCriteria(SortCriteria criteria)
    {
        List<Shape> sortedList = shapesOnScreen;
        
        switch( criteria )
        {
            case AREA:            Collections.sort(sortedList, new SortByArea());
                                  break;
                       
            case PERIMETER:       Collections.sort(sortedList, new SortByPerimeter());
                                  break;
            
            case ORIGIN_DISTANCE: Collections.sort(sortedList, new SortByDistanceOfOrigin());
                                  break;
           
            case TIMESTAMP:       Collections.sort(sortedList, new SortByTimestamp());
                                  break;  
        }
    }
}