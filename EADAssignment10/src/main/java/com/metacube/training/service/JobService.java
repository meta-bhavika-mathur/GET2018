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
    * returns job by id
    */
   public Job getById(int id)
   {
        return jobDAO.getById(id);
   }
    /**
    * returns list of all jobs
    */
   public List<Job> getAll()
   {
       return jobDAO.getAll();
   }
    /**
    * deletes job
    */
   public boolean delete(int id)
   {
       Job job = jobDAO.getById(id);
       return jobDAO.delete(job);
   }
    /**
    * updates job
    */
   public boolean update(Job job)
   {
       return jobDAO.update(job);
   }
    /**
    * adds job
    */
   public boolean create(Job job)
   {
       return jobDAO.create(job);
   }
}