package assignment3;

import java.util.*;
class AreaDemo
{
	public static void main(String args[])
	{
		Area area_obj = new Area();
		Scanner sc = new Scanner(System.in);
		try
		{
		    int choice;
			System.out.println("Choose an operation to perform:");
			do
			{
				System.out.println("1.Area of Rectangle");
				System.out.println("2.Area of Square");
				System.out.println("3.Area of Circle");
				System.out.println("4.Area of Triangle");
				choice = sc.nextInt();
				switch(choice)
				{
					case 1:System.out.println("Enter width:");
						double width = sc.nextDouble();
						System.out.println("Enter height:");
						double height = sc.nextDouble();
						System.out.println("Area:" + area_obj.areaOfRectangle(width, height));
						break;
					case 2: System.out.println("Enter side:");
						double side = sc.nextDouble();
						System.out.println("Area:" + area_obj.areaOfSquare(side));
						break;
					case 3: System.out.println("Enter radius:");
						double radius = sc.nextDouble();
						System.out.println("Area:" + area_obj.areaOfCircle(radius));
						break;
					case 4:System.out.println("Enter base:");
						double base = sc.nextDouble();
						System.out.println("Enter height:");
						height = sc.nextDouble();
						System.out.println("Area:" + area_obj.areaOfRectangle(base, height));
						break;
					default:System.out.println("Wrong choice!Try again");
				}
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
				choice = sc.nextInt();
		}while(choice != 0);
		}
		catch(ArithmeticException e)
		{
				System.out.println("Error:" + e.getMessage());
		}
	}
}	