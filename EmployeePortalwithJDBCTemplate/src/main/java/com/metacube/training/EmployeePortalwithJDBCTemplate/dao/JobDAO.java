package com.metacube.training.EmployeePortalwithJDBCTemplate.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.metacube.training.EmployeePortalwithJDBCTemplate.mapper.JobMapper;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Job;

@Repository
public class JobDAO 
{
   private JdbcTemplate jdbcTemplate;
   
   @Autowired
   public JobDAO(DataSource dataSource)
   {
       jdbcTemplate = new JdbcTemplate(dataSource);
   }
   
   private final String SQL_FIND_JOB = "select * from jobtitle where job_code = ?";
   private final String SQL_DELETE_JOB = "delete from jobtitle where job_code = ?";
   private final String SQL_UPDATE_JOB = "update jobtitle set job_title = ? where job_code = ?";
   private final String SQL_GET_ALL = "select * from jobtitle";
   private final String SQL_INSERT_JOB = "insert into jobtitle(job_title) values(?)";
    /**
    * returns job by id
    */
   public Job getById(int id)
   {
       return jdbcTemplate.queryForObject(SQL_FIND_JOB, new Object[] { id },
               new JobMapper());
   }
    /**
    * returns list of all jobs
    */
   public List<Job> getAll()
   {
       return jdbcTemplate.query(SQL_GET_ALL, new JobMapper());
   }
    /**
    * deletes job
    */
   public boolean delete(Job job)
   {
       return jdbcTemplate.update(SQL_DELETE_JOB, job.getId()) > 0;
   }
    /**
    * updates job
    */
   public boolean update(Job job)
   {
       return jdbcTemplate.update(SQL_UPDATE_JOB, job.getTitle(), job.getId()) > 0;
   }
    /**
    * adds job
    */
   public boolean create(Job job)
   {
       return jdbcTemplate.update(SQL_INSERT_JOB, job.getTitle()) > 0;
   }
}