package com.metacube.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.metacube.training.model.Employee;
import com.metacube.training.model.Job;
import com.metacube.training.model.Project;
import com.metacube.training.model.Skill;
import com.metacube.training.service.EmployeeService;
import com.metacube.training.service.JobService;
import com.metacube.training.service.ProjectService;
import com.metacube.training.service.SkillService;

@Controller
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private JobService jobService;
    @Autowired
    private EmployeeService employeeService;
    
    /**
     *  To go to login page
     * 
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }
    
     /**
      * To direct to admin dash board after successful login, if unsuccessful, go to home page
      * @param userName
      * @param password
      * @return
      */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "email") String userName,
            @RequestParam(value = "password") String password)
    {  
        if((userName == null) || (password == null) || !userName.equals("bhavika@gmail.com") || !password.equals("1234"))
        {
            return "redirect:/";
        }
        return "redirect:/admin/dashboard";
    }
    
     /**
      *  To go to add new project form
      * @param model
      * @return
      */
    @RequestMapping(value = "/addProject", method = RequestMethod.GET)
    public String addProject(Model model)
    {
        model.addAttribute("project", new Project());
        return "admin/addProject";
    }
    
    /**
     *  To add new project into the project master table
     * @param project
     * @return
     */
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    public String addProject(@ModelAttribute("project") Project project)
    {
        if (project.getId() == 0 && project != null)
        {
            projectService.create(project);
        }
        else
        {
            projectService.update(project);
        }
        return "redirect:/admin/showAllProjects";
    }
    
     /**
      *  To delete project from project master table
      * @param id
      * @return
      */
    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(value = "id") String id)
    {
        projectService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllProjects";
    }
     
    /**
     *  To show all projects in the database
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAllProjects", method = RequestMethod.GET)
    public String showAllProjects(Model model)
    {
        model.addAttribute("projects", projectService.getAll());
        return "admin/projects";
    }
    
     /**
      * To update project details
      * @param projectId
      * @param model
      * @return
      */
    @RequestMapping(value = "/updateProject", method = RequestMethod.GET)
    public String updateProject(@RequestParam(value = "id") int projectId,
            Model model)
    {
        Project project = projectService.getById(projectId);
        model.addAttribute("project", project);
        return "admin/addProject";
    }
    
     /**
      *  To go to add employee page
      * @param model
      * @return
      */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addEmployee(Model model)
    {
        model.addAttribute("employee", new Employee());
        return "admin/addEmployee";
    }
    
     /**
     * To add employee into database
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute("employee") Employee employee)
    {
        if (employee.getId() == 0 && employee != null)
        {
            employeeService.create(employee);
        } else
        {
            employeeService.update(employee);
        }
        return "redirect:/admin/showAllEmployees";
    }
     /**
     * To deletes Employee
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
    public String deleteEmployee(@RequestParam(value = "id") String id)
    {
        employeeService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllEmployees";
    }
    
     /**
     * To show all employees
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAllEmployees", method = RequestMethod.GET)
    public String showAllEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.getAll());
        return "admin/employees";
    }
    
     /**
     * To update employee
     * 
     * @param employeeId
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.GET)
    public String updateEmployee(@RequestParam(value = "id") int employeeId,
            Model model)
    {
        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employee", employee);
        return "admin/addEmployee";
    }
    
    
     /**
     * To goto add job form
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/addJob", method = RequestMethod.GET)
    public String addJob(Model model)
    {
        model.addAttribute("job", new Job());
        return "admin/addJob";
    }
    
     /**
     * To add job
     * 
     * @param job
     * @return
     */
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public String addJob(@ModelAttribute("job") Job job)
    {
        if (job.getId() == 0 && job != null)
        {
            jobService.create(job);
        } 
        else
        {
            jobService.update(job);
        }
        return "redirect:/admin/showAllJobs";
    }
    
    @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public String updateJob(Model model)
    {
        model.addAttribute("job", new Job());
        return "admin/updateJob";
    }
    
    @RequestMapping(value = "/updateJob", method = RequestMethod.POST)
    public String updateJob(@RequestParam(value = "id") int jobId,
            Model model)
    {
        /*Job newJob = jobService.getById(jobId);
        model.addAttribute("job", newJob);*/
        return "admin/UpdateJobDetails";
    }
   
  
 
    
   /* // testmethods
    @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public String updateJob(Model model)
    {
        model.addAttribute("job", new Job());
        return "admin/updateJob";
    }*/
    
   /* @RequestMapping(value = "/updateJob", method = RequestMethod.POST)
    public String updateJob(@ModelAttribute("job") Job job)
    { 
        jobService.update(job);
        return "redirect:/admin/showAllJobs";
    }*/
    
    
     /**
     * To delete job information
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    public String deleteJob(@RequestParam(value = "id") String id)
    {
        jobService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllJobs";
    }
     /**
     * To show all jobs
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAllJobs", method = RequestMethod.GET)
    public String showAllJobs(Model model)
    {
        model.addAttribute("jobs", jobService.getAll());
        return "admin/jobs";
    }
     /**
     * To update job information
     * 
     * @param jobId
     * @param model
     * @return
     */
    
