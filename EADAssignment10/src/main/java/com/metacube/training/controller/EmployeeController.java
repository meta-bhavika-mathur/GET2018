package com.metacube.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metacube.training.model.Employee;
import com.metacube.training.service.EmailService;
import com.metacube.training.service.EmployeeService;
import javax.mail.MessagingException;


@Controller
@RequestMapping("/employee")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmailService emailService;
    
    final static String EXCEPTION_OCCURED_MESSAGE = "Exception Occured!";
     /**
     * goto employee login window
     * 
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }
     /**
     * logs into employee dashboard
     * 
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "email") String userName,
            @RequestParam(value = "password") String password)
    {
        if (employeeService.getByEmail(userName) == null
                || !employeeService.getByEmail(userName).getPassword()
                        .equals(password))
        {
            return "redirect:/";
        }
        return "redirect:/employee/dashboard";
    }
     /**
     * searches employee
     * 
     * @param id
     *            employee id
     * @param model
     * @return
     */
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public String searchEmployee(@RequestParam(value = "id") int id, Model model)
    {
        model.addAttribute("employees", employeeService.getById(id));
        return "employee/searchEmployee";
    }
     /**
     * goto employee dashboard
     * 
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String gotoDashboard()
    {
        return "employee/dashboard";
    }
     /**
     * logs out from employee dashboard
     * 
     * @return
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
     * goto forgot password page
     * @return
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword()
    {
        return "forgotPassword";
    }
     /**
     * Sets default password and sends it to the email address
     * @param email
     * @return
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@RequestParam(value = "email") String email, ModelAndView modelAndView)
    {
        Employee employee = employeeService.getByEmail(email);
        if (employee != null)
        {
            try
            {
                employee.setPassword("xyz");
                employeeService.update(employee);
                emailService.sendEmail(email, "forgot password", "Password updated! \nNew Password: "
                                       + employee.getPassword());
                modelAndView.addObject("message", "Email sent.");
            } 
            catch (Exception me)
            {
                System.out.println(EXCEPTION_OCCURED_MESSAGE);
            }
        }
        else
        {
            modelAndView.addObject("message", "Email not present!");
            
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }
 }