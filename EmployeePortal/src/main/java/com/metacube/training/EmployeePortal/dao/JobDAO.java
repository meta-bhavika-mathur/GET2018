package com.metacube.training.EmployeePortal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.metacube.training.EmployeePortal.model.JobTitle;

// Class to perform operations on JobTitle database
public class JobDAO implements BaseDAO<JobTitle>
{
    private Connection connection;
    
    public JobDAO()
    {
        connection = JDBCConnectivity.getMysqlConnection("root", "1234", "EmployeePortal");
    }
    
    // To add job title
    @Override
    public boolean add(JobTitle job) 
    {
        int jobAdded = 0;
        String query = Queries.ADD_JOB_TITLE;
        
        try
        (
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {   
            preparedStatement.setString(1, job.getJobTitle());
            
            jobAdded = preparedStatement.executeUpdate();
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
        
        if(jobAdded > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<JobTitle> getAll() {
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
