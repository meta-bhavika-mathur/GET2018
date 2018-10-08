package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.UserController;
import com.dao.DatabaseQueries;
import com.facade.UserFacade;
import com.model.User;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet 
{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        System.out.println("Hello");
        String userFirstName = req.getParameter("firstName");
        String userLastName = req.getParameter("lastName");
        String userEmail = req.getParameter("email");
        String userPassword = req.getParameter("password");
        String userOrganization = req.getParameter("organization");
        int userAge = Integer.parseInt(req.getParameter("age"));
        String userPhoneNumber = req.getParameter("mobile");
        String image = "url";
        Date userDateOfBirth = null;
        System.out.println(userEmail);
        try
        {
            userDateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dob"));
        }
        catch(java.text.ParseException e)
        {
            e.printStackTrace();
            System.exit(1);    
        }
         
        DatabaseQueries query = new DatabaseQueries();
        User newUser = new User(userFirstName, userLastName, userEmail, userPassword, userOrganization, userAge, userDateOfBirth, image, userPhoneNumber);
        
        System.out.println(newUser.getEmailId());
        System.out.println(userDateOfBirth);
        System.out.println("Test:" + newUser.getDateOfBirth());
       
        UserController newUserController = new UserController();
        int result = newUserController.insertUser(newUser);
        
        if(result == 1)
        {
            out.println("<script>alert(\"Record Inserted!\")</script>");
        }
        else
        {
            out.println("<script>alert(\"Record not Inserted!\")</script>");
        }

        RequestDispatcher requestdispatch = req.getRequestDispatcher("index.html");
        requestdispatch.include(req, res);
         
        
    }
}
