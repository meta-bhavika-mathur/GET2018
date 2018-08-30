package com.servlets;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

import com.connection.DatabaseQueries;
import com.connection.Employee;

public class EmployeeServlet extends HttpServlet 
{
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
    
      PrintWriter out = res.getWriter();
      String employeeFirstName = req.getParameter("firstName");
      String emplpoyeeLastName = req.getParameter("lastName");
      String emailId = req.getParameter("email");
      int employeeAge = Integer.parseInt(req.getParameter("age"));
      
      Employee employee = new Employee(employeeFirstName, emplpoyeeLastName, emailId, employeeAge);
      
      DatabaseQueries query = new DatabaseQueries();
      int result = query.insertEmployeeInformation(employee);
      
      if(result == 1)
      {
          out.println("<script>alert(\"Record Inserted!\")</script>");
      }
      else
      {
          out.println("<script>alert(\"Record not inserted!\")</script>");
      }

      RequestDispatcher requestdispatch = req.getRequestDispatcher("AddEmployeeDetail.html");
      requestdispatch.include(req, res);
  }
}