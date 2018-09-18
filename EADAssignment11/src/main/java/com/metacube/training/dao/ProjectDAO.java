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

import com.metacube.training.mapper.ProjectMapper;
import com.metacube.training.model.Project;
import com.metacube.training.model.Skill;

@Repository
@Transactional
public class ProjectDAO
{
   private JdbcTemplate jdbcTemplate;
   
   @Autowired
   private SessionFactory sessionFactory;
  
   private final String SQL_FIND_PROJECT = "from Project where project_id = :id";
   private final String SQL_DELETE_PROJECT = "delete from Project where project_id = :id";
   private final String SQL_UPDATE_PROJECT = "update Project set project_logo = :logo, description = :description, start_date  = :startDate, end_date  = :endDate where project_id = :id";
   private final String SQL_GET_ALL = "from Project";
 //  private final String SQL_INSERT_PROJECT = "insert into projectmaster(project_logo, description, start_date, end_date) values (:logo,:description,:startDate,:endDate)";
   
   /**
    * returns project by id
    */
   public Project getById(int id)
   {  
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
       criteria.add(Restrictions.eq("id", id));
       return (Project) criteria.uniqueResult();
       
       /*try
       {
           TypedQuery<Project> query = sessionFactory.getCurrentSession().createQuery(SQL_FIND_PROJECT);
           query.setParameter("id", id);
           return query.getSingleResult();
       } 
       catch (EmptyResultDataAccessException e)
       {
           return null;
       }*/
   }
   
    /**
    * returns list of all projects
    */
   public List<Project> getAll()
   {
    /*   TypedQuery<Project> query = sessionFactory.getCurrentSession().createQuery(SQL_GET_ALL);
       return query.getResultList();
       */
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
       return criteria.list();
   }
   
    /**
    * deletes project
    */
   public boolean delete(Project project)
   {
       try
       {
           TypedQuery<Project> query = sessionFactory.getCurrentSession().createQuery(SQL_DELETE_PROJECT);
           query.setParameter("id", project.getId());
           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * updates project
    */
   public boolean update(Project project)
   {
       try
       {
           TypedQuery<Project> query = sessionFactory.getCurrentSession().createQuery(SQL_UPDATE_PROJECT);
           query.setParameter("logo", project.getLogo());
         /*  query.setParameter("description", project.getDescription());*/
           query.setParameter("startDate", project.getStartDate());
           query.setParameter("endDate", project.getEndDate());

           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * adds project
    */
   public void create(Project project)
   {
       sessionFactory.getCurrentSession().save(project);
   }
} 