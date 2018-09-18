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

import com.metacube.training.mapper.JobMapper;
import com.metacube.training.model.Employee;
import com.metacube.training.model.Job;
import com.metacube.training.model.Project;

@Repository
@Transactional
public class JobDAO 
{
   private JdbcTemplate jdbcTemplate;

   @Autowired
   private SessionFactory sessionFactory;
   
   private final String SQL_FIND_JOB = "from Job where job_code = :id";
   private final String SQL_DELETE_JOB = "delete from Job where job_code = :id";
   private final String SQL_UPDATE_JOB = "update Job set job_title = :title where job_code = :id";
   private final String SQL_GET_ALL = "from Job";
 //  private final String SQL_INSERT_JOB = "insert into jobtitle(job_title) values(:title)";
   
   /**
    * returns job by id
    */
   public Job getById(int id)
   {
       /*try
       {
           TypedQuery<Job> query = sessionFactory.getCurrentSession().createQuery(SQL_FIND_JOB);
           query.setParameter("id", id);
           return query.getSingleResult();
       } 
       catch (EmptyResultDataAccessException e)
       {
           return null;
       }*/
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Job.class);
       criteria.add(Restrictions.eq("id", id));
       Job job = (Job) criteria.uniqueResult();
       return job;
   }
    /**
    * returns list of all jobs
    */
   public List<Job> getAll()
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Job.class);
       return criteria.list();
       /*TypedQuery<Job> query = sessionFactory.getCurrentSession().createQuery(SQL_GET_ALL);
       return query.getResultList();*/
   }
    /**
    * deletes job
    */
   public boolean delete(Job job)
   {
       try
       {
           TypedQuery<Job> query = sessionFactory.getCurrentSession().createQuery(SQL_DELETE_JOB);
           query.setParameter("id", job.getId());
           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * updates job
    */
   public boolean update(Job job)
   {
       try
       {
           TypedQuery<Project> query = sessionFactory.getCurrentSession().createQuery(SQL_UPDATE_JOB);
           query.setParameter("title", job.getTitle()); 
           query.setParameter("id", job.getId());
           
           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * adds job
    */
   public void create(Job job)
   {
       sessionFactory.getCurrentSession().save(job);
   }
}