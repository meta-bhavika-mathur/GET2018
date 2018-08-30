package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.DatabaseQueries;
import com.connection.Employee;

public class UpdateInformation extends HttpServlet 
{
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
    
      PrintWriter out = res.getWriter();
      
      int employeeId = Integer.parseInt(req.getParameter("employeeId"));
      String employeeFirstName = req.getParameter("employeeFirstName");
      String emplpoyeeLastName = req.getParameter("employeeLastName");
      String emailId = req.getParameter("employeeEmail");
      int employeeAge = Integer.parseInt(req.getParameter("employeeAge"));
      
      Employee employee = new Employee(employeeId, employeeFirstName, emplpoyeeLastName, emailId, employeeAge );
      
      DatabaseQueries query = new DatabaseQueries();
      int result = query.updateEmployeeInformation(employee);
      
      if(result == 1)
      {
          out.println("<script>alert(\"Record Updated!\")</script>");
      }
      else
      {
          out.println("<script>alert(\"Record not Updated!\")</script>");
      }

      RequestDispatcher requestdispatch = req.getRequestDispatcher("UpdateInformation");
      requestdispatch.include(req, res);
  }
}