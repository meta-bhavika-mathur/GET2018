package com.facade;

import java.util.List;

import com.dao.DatabaseQueries;
import com.enums.DBType;
import com.enums.EntityName;
import com.enums.Status;
import com.model.User;

public class UserFacade 
{
    private static UserFacade userFacade = new UserFacade();
    DatabaseQueries userDao = new DatabaseQueries();
    
    public static UserFacade getInstance() 
    {
        return userFacade;
    }
    
    /**
     * To insert new user in the database 
     * @param user
     * @return
     */
    public int insert(User user) 
    {
        int result = userDao.insertNewUser(user);
        return result;
    }

    public boolean login(String userEmail, String userPassword) 
    {
        System.out.println("Inside facade");
        boolean result = userDao.loginUser(userEmail, userPassword);
        return result;
    }

    public User getUserInfo(String userEmail)
    {
        System.out.println("Inside facade");
        User result = userDao.getUserInfo(userEmail);
        return result;
    }
}