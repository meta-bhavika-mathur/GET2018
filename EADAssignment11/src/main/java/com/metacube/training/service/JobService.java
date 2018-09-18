package com.metacube.training.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metacube.training.dao.JobDAO;
import com.metacube.training.model.Job;

@Service
public class JobService
{
   @Autowired
   private JobDAO jobDAO;
   
   /**
    * To return job by id
    */
   public Job getById(int id)
   {
        return jobDAO.getById(id);
   }
   
    /**
    * To return list of all jobs
    */
   public List<Job> getAll()
   {
       return jobDAO.getAll();
   }
   
    /**
    * To delete job
    */
   public boolean delete(int id)
   {
       Job job = jobDAO.getById(id);
       return jobDAO.delete(job);
   }
   
    /**
    * To update job information
    */
   public boolean update(Job job)
   {
       return jobDAO.update(job);
   }
   
    /**
    * To add new job
    */
   public void create(Job job)
   {
       jobDAO.create(job);
   }
}
