package employeesorting;

// Employee class implementing Comparable Interface
public class Employee implements Comparable<Employee>
{
	int id;
	String name;
	String address;
	
	// Constructor for employee class
	public Employee(int id, String name, String address)
	{
		this.id = id;
		this.name = name;
		this.address = address;
	}
	
	// To set id of employee
	public void setEmployeeId(int id)
	{
		this.id = id;
	}
	
	// To set name of employee
	public void setEmployeeName(String name)
	{
		this.name = name;
	}
	
	// To set address of employee
	public void setEmployeeAddress(String address)
	{
		this.address = address;
	}
	
	// To get employee id
	public int getEmployeeId()
	{
		return id;
	}
	
	// To get employee name
	public String getEmployeeName()
	{
		return name;
	}
	
	// To get employee address
	public String getEmployeeAddress()
	{
		return address;
	}
	
	// Overridden compareTo method of Comparable interface
	@Override
	public int compareTo(Employee employee) 
	{
		return this.id - employee.id;
	}
	
	@Override
	public String toString() 
	{
	    return String.format("(%d, %s, %s)", id, name, address);
	}
}