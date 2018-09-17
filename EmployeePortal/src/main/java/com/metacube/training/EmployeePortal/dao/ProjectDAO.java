package com.metacube.training.EmployeePortal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import com.metacube.training.EmployeePortal.model.Project;

public class ProjectDAO implements BaseDAO<Project>
{
    private Connection connection;
    
    public ProjectDAO()
    {
        connection = JDBCConnectivity.getMysqlConnection("root", "1234", "EmployeePortal");
    }

    @Override
    public boolean add(Project project) 
    {
        int projectAdded = 0;
        String query = Queries.ADD_PROJECT;
        
        try
        (
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {      
            Date startDate = project.getStartDate();  
            Date endDate =  project.getEndDate();
          
            preparedStatement.setString(1, project.getDescription());
            preparedStatement.setDate(2, (java.sql.Date) startDate);
            preparedStatement.setDate(3, (java.sql.Date) endDate);
            preparedStatement.setString(4, project.getDescription());
            
            projectAdded = preparedStatement.executeUpdate();
        } 
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            System.exit(2);
        }
        
        if(projectAdded > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<Project> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateName(String oldName, String newName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return false;
    }
}
