package com.controller;

import java.util.*;

import com.enums.Status;
import com.facade.UserFacade;
import com.model.User;

public class UserController
{
    private UserFacade userFacade = UserFacade.getInstance();
    
  /*  public List<User> getAllUsers()
    {
        //return userFacade.getAllUser();
    }*/
    
    public int insertUser(User user)
    {
        int result = userFacade.insert(user);
        return result;   
    }

    public boolean loginUser(String userEmail, String userPassword)
    {
        boolean result = userFacade.login(userEmail, userPassword);
        return result;
    }

}
