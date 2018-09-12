package com.metacube.training.EmployeePortalwithJDBCTemplate.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metacube.training.EmployeePortalwithJDBCTemplate.dao.EmployeeDAO;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Employee;

@Service
public class EmployeeService
{
   @Autowired
   private EmployeeDAO employeeDAO;
    /**
    * returns employee by email
    */
  
   public Employee getByEmail(String email)
   {
       if (employeeDAO.getByEmail(email) == null)
       {
           return null;
       }
       return employeeDAO.getByEmail(email);
   }
    /**
    * returns employee by id
    */

   public Employee getById(int id)
   {
       if (employeeDAO.getById(id) == null)
       {
           return null;
       }
       return employeeDAO.getById(id);
   }
   
   /**
    * returns employee by first name
    */

   public Employee getByFirstName(String firstName)
   {
       if (employeeDAO.getByFirstName(firstName) == null)
       {
           return null;
       }
       return employeeDAO.getByFirstName(firstName);
   }
   
    /**
    * returns list of all employees
    */
 
   public List<Employee> getAll()
   {
       return employeeDAO.getAll();
   }
    /**
    * deletes employee
    */
   public boolean delete(int id)
   {
       Employee employee = employeeDAO.getById(id);
       return employeeDAO.delete(employee);
   }
    /**
    * updates employee
    */
  
   public boolean update(Employee employee)
   {
       return employeeDAO.update(employee);
   }
    /**
    * adds employee
    */
   public boolean create(Employee employee)
   {
       return employeeDAO.create(employee);
   }
}