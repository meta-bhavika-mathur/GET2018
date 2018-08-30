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

public class UpdateEmployeeServlet extends HttpServlet 
{
  public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
      PrintWriter out = res.getWriter();
      int employeeId = Integer.parseInt(req.getParameter("empId"));
      System.out.println(employeeId);
      
      DatabaseQueries query = new DatabaseQueries();
      Employee newEmployee =  query.getEmployeeDetail(employeeId);
      
      out.print("<form name = 'updateEmployeeForm' method = 'post' action = 'UpdateInformation'>");
          out.print("<table align = 'center'>");
              out.print("<tr>");
                      out.print("<td><input type = 'text' value = 'Id'" + " /></td>");
                      out.print("<td><input type = 'number' id = 'employeeId' name = 'employeeId' value = " + newEmployee.getEmployeeId() + " /></td>");
                  out.print("</tr>");
                  out.print("<tr>");
                  out.print("<td><input type = 'text' value = 'First Name'" + " /></td>");
                      out.print("<td><input type = 'text' id = 'employeeFirstName' name = 'employeeFirstName' value = " + newEmployee.getFirstName() + " /></td>");
                  out.print("</tr>");
                  out.print("<tr>");
                  out.print("<td><input type = 'text' value = 'Last Name'" + " /></td>");
                      out.print("<td><input type = 'text' id = 'employeeLastName' name = 'employeeLastName' value = " + newEmployee.getLastName() + " /></td>");
                  out.print("</tr>");
                  out.print("<tr>");
                      out.print("<td><input type = 'text' value = 'Email Id'" + " /></td>");
                      out.print("<td><input type = 'text' id = 'employeeEmail' name = 'employeeEmail' value = " + newEmployee.getEmailId() + " /></td>");
                  out.print("</tr>");
                  out.print("<tr>");
                      out.print("<td><input type = 'text' value = 'Age'" + " /></td>");
                      out.print("<td><input type = 'number' id = 'employeeAge' name = 'employeeAge' value = " + newEmployee.getAge() + " /></td>");
                  out.print("</tr>");
         out.print("</table>");
         out.print("<center><input type = 'submit' id = 'updateEmployee' name = 'editEmployee' value = 'Update Information' ></center></form>");

     }
}
             