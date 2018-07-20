package assignment3;

import java.util.*;

class StudentDemo
{
	public static void main(String args[])
	{
		Marksheet first_sheet;
		double grade;
		int choice;

		try
		{
			System.out.println("Enter number of students:");
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();
			first_sheet = new Marksheet(num);

			Marksheet second_sheet = new Marksheet(num);
			second_sheet.getStudentGrade(1);
			
			System.out.println("Choose an operation to perform:");
			do
			{
				System.out.println("1.Set grade of students");
				System.out.println("2.Get grade of student");
				System.out.println("3.Get average grade");
				System.out.println("4.Get minimum grade");
				System.out.println("5.Get maximum grade");
				System.out.println("6.Get percentage of passed students.");
				choice = in.nextInt();
				switch(choice)
				{
					case 1: for(int i=1; i<=num ; i++)
					 	{
							System.out.println("Enter grade for student " + i + ":");
							grade = in.nextDouble();
							first_sheet.setStudentGrade(i,grade);
						}
						break;

					case 2: System.out.println("Enter student number:");
						num = in.nextInt();
						System.out.println("Grade of student" + num + ":" + first_sheet.getStudentGrade(num));
						break;

					case 3: System.out.println("Average grade:" + first_sheet.averageOfGrades());
					 	break;

					case 4: System.out.println("Minimum grade:" + first_sheet.minimumGrade());
						break;

				    	case 5: System.out.println("Maximum grade:" + first_sheet.maximumGrade());
						break;

				    	case 6: System.out.println("Percentage of passed students:" + first_sheet.percentagePassed());
						break;

					default:System.out.println("Wrong choice!Try again");
				}
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
				choice = in.nextInt();
			}while(choice != 0);
		}
		catch(ArithmeticException e)
		{
				System.out.println("Error:" + e.getMessage());
		} 
	}
}	
