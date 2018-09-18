package com.metacube.training.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.training.mapper.EmployeeMapper;
import com.metacube.training.model.Employee;
import com.metacube.training.model.Project;

@Repository
@Transactional
public class EmployeeDAO
{
   private JdbcTemplate jdbcTemplate;
   
   @Autowired
   private SessionFactory sessionFactory;
  
   private final String SQL_DELETE_EMPLOYEE = "delete from Employee where emp_code = :id";
   private final String SQL_UPDATE_EMPLOYEE = "update Employee set first_name = :firstName, middle_name = :middleName, last_name  = :lastName, dob  = :dateOfBirth, gender = :gender, email_id = :email, password = :password where emp_code = :id";
   
    /**
    * To gets employee information by employee id
    */
   public Employee getById(int id)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       criteria.add(Restrictions.eq("id", id));
       return (Employee) criteria.uniqueResult();
    }
   
   /**
    * To get employee informatiojn by employee first name
    */
   public Employee getByFirstName(String firstName)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       criteria.add(Restrictions.eq("firstName", firstName));
       return (Employee) criteria.uniqueResult();
    }
   
    /**
    * To returns list of all employees
    */
   public List<Employee> getAll()
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       return criteria.list();
   }
   
    /**
    * To delete employee
    */
   public boolean delete(Employee employee)
   {
       try
       {
           TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_DELETE_EMPLOYEE);
           query.setParameter("id", employee.getId());
           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * To update employee information
    */
   public boolean update(Employee employee)
   {
       try
       {      
           TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_UPDATE_EMPLOYEE);
           query.setParameter("firstName", employee.getFirstName());
           query.setParameter("middleName", employee.getMiddleName());
           query.setParameter("lastName", employee.getLastName());
           query.setParameter("dateOfBirth", employee.getDateOfBirth());
           query.setParameter("gender", employee.getGender());
           query.setParameter("email", employee.getEmail());
           query.setParameter("password", employee.getPassword());
           query.setParameter("id", employee.getId());
           return query.executeUpdate() > 0;
       } 
       catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * To add employee
    */
   public boolean create(Employee employee)
   {
       try
       {
           sessionFactory.getCurrentSession().save(employee);
           return true;
       } catch (Exception e)
       {
           return false;
       }
   }

   /**
   * To get employee information by skill
   */
   public Employee getBySkill(String skill)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       criteria.add(Restrictions.eq("skill", skill));
       return (Employee) criteria.uniqueResult();
   }
}
