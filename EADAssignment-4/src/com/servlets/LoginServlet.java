package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.UserController;
import com.dao.DatabaseQueries;
import com.model.User;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet
{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        System.out.println("Inside Login Servlet");

        String userEmail = req.getParameter("email");
        String userPassword = req.getParameter("password");
       
        System.out.println(userEmail);
        
        DatabaseQueries query = new DatabaseQueries();
    
       
        UserController newUserController = new UserController();
        boolean result = newUserController.loginUser(userEmail, userPassword);
        
        if(result)
        {
            out.println("<script>alert(\"Login Successful!\")</script>");
            RequestDispatcher requestdispatch = req.getRequestDispatcher("UserProfile.html");
            requestdispatch.include(req, res);
        }
        else
        {
            out.println("<script>alert(\"Error while logining in!\")</script>");
            RequestDispatcher requestdispatch = req.getRequestDispatcher("index.html");
            requestdispatch.include(req, res);
        }

        
         
        
    }

}
