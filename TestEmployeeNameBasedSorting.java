package employeesorting;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

import employee.Employee;
import employee.EmployeeComparision;


public class TestEmployeeNameBasedSorting 
{
    EmployeeComparision employee = new EmployeeComparision();
	
    /**
     * Test to add a new employee to the employee list
     */
    @Test
    public void testAddEmployee()
    {
        boolean expectedOutput = true;
        boolean actualOutput = employee.addNewEmployee(new Employee(7, "ABC", "Address"));
        assertEquals(expectedOutput, actualOutput);
    }
	
    /**
     * Test to add a new employee to the employee list with id equal to another existing employee
     */
    @Test
    public void testAddEmployeeWithAlreadyExistingId()
    {
		employee.addNewEmployee(new Employee(7, "DDC", "Address"));
        boolean actualOutput = employee.addNewEmployee(new Employee(7, "ABC", "Address"));
        boolean expectedOutput = false;
        assertEquals(expectedOutput, actualOutput);
    }
	
	/** 
	 *To test natural order sorting of objects of employee class based on their id
	 */
    @Test
	public void testNaturalOrderSortingOnEmployeeId() 
	{
		
		List<Employee> actualEmployeesList = new ArrayList<Employee>();
		List<Employee> expectedEmployeesList = new ArrayList<Employee>();
		 
		employee.addNewEmployee(new Employee(7, "ABC", "Mansarovar"));
		employee.addNewEmployee(new Employee(15, "ABB", "Malviya Nagar"));
		employee.addNewEmployee(new Employee(1, "DEF", "Sitapura"));
		employee.addNewEmployee(new Employee(4, "EFG", "Sanganer"));	
		
		Collections.sort(employee.getEmployeeList()); // Sorting based on natural ordering
		actualEmployeesList = employee.getEmployeeList();
		
		expectedEmployeesList.add(new Employee(1, "DEF", "Sitapura"));
		expectedEmployeesList.add(new Employee(4, "EFG", "Sanganer"));
		expectedEmployeesList.add(new Employee(7, "ABC", "Mansarovar"));
		expectedEmployeesList.add(new Employee(15, "ABB", "Malviya Nagar"));
		
		for(int i = 0; i < actualEmployeesList.size(); i++)
			assertEquals(expectedEmployeesList.get(i).getEmployeeId(), actualEmployeesList.get(i).getEmployeeId());		
	}
	
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
		
		
		actualEmployeesList = employee.sortListNames();
		
		expectedEmployeesList.add(new Employee(15, "ABB", "Malviya Nagar"));
		expectedEmployeesList.add(new Employee(7, "ABC", "Mansarovar"));
		expectedEmployeesList.add(new Employee(1, "DEF", "Sitapura"));
		expectedEmployeesList.add(new Employee(4, "EFG", "Sanganer"));
		
		for(int i = 0; i < actualEmployeesList.size(); i++)
			assertEquals(expectedEmployeesList.get(i).getEmployeeName(), actualEmployeesList.get(i).getEmployeeName());		
	}	
}
