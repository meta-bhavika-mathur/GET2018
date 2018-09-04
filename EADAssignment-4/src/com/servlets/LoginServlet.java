package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.UserController;
import com.dao.DatabaseQueries;
import com.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        System.out.println("Inside Login Servlet");

        String userEmail = req.getParameter("email");
        String userPassword = req.getParameter("password");
       
        System.out.println("Login Servlet:" + " " + userEmail);
        
        DatabaseQueries query = new DatabaseQueries();
    
       
        UserController newUserController = new UserController();
        boolean result = newUserController.loginUser(userEmail, userPassword);
        
        if(result)
        {
            
            System.out.println("Login Successful!");
            
            //generate a new session
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("email", userEmail);
            
            System.out.println("Inside login successful!");
            out.println("<script>alert(\"Login Successful!\")</script>");
            res.sendRedirect("./UserProfile");
        } 
        else
        {
            RequestDispatcher requestdispatch = getServletContext().getRequestDispatcher("/UserLogin.html");
            out.println("<script>alert('Either username or password is wrong!')</script>");
            requestdispatch.include(req, res);

        }        
    }
}