package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.model.User;

public class DatabaseQueries 
{
    JDBCConnectivity jdbcConnect = new JDBCConnectivity();

    public int insertNewUser(User user)
    {
       final String query =  "INSERT INTO UserDetails(userFirstName, userLastName, userEmail, userPassword, userOrganization, userAge, userDOB,"
                           + " userImage, userMobileNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int result = 0;
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {

           Date utilDateOfBirth = user.getDateOfBirth();
           System.out.println(utilDateOfBirth);
           System.out.println(user.getEmailId());
           
           
           java.sql.Date sqlDateOfBirth = new java.sql.Date(utilDateOfBirth.getTime());
           
            
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmailId());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getOrganization());
            statement.setInt(6, user.getAge());
            statement.setDate(7, sqlDateOfBirth);
            statement.setInt(8, user.getAge());
            statement.setString(9, user.getMobileNumber());
              
            result = statement.executeUpdate();
            System.out.println(query);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 
        return result;
    }

    public boolean loginUser(String userEmail, String userPassword) 
    {

        final String query =  "SELECT userEmail FROM UserDetails WHERE userEmail = ? AND userPassword = ?";
        boolean result = false; ;
        
        try
        (
            Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {

            statement.setString(1, userEmail);
            statement.setString(2, userPassword);
            ResultSet resultSet = statement.executeQuery();
            
            result = resultSet.next();
            System.out.println(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 
        return result;
                           
    }
    
    public User getUserInfo(String email)
    {
        final String query =  "SELECT * FROM UserDetails WHERE email = 'bhavika.mathur@metacube.com'";
        User user = null;
        
        try
        (
            Connection connection = jdbcConnect.getMysqlConnection("root", "1234", "EmployeeDetail");
            PreparedStatement statement = connection.prepareStatement(query);
        ) 
        {
           // statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
        
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String emailId = resultSet.getString("emailId");
            String organsation = resultSet.getString("userOrganisation");
            int age = resultSet.getInt("age");
            Date dob = resultSet.getDate("userDOB");
            String mobile = resultSet.getString("userMobileNumber");
              
            user = new User(firstName, lastName, emailId, organsation, age, dob, mobile);
           
        }
        catch (SQLException ex)
        {
             ex.printStackTrace();
        } 
        return user;
    }

}  