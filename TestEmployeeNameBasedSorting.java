package namebasedsorting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import employee.Employee;
import employee.EmployeeComparision;
import java.util.*;

public class TestEmployeeNameBasedSorting {

	EmployeeComparision employee = new EmployeeComparision();
	
	/**
	 * Test to sort employee list on the basis of names 
	 */
	@Test
	public void testSortingOnEmployeeName() 
	{
		List<Employee> actualEmployeesList = new ArrayList<Employee>();
		List<Employee> expectedEmployeesList = new ArrayList<Employee>();
		 
		employee.addNewEmployee(new Employee(7, "ABC", "Mansarovar"));
		employee.addNewEmployee(new Employee(15, "ABB", "Malviya Nagar"));
		employee.addNewEmployee(new Employee(1, "DEF", "Sitapura"));
		employee.addNewEmployee(new Employee(4, "EFG", "Sanganer"));	
		
		List<Employee> employeesList = employee.getEmployeeList();	
		Collections.sort(employeesList, new SortByName());
		actualEmployeesList = employee.getEmployeeList();
		
		expectedEmployeesList.add(new Employee(15, "ABB", "Malviya Nagar"));
		expectedEmployeesList.add(new Employee(7, "ABC", "Mansarovar"));
		expectedEmployeesList.add(new Employee(1, "DEF", "Sitapura"));
		expectedEmployeesList.add(new Employee(4, "EFG", "Sanganer"));
		
		assertEquals(expectedEmployeesList, actualEmployeesList);
	}
}