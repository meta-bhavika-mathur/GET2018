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
   
   private final String SQL_FIND_EMPLOYEE = "from Employee where emp_code = :id";
   private final String SQL_SEARCH_EMPLOYEE_BY_NAME = "from Employee where first_name = :firstName";
   private final String SQL_DELETE_EMPLOYEE = "delete from Employee where emp_code = :id";
   private final String SQL_UPDATE_EMPLOYEE = "update Employee set first_name = :firstName, middle_name = :middleName, last_name  = :lastName, dob  = :dateOfBirth, gender = :gender, email_id = :email, password = :password where emp_code = :id";
   private final String SQL_GET_ALL = "from Employee";
 //  private final String SQL_INSERT_EMPLOYEE = "insert into employee(first_name, middle_name, last_name, dob, gender, email_id, password) values(:firstName,:middleName,:lastName,:dateOfBirth,:gender,:email,:password)";
   private final String SQL_GET_EMPLOYEE_BY_EMAIL = "from Employee where email_id = :email";
   private final String SQL_SEARCH_EMPLOYEE_BY_SKILL = "from Employee where email_id = :email";
    
   /**
    * gets employee by email
    */
   public Employee getByEmail(String email)
   {
       try
       {
           TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_GET_EMPLOYEE_BY_EMAIL);
           query.setParameter("email", email);
           return query.getSingleResult();
       } 
       catch (EmptyResultDataAccessException e)
       {
           return null;
       }
   }
   
    /**
    * gets employee employee id
    */
   public Employee getById(int id)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       criteria.add(Restrictions.eq("id", id));
       return (Employee) criteria.uniqueResult();
      /* try
       {
           TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_FIND_EMPLOYEE);
           query.setParameter("id", id);
           return query.getSingleResult();
       } catch (EmptyResultDataAccessException e)
       {
           return null;
       }*/
    }
   
   /**
    * gets employee employee id
    */
   public Employee getByFirstName(String firstName)
   {
       /*try
       {
           TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_SEARCH_EMPLOYEE_BY_NAME);
           query.setParameter("firstName", firstName);
           return query.getSingleResult();
       } catch (EmptyResultDataAccessException e)
       {
           return null;
       }*/
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       criteria.add(Restrictions.eq("firstName", firstName));
       return (Employee) criteria.uniqueResult();
    }
   
    /**
    * returns list of all employees
    */
   public List<Employee> getAll()
   {
       /*TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_GET_ALL);
       return query.getResultList();*/
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
       return criteria.list();
   }
   
    /**
    * deletes employee
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
    * deletes employee
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
    * adds employee
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

    public Employee getBySkill(String skill)
    {
        /*try
        {
            TypedQuery<Employee> query = sessionFactory.getCurrentSession().createQuery(SQL_SEARCH_EMPLOYEE_BY_SKILL);
            query.setParameter("skill", skill);
            return query.getSingleResult();
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }*/
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("skill", skill));
        return (Employee) criteria.uniqueResult();
    }
}