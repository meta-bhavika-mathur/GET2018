package com.metacube.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.training.dao.EmployeeDAO;
import com.metacube.training.model.Employee;

@Service
public class EmployeeService
{
   @Autowired
   private EmployeeDAO employeeDAO;
    
   /**
    * To returns employee by email
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
    * To returns employee by id
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
    * To return employee by first name
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
    * To return list of all employees
    */
 
   public List<Employee> getAll()
   {
       return employeeDAO.getAll();
   }
   
    /**
    * to delete employee infromation
    */
   public boolean delete(int id)
   {
       Employee employee = employeeDAO.getById(id);
       return employeeDAO.delete(employee);
   }
   
    /**
    * To update employee information
    */
   public boolean update(Employee employee)
   {
       return employeeDAO.update(employee);
   }
   
    /**
    * To add employee
    */
   public boolean create(Employee employee)
   {
       return employeeDAO.create(employee);
   }
   
   /**
   * To get employee infromation by skill
   */
   public Employee getBySkill(String skill)
   {
       if (employeeDAO.getBySkill(skill) == null)
       {
           return null;
       }
       return employeeDAO.getBySkill(skill);
   }
}
