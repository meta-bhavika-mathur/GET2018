package com.metacube.training.EmployeePortal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metacube.training.EmployeePortal.model.JobTitle;
import com.metacube.training.EmployeePortal.model.Project;
import com.metacube.training.EmployeePortal.model.Skills;
import com.metacube.training.EmployeePortal.service.JobService;
import com.metacube.training.EmployeePortal.service.ProjectService;
import com.metacube.training.EmployeePortal.service.SkillService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "email") String userName,
            @RequestParam(value = "password") String password)
    {
        System.out.println(userName);
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String addProject()
    {
        return "admin/project";
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ModelAndView addProject(@RequestParam(value = "projectDescription") String projectDescription,
                             @RequestParam(value = "projectStartDate") String projectStartDate,
                             @RequestParam(value = "projectEndDate") String projectEndDate,
                             @RequestParam(value = "imageUrl") String imageUrl) throws ParseException
    {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");  
        Date date1, date2;
        try 
        {
            date1 = formatter1.parse(projectStartDate);
            date2 = formatter1.parse(projectEndDate); 
            Project newProject = new Project(projectDescription, date1, date2, imageUrl);
            
            ProjectService projectService = new ProjectService();
            projectService.addProjectDetails(newProject);     
        } 
        catch (ParseException e) 
        {          
            e.printStackTrace();
        } 
        return new ModelAndView("redirect:/admin/dashboard"); 
    }

    @RequestMapping(value = "/addJob", method = RequestMethod.GET)
    public String addJob()
    {
        return "admin/job";
    }

    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public ModelAndView addJob(@RequestParam(value = "jobTitle") String jobTitle)
    {
       JobTitle newJob = new JobTitle();
       newJob.setJobTitle(jobTitle);
       
       JobService jobservice = new JobService();
       jobservice.addJobTitle(newJob);
       
       return new ModelAndView("admin/dashboard");
    }

    @RequestMapping(value = "/addSkill", method = RequestMethod.GET)
    public String addSkill()
    {
        return "admin/skills";
    }

    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public ModelAndView addSkill(@RequestParam(value = "skillName") String skillName)
    {
        Skills skill = new Skills();
        skill.setName(skillName);
           
        SkillService skillService = new SkillService();
        skillService.addSkill(skill);
           
        return new ModelAndView("admin/dashboard");
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addEmployee()
    {
        return "admin/addEmployee";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "middleName") String middleName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "dateOfBirth") Date dateOfBirth,
            @RequestParam(value = "gender") String gender,
            @RequestParam(value = "dateOfJoining") Date dateOfJoining,
            @RequestParam(value = "reportingManager") String reportingManager,
            @RequestParam(value = "teamLead") String teamLead,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password)
    {
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String gotoDashboard()
    {
        return "admin/dashboard";
    }
    
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.GET)
    public String searchEmployee()
    {
        return "admin/searchEmployee";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout()
    {
        return "redirect:/";
    }

    @RequestMapping(value = "/addProject", method = RequestMethod.GET)
    public String addProject(Model model)
    {
       model.addAttribute("project", new Project());
       return "admin/addProject";
    }
}
