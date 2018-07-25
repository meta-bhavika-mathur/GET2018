package assignment3;

class Area
{
	final String error_message = "Inputs should be greater than 0";

	/**
	 * Find area of rectangle.
	 * @param width, width of the rectangle requires that it should be always greater than 0
	 * @param height, height of the rectangle requires that it should be always greater than 0
	 * @return area, containing area of rectangle
	 * @throws ArithmeticException
	 */
	public double areaOfRectangle(double width , double height) throws ArithmeticException
	{
		if(width <= 0.0 || height <= 0.0)
			throw new ArithmeticException(error_message);		
		return width * height;
	}

	/**
	 * Find area of Square.
	 * @param side, side of square requires that it should be greater than 0
	 * @return area, containing area of Square
	 * @throws ArithmeticException
	 */
	public double areaOfSquare(double side) throws ArithmeticException
	{
		if(side <= 0.0)
			throw new ArithmeticException(error_message);
		return side * side;
	}

	/**
	 * Find area of Circle.
	 * @param radius, radius of circle requires that it should be greater than 0
	 * @return area, containing area of circle
	 * @throws ArithmeticException
	 */
	public double areaOfCircle(double radius) throws ArithmeticException
	{
		if(radius <= 0.0)
			throw new ArithmeticException(error_message);
		return (22 * radius * radius) / 7 ;
	}

	/**
	 * Find area of Triangle.
	 * @param base, base of triangle requires that it should be greater than 0
	 * @param height, height of triangle requires that it should be greater than 0
	 * @return area, containing of triangle
	 * @throws ArithmeticException
	 */
	public double areaOfTriangle(double base, double height) throws ArithmeticException
	{
		if(base <= 0.0 || height <=0.0)
			throw new ArithmeticException(error_message);
		return (base * height) / 2;
	}
}
