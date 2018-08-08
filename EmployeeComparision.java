package namebasedsorting;

import java.util.*;

public class EmployeeComparision 
{
	List<Employee> listOfEmployees;
	
	// Constructor for EmployeeComparision class
	public EmployeeComparision()
	{
		listOfEmployees = new ArrayList<Employee>();
	}
	
	/**
	 * To add new employee to the list of employees requires that employee id should be unique
	 * @param employee, employee to insert into the employee list.
	 * @return true, if the employee is successfully added, otherwise false.
	 */
	public boolean addNewEmployee(Employee employee)
	{
		for(Employee emp : listOfEmployees)
		{
			if(emp.getEmployeeId() == employee.getEmployeeId())
				return false;
		}
		listOfEmployees.add(employee);
		return true;
	}
	
	/**
	 * To get list of employees
	 */
	public List<Employee> getEmployeeList()
	{
		return listOfEmployees;
	}
	
	/**
	 * To sort list on the basis of name in alphabetical order
	 */
	public void sortListNames()
	{
		Collections.sort(listOfEmployees, new SortByName());
	}
}