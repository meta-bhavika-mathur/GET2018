package employeesorting;

import java.util.Comparator;

// Class to sort employee list on the basis of names using Comparator Interface
class SortByName implements Comparator<Employee>
{
	@Override
	public int compare(Employee firstEmployee, Employee secondEmployee) 
	{
		return firstEmployee.name.compareTo(secondEmployee.name);
	}
}