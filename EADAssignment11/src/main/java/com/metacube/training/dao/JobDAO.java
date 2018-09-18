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
   
   private final String SQL_DELETE_JOB = "delete from Job where job_code = :id";
   private final String SQL_UPDATE_JOB = "update Job set job_title = :title where job_code = :id";
   
   /**
    * to returns job information by id
    */
   public Job getById(int id)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Job.class);
       criteria.add(Restrictions.eq("id", id));
       Job job = (Job) criteria.uniqueResult();
       return job;
   }
   
    /**
    * To return list of all jobs
    */
   public List<Job> getAll()
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Job.class);
       return criteria.list();
   }
   
    /**
    * To delete job information
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
    * To update job informaation 
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
    * To add job information
    */
   public void create(Job job)
   {
       sessionFactory.getCurrentSession().save(job);
   }
}
