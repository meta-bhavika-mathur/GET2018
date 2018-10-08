package com.metacube.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metacube.training.dao.ProjectDAO;
import com.metacube.training.model.Project;

@Service
public class ProjectService
{
    @Autowired
   private ProjectDAO projectDAO;
   
    /**
    * To return project by id
    */
   public Project getById(int id)
   {
        return projectDAO.getById(id);
   }
    
    /**
    * To return list of all projects 
    */
   public List<Project> getAll()
   {
       return projectDAO.getAll();
   }
    
    /**
    * To deletes project by id
    */
   public boolean delete(int id)
   {
       Project project = projectDAO.getById(id);
       return projectDAO.delete(project);
   }
  
    /**
    * To update project information
    */
   public boolean update(Project project)
   {
       return projectDAO.update(project);
   }
    
    /**
    * To add new project
    */
   public void create(Project project)
   {
       projectDAO.create(project);
   }
}