/*    @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public String updateJob(@RequestParam(value = "id") int jobId, Model model)
    {
        Job job = jobService.getById(jobId);
        model.addAttribute("job", job);
        return "admin/addJob";
    }*/
   
   /* @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public String updateJob(@RequestParam(value = "id") int jobId, Model model)
    {
        Job job = jobService.getById(jobId);
        model.addAttribute("job", new Job());
        return "admin/addJob";
    }*/
    
     /**
     * To go to skills page
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/addSkill", method = RequestMethod.GET)
    public String addSkill(Model model)
    {
        model.addAttribute("skill", new Skill());
        return "admin/addSkill";
    }
    
     /**
     * To go to create and update skill
     * 
     * @param skill
     * @return
     */
    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public String addSkill(@ModelAttribute("skill") Skill skill)
    {
        if (skill.getId() == 0 && skill != null)
        {
            skillService.create(skill);
        } else
        {
            skillService.update(skill);
        }
        return "redirect:/admin/showAllSkills";
    }
    
     /**
     * To delete skill
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteSkill", method = RequestMethod.POST)
    public String deleteSkill(@RequestParam(value = "id") String id)
    {
        skillService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllSkills";
    }
    
     /**
     * To display all skills from Skills table
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAllSkills", method = RequestMethod.GET)
    public String showAllSkills(Model model)
    {
        model.addAttribute("skills", skillService.getAll());
        return "admin/skills";
    }
    
     /**
     * To update skill
     * 
     * @param skillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateSkill", method = RequestMethod.GET)
    public String updateSkill(@RequestParam(value = "id") int skillId,
            Model model)
    {
        Skill skill = skillService.getById(skillId);
        model.addAttribute("skill", skill);
        return "admin/addSkill";
    }
     /**
     * To goto admin dashboard
     * 
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String gotoDashboard()
    {
        return "admin/dashboard";
    }
    
     /**
     * To log out from admin dashboard
     * 
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout()
    {
        return "redirect:/";
    }
    
    /** To go to search employee page
     * 
     * @return
     */
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.GET)
    public String searchEmployee(Model model)
    {
        return "admin/searchEmployee";
    }   
    
    /** To search employee
     * 
     * @return
     */
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public String searchEmployee(@RequestParam(value = "firstName") String firstName, Model model)
    {
        model.addAttribute("employees", employeeService.getByFirstName(firstName));
        return "admin/employees";
    }
    
    // To search employee on the basis of skills
   /*@RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public String searchEmployeeBySkill(@RequestParam(value = "skill") String skill, Model model)
    {
        model.addAttribute("employees", employeeService.getBySkill(skill));
        return "admin/employees";
    }*/
    /*
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public String searchEmployeeByProject(@RequestParam(value = "firstName") String firstName, Model model)
    {
        model.addAttribute("employees", employeeService.getByFirstName(firstName));
        return "admin/employees";
    }*/
  

}