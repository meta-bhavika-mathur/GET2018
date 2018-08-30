package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.DatabaseQueries;
import com.connection.Employee;

public class DisplayEmployees extends HttpServlet 
{
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
    
      PrintWriter out = res.getWriter();
      List<Employee> employeeList = new ArrayList<Employee>();
         
      DatabaseQueries query = new DatabaseQueries();
      employeeList = query.getAllEmployees();
      
      out.print("<h1><center>Employee Details</center></h1>");
      out.print("<table align='center' cellpadding = '10' cellspacing = '5' border = '2'>");
      out.print("<tr>");
          out.print("<th>Employee ID</th>");
          out.print("<th>First Name</th>");
          out.print("<th>Last Name</th>");
          out.print("<th>Email ID</th>");
          out.print("<th>Age</th>");
          out.print("<th>Edit Employee Information</th>");
      out.print("</tr>");
     
      for(Employee employee: employeeList)
      {
          out.print("<tr>");
              out.print("<td>" + employee.getEmployeeId() + "</td>");
              out.print("<td>" + employee.getFirstName() + "</td>");
              out.print("<td>" + employee.getLastName() + "</td>");
              out.print("<td>" + employee.getEmailId() + "</td>");
              out.print("<td>" + employee.getAge() + "</td>");
              out.print("<td><form name = 'editEmployeeForm' method = 'post' action = 'UpdateEmployeeServlet'>");
              out.print("<input type = 'hidden' id = 'empId' name = 'empId' value = " + employee.getEmployeeId() + " />");
              out.print("<input type = 'submit' id = 'editEmployee' name = 'editEmployee' value = 'Edit'></form></td>");
              
          out.print("</tr>");
      }
      out.print("</table>");
  }
}              