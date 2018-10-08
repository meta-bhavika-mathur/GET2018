package com.metacube.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.metacube.training.model.Employee;
import com.metacube.training.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;
   
    final static String EXCEPTION_OCCURED_MESSAGE = "Exception Occured!";
     
    /**
     * To go to employee login page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }
    
     /**
     * To logs into employee dashboard
     * @param userName
     * @param password
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "email") String userName , @RequestParam(value = "password") String password)
    {
        if (employeeService.getByEmail(userName) == null || !employeeService.getByEmail(userName).getPassword().equals(password))
        {
            return "redirect:/";
        }
        return "redirect:/employee/dashboard";
    }
    
     /**
     * To searche employee
     * @param id, id of employee to be searched
     * @param model
     */
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public String searchEmployee(@RequestParam(value = "id") int id, Model model)
    {
        model.addAttribute("employees", employeeService.getById(id));
        return "employee/searchEmployee";
    }
    
     /**
     * To go to employee dashboard
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String gotoDashboard()
    {
        return "employee/dashboard";
    }
    
     /**
     * To log out from employee dashboard
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout()
    {
        return "redirect:/";
    }
    
    @RequestMapping(value = "/updateEmployeeInformation", method = RequestMethod.GET)
    public String updateEmployeeInformation()
    {
        return "redirect:/employee/updateEmployeeInformation";
    }
    
    /**
     * To go to forgot password page
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword()
    {
        return "forgotPassword";
    }
}
