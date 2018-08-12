package employeesorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeNaturalOrderSorting 
{
	public static void main(String args[])
	{
		EmployeeComparision employee = new EmployeeComparision();
		List<Employee> employeesList = new ArrayList<Employee>();
		 
		employee.addNewEmployee(new Employee(7, "Tom", "Mansarovar"));
		employee.addNewEmployee(new Employee(15, "Sam", "Malviya Nagar"));
		employee.addNewEmployee(new Employee(1, "Alex", "Sitapura"));
		employee.addNewEmployee(new Employee(4, "Peter", "Sanganer"));
		employee.addNewEmployee(new Employee(7, "Tia", "Mansovar"));
		 
		employeesList = employee.getEmployeeList();
		System.out.println("Before sorting: " + employeesList);
		 
		Collections.sort(employee.getEmployeeList());
		 
		employeesList = employee.getEmployeeList();
		System.out.println("After sorting: " + employeesList);
	}
}
