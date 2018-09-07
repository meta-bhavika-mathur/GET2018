package com.metacube.training.EmployeePortal.service;

import com.metacube.training.EmployeePortal.dao.JobDAO;
import com.metacube.training.EmployeePortal.model.JobTitle;


public class JobService 
{
    JobDAO jobDAO = new JobDAO();
    
    public boolean addJobTitle(JobTitle job)
    {
        return jobDAO.add(job);
    }
}