package com.connection;

public class Employee
{
    String firstName, lastname, emailId;
    int employeeId, age;

    // Constructor for employee class
    public Employee(String firstName, String lastName, String emailId, int age)
    {
        this.firstName = firstName;
        this.lastname = lastName;
        this.emailId = emailId;
        this.age = age;
    }
    
    // Constructor for employee class
    public Employee(int employeeId, String firstName, String lastName, String emailId, int age)
    {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastname = lastName;
        this.emailId = emailId;
        this.age = age;
    }
    
    // Get employee first name
    public String getFirstName() 
    {
        return firstName;
    }
    
    //Set employee first name
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    // Get employee last name
    public String getLastName()
    {
        return lastname;
    }
    
    // Set employee last name
    public void setLastName(String lastname)
    {
        this.lastname = lastname;
    }
    
    // Get employee age
    public int getAge() 
    {
        return age;
    }
    
    // Set employee age
    public void setAge(int age) 
    {
        this.age = age;
    }
    
    // Get employee email id
    public String getEmailId() 
    {
        return emailId;
    }

    // Get employee email id
    public void setEmailId(String emailId) 
    {
        this.emailId = emailId;
    }
    
    // Get employee id
    public int getEmployeeId()
    {
        return employeeId;
    }
   
}
