package com.metacube.training.EmployeePortal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.metacube.training.EmployeePortal.model.Skills;

// To perform operations on skill Database
public class SkillDAO implements BaseDAO<Skills>{

    private Connection connection;
    
    public SkillDAO()
    {
        connection = JDBCConnectivity.getMysqlConnection("root", "1234", "EmployeePortal");
    }
    
    @Override
    public boolean add(Skills skill) 
    {
        int projectAdded = 0;
        String query = Queries.ADD_SKILL;
        
        try
        (
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {   
            preparedStatement.setString(1, skill.getName());
            
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
    public List<Skills> getAll() {
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
