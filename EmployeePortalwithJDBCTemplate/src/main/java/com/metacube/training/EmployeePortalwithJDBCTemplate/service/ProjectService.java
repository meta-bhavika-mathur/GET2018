package com.metacube.training.EmployeePortalwithJDBCTemplate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.training.EmployeePortalwithJDBCTemplate.dao.ProjectDAO;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Project;


@Service
public class ProjectService
{
    @Autowired
   private ProjectDAO projectDAO;
    /**
    * returns project by id
    */
  
   public Project getById(int id)
   {
        return projectDAO.getById(id);
   }
    /**
    * returns list of all projects 
    */

   public List<Project> getAll()
   {
       return projectDAO.getAll();
   }
    /**
    * deletes project
    */
  
   public boolean delete(int id)
   {
       Project project = projectDAO.getById(id);
       return projectDAO.delete(project);
   }
    /**
    * updates project
    */
 
   public boolean update(Project project)
   {
       return projectDAO.update(project);
   }
    /**
    * adds project
    */
 
   public boolean create(Project project)
   {
       return projectDAO.create(project);
   }
}