package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.UserController;
import com.dao.DatabaseQueries;
import com.model.User;


/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet
{
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
      
        PrintWriter out = res.getWriter();
        HttpSession newSession = req.getSession();
       
        /*
        String userFirstName = req.getParameter("firstName");
        String userLastName = req.getParameter("lastName");
       // String userEmail = req.getParameter("email");*/
        
        String userEmail = (String) newSession.getAttribute("email");
        
        UserController newUserController = new UserController();
        User result = newUserController.getUser(userEmail);
        
        System.out.println("Inside User Profile!");
        System.out.println(userEmail);
        
        System.out.println(result.getFirstName());
       /* String userPassword = req.getParameter("password");
        String userOrganization = req.getParameter("organization");
        int userAge = Integer.parseInt(req.getParameter("age"));
        String userPhoneNumber = req.getParameter("mobile");
        String image = "url";*/
        /*Date userDateOfBirth = null;
        
        System.out.println("Inside User Profile!");
        try
        {
            userDateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dob"));
        }
        catch(java.text.ParseException e)
        {
            e.printStackTrace();
            System.exit(1);    
        }*/
        out.print("<head>");
                out.print("<title>User profile</title>");
                        out.print("<link href = 'css/effects.css' rel = 'stylesheet'>");
                                out.print("</head>");
    
                                        out.print("<body>");
                                                out.print("<div>");
                                                        out.print("<div class = 'topContainer' >");
                                                                out.print("<div class = 'logo'><img src = 'images/newimage.png' alt = 'Image Not Available' width = '75px' height = '85px'></div>");
                                                                        out.print("<div class = 'menu' >");
                                                                                out.print("<span><input type = 'button' value = 'Logout' onclick = 'window.open('HomePage.html')' /></span>") ; 
                                                                                    out.print("<span><input type = 'button' value = 'Friends' onclick = 'window.open('Fr.html')' /></span>") ; 
                                                                                        out.print(" <span><input type = 'label' value = " + userEmail + " /></span>");
                                                                                                out.print("<span>");
                                                                                                        out.print("<label for = 'image'>");
                                                                                                                out.print("<input type = 'file' name = 'image' id = 'image' style = 'display:none;'/>");
                                                                                                        out.print(" <img id = 'profileImage' src = 'images/defaultProfileImage.png' alt = 'Image not found!' accept = 'image/*' />");
                                                                                                        out.print("</label>");
                                                                                                        out.print("</span>");
                                                                                                        out.print("</div>");      
                                                                                                        out.print("</div>");
                                                                                                        out.print("</div>");
        
     
      
                                                                                                        out.print("<font><center>Your information is:-</center></font>");         
                                                                                                        out.print("<form>");
                                                                                                        out.print("<table align = 'center' cellpadding = '15'>");
                                                                                                        out.print("<tr>");
                                                                                                        out.print("<td>First Name</td>");
                                                                                                        out.print("<td><input type = 'text' name = 'firstName' value = 'Bhavika' disabled = 'true'/></td>");     
                                                                                                        out.print("</tr>");
                                                                                                        out.print("<tr>");
                                                                                                        out.print("<td>Last Name</td>");
                                                                                                        out.print("<td><input type = 'text' name = 'lastName' value = 'Mathur' disabled = 'true'/></td>");
                                                                                                        out.print("</tr>");
                                                                                                        out.print("<tr>");
                                                                                                            out.print("<td>Email</td>");
                                                                                                            out.print("<td><input type = 'email' name = 'email' value = 'bhavika.mathur@metacube.com' disabled = 'true'/></td>");
                                                                                                         out.print("</tr>");
                                                                                                         out.print("<tr>");
                                                                                                                                out.print("<td>Password</td>");
                                                                                                                                        out.print("<td><input type = 'password' name = 'password' value = '********' disabled = 'true'/></td>");
                                                                                                                                                out.print(" </tr>");
                                                                                                                                                        out.print("<tr>");
                                                                                                                                                                out.print(" <td>Contact Number</td>");
                                                                                                                                                                        out.print("<td><input type = 'text' name = 'contact' value = '9898989878' disabled = 'true'></td>");
                                                                                                                                                                        out.print("</tr>");
                                                                                                                                                                        out.print("<tr>");
                                                                                                                                                                                out.print("<td>Company</td>");
                                                                                                                                                                                        out.print("<td><input type = 'text' name = 'contact' value = 'Metacube' disabled = 'true'> </td>");
                                                                                                                                                                                                out.print("</tr>");
                
                                                                                                                                                                                                        out.print(" <tr colspan = '2'>");
                                                                                                                                                                                                                out.print(" <td></td>");
                    
                                                                                                                                                                                                                        out.print("</tr> ");  
                                                                                                                                                                                                                                out.print("</table>");
                                                                                                                                                                                                                                        out.print(" <center><input type = 'button' value = 'Edit Information' style = 'align:center' onclick = 'window.open('EditProfileInfo.html')'/></center>");
                                                                                                                                                                                                                                                out.print("</form>");
        
        out.println("<script>alert(\"USer Profile!\")</script>");
    }

}
