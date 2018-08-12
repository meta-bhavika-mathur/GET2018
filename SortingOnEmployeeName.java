package employeesorting;

import java.util.*;

public class SortingOnEmployeeName
{
	public static void main(String args[])
	{
		EmployeeComparision employee = new EmployeeComparision();
		List<Employee> employeesList = new ArrayList<Employee>();
		 
		employee.addNewEmployee(new Employee(7, "Tom", "Mansarovar"));
		employee.addNewEmployee(new Employee(15, "Bell", "Malviya Nagar"));
		employee.addNewEmployee(new Employee(1, "Alex", "Sitapura"));
		employee.addNewEmployee(new Employee(4, "Clara", "Sanganer"));
		employee.addNewEmployee(new Employee(7, "Tom", "Mansarovar"));
		 
		employeesList = employee.getEmployeeList();
		System.out.println("Before sorting: " + employeesList);
		 
		Collections.sort(employeesList, new SortByName());
		
		employeesList = employee.getEmployeeList();
		System.out.println("After sorting: " + employeesList);
	}
}
