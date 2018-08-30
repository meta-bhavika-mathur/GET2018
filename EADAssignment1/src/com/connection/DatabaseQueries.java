package com.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DatabaseQueries 
{
    JDBCConnectivity jdbcConnect = new JDBCConnectivity();
    
    /**
     * To insert employee information into Employee Database
     * @param employee, employee information to be inserted in the database
     * @return true, if employee information is successfully inserted, otherwise false
     */
    public int insertEmployeeInformation(Employee employee)
    {
        final String query =  "INSERT INTO Employee(firstName, lastName, emailId, age) VALUES (?, ?, ?, ?)";
        
        int result = 0;
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getEmailId());
            statement.setInt(4, employee.getAge());
            
            result = statement.executeUpdate();
            System.out.println(query);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 
        return result;
    }
    
    public List<Employee> searchEmployee(String firstName, String secondName)
    {
        final String query =  "SELECT * FROM Employee WHERE firstName = ? AND lastName = ?";
        List<Employee> employeeList = new ArrayList<Employee>();
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
            statement.setString(1, firstName);
            statement.setString(2, secondName);
            
            ResultSet resultSet = statement.executeQuery();
            System.out.println(query);
            
            while (resultSet.next()) 
            { 
                int id = resultSet.getInt("employeeId");
                String fName = resultSet.getString("firstName");
                String lName = resultSet.getString("lastName");
                String email = resultSet.getString("emailId");
                int age = resultSet.getInt("age");
               
                Employee employee = new Employee(id, fName, lName, email, age);
                employeeList.add(employee);
            }
        }
        catch (SQLException ex)
        {
             ex.printStackTrace();
        } 
        return employeeList;
    }
    
    public List<Employee> getAllEmployees()
    {
        final String query =  "SELECT * FROM Employee";
        List<Employee> employeeList = new ArrayList<Employee>();
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) 
            { 
                int id = resultSet.getInt("employeeId");
                String fName = resultSet.getString("firstName");
                String lName = resultSet.getString("lastName");
                String email = resultSet.getString("emailId");
                int age = resultSet.getInt("age");
               
                Employee employee = new Employee(id, fName, lName, email, age);
                employeeList.add(employee);
            }
        }
        catch (SQLException ex)
        {
             ex.printStackTrace();
        } 
        return employeeList;
    }
    
    public Employee getEmployeeDetail(int id)
    {
        final String query =  "SELECT * FROM Employee WHERE employeeId = ?";
        Employee employee = null;
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            System.out.println(query);
            
            while (resultSet.next()) 
            { 
                String fName = resultSet.getString("firstName");
                String lName = resultSet.getString("lastName");
                String email = resultSet.getString("emailId");
                int age = resultSet.getInt("age");
                
                employee = new Employee(id, fName, lName, email, age);
            }
        }
        catch (SQLException ex)
        {
             ex.printStackTrace();
        } 
        return employee;
    }
    
    public int updateEmployeeInformation(Employee employee)
    {
        int employeeId = employee.getEmployeeId();
        String employeeFirstName = employee.getFirstName();
        String emplpoyeeLastName = employee.getLastName();
        String employeeEmailId = employee.getEmailId();
        int employeeAge = employee.getAge();
        
        final String query =  "UPDATE Employee SET firstName = ?, lastName = ?, employeeEmailId = ?, " +
                              "employeeAge = ? WHERE employeeId = ?";
               
        int result = 0;
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
            statement.setString(1, employeeFirstName);
            statement.setString(2, emplpoyeeLastName);
            statement.setString(3, employeeEmailId);
            statement.setInt(4, employeeAge);
            statement.setInt(5, employeeId);
            
            result = statement.executeUpdate();
            System.out.println(query);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 
        return result;
    }
}  