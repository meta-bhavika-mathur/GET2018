package assignment3;

class Marksheet
{
	int no_of_students;
	double[] grades;
	Marksheet(int n) throws ArithmeticException
	{
		if( n <= 0 )
			throw new ArithmeticException("Number of students should be greater than 0");
		no_of_students = n;
		grades = new double[no_of_students];
	}
	/**
	 * Set grade of all the students, where grade should be in range 0 to 100
	 * @param student, student number
	 * @param stud_grade , grade of student 'student'
	 */
	public void setGrade(int student, double stud_grade) throws ArithmeticException
	{
		if( stud_grade >= 0 && stud_grade <= 100)
			grades[student-1] = stud_grade;
		else
			throw new ArithmeticException("Grades must be in the range 0-100");
	}
	/**
	 * Get grade of a student
	 * @param student, student number whose grade is to be found
	 * @return grade of student
	 */
	public double getGrade(int student)  
	{
		return grades[student-1];
	}
	/**
	 * Compute average grade.
	 * @return average, containing average grade of all the students
	 * @throws ArithmeticException
	 */
	public double averageGrade() throws ArithmeticException
	{
		double sum = 0.0;
		if( no_of_students <= 0 )
			throw new ArithmeticException("Number of students should be greater than 0");
		for(int i=0; i < no_of_students ; i++)
		{
			sum += grades[i];
		}
		return sum/no_of_students;
	}
	/**
	 * Compute maximum grade.
	 * @return max, containing maximum grade among all the students
	 * @throws ArithmeticException
	 */
	public double maximumGrade() throws ArithmeticException
	{	
		double max = grades[0];
		if( no_of_students <= 0 )
			throw new ArithmeticException("Number of students should be greater than 0");
		for(int i=1; i < no_of_students; i++)
		{
			if(grades[i] > max)
			{
				max = grades[i];
			}
		}
		return max;
	}
	/**
	 * Compute minimum grade.
	 * @return min, containing minimum grade among all the students
	 * @throws ArithmeticException
	 */
	public double minimumGrade() throws ArithmeticException
	{	
		double min = grades[0];
		if( no_of_students <= 0 )
			throw new ArithmeticException("Number of students should be greater than 0");
		for(int i=1; i < no_of_students; i++)
		{
			if(grades[i] < min)
			{
				min = grades[i];
			}
		}
		return min;
	}
	/**
	 * Compute percentage of students passed.
	 * @return percent, containing percentage of students passed
	 * @throws ArithmeticException
	 */
	public double percentagePassed() throws ArithmeticException
	{
		int total = 0;
		if( no_of_students <= 0 )
			throw new ArithmeticException("Number of students should be greater than 0");
		for(int i=0; i < no_of_students ; i++)
		{
			if(grades[i] >= 40.00)
				total++;
		}
		return (total * 100.00 ) / no_of_students;
	}
}