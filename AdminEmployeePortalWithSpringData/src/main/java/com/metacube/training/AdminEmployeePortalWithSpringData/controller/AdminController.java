package com.metacube.training.AdminEmployeePortalWithSpringData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.metacube.training.AdminEmployeePortalWithSpringData.model.Employee;
import com.metacube.training.AdminEmployeePortalWithSpringData.model.EmployeeSkillMapping;
import com.metacube.training.AdminEmployeePortalWithSpringData.model.Job;
import com.metacube.training.AdminEmployeePortalWithSpringData.model.JobDetails;
import com.metacube.training.AdminEmployeePortalWithSpringData.model.Project;
import com.metacube.training.AdminEmployeePortalWithSpringData.model.Skill;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.EmployeeService;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.EmployeeSkillMappingService;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.JobDetailsService;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.JobService;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.ProjectService;
import com.metacube.training.AdminEmployeePortalWithSpringData.service.SkillService;

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
    @Autowired
    private EmployeeSkillMappingService employeeSkillMappingService;
    @Autowired
    private JobDetailsService jobDetailsService;

    /**
     * To go to admin login window
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }

    /**
     * To login into admin dashboard
     * @param userName
     * @param password
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
     * To go to add project form
     * @param model
     */
    @RequestMapping(value = "/addProject", method = RequestMethod.GET)
    public String addProject(Model model)
    {
        model.addAttribute("project", new Project());
        return "admin/addProject";
    }

    /**
     * To add project
     * @param project
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
     * To delete project
     * @param id, id of project to be deleted
     */
    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(value = "id") String id)
    {
        projectService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllProjects";
    }

    /**
     * To display all projects
     * @param model
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
     * To go to add employee form
     * @param model
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addEmployee(Model model)
    {
        model.addAttribute("employee", new Employee());
        return "admin/addEmployee";
    }

    /**
     * To add new employee information
     * @param employee, employee to be inserted
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute("employee") Employee employee)
    {
        if (employee.getId() == 0 && employee != null)
            employeeService.create(employee);
        else
            employeeService.update(employee);

        return "redirect:/admin/showAllEmployees";
    }

    /**
     * To delete employee record
     * @param id, id of the project to be deleted
     */
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
    public String deleteEmployee(@RequestParam(value = "id") String id)
    {
        employeeService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllEmployees";
    }

    /**
     * To display all employees 
     * @param model
     */
    @RequestMapping(value = "/showAllEmployees", method = RequestMethod.GET)
    public String showAllEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.getAll());
        return "admin/employees";
    }

    /**
     * To update employee
     * @param employeeId, id of the e mployee whose information is to be updated
     * @param model
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
     * To go to add job information form
     * @param model
     */
    @RequestMapping(value = "/addJob", method = RequestMethod.GET)
    public String addJob(Model model)
    {
        model.addAttribute("job", new Job());
        return "admin/addJob";
    }

    /**
     * To add new job information
     * @param job, job to be inserted in the table
     */
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public String addJob(@ModelAttribute("job") Job job)
    {
        if (job.getId() == 0 && job != null)
            jobService.create(job);
        else
            jobService.update(job);

        return "redirect:/admin/showAllJobs";
    }

    /**
     * To delete job from the table
     * @param id, id of the job to be deleted
     */
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    public String deleteJob(@RequestParam(value = "id") String id)
    {
        jobService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllJobs";
    }

    /**
     * To show all jobs
     * @param model
     */
    @RequestMapping(value = "/showAllJobs", method = RequestMethod.GET)
    public String showAllJobs(Model model)
    {
        model.addAttribute("jobs", jobService.getAll());
        return "admin/jobs";
    }

    /**
     * To update job information
     * @param jobId, id of the job whose information has to be updated
     * @param model
     */
    @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public String updateJob(@RequestParam(value = "id") int jobId, Model model)
    {
        Job job = jobService.getById(jobId);
        model.addAttribute("job", job);
        return "admin/addJob";
    }

    /**
     * To go to add skill page
     * @param model
     */
    @RequestMapping(value = "/addSkill", method = RequestMethod.GET)
    public String addSkill(Model model)
    {
        model.addAttribute("skill", new Skill());
        return "admin/addSkill";
    }

    /**
     * To add new skill
     * @param skill
     */
    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public String addSkill(@ModelAttribute("skill") Skill skill)
    {
        if (skill.getId() == 0 && skill != null)
            skillService.create(skill);
        else
            skillService.update(skill);
        
        return "redirect:/admin/showAllSkills";
    }

    /**
     * To delete skill
     * @param id, id of the skill to be deleted
     */
    @RequestMapping(value = "/deleteSkill", method = RequestMethod.POST)
    public String deleteSkill(@RequestParam(value = "id") String id)
    {
        skillService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllSkills";
    }

    /**
     * To shows all skills
     * @param model
     */
    @RequestMapping(value = "/showAllSkills", method = RequestMethod.GET)
    public String showAllSkills(Model model)
    {
        model.addAttribute("skills", skillService.getAll());
        return "admin/skills";
    }

    /**
     * To update skill
     * @param skillId, id of the skill whose information is to be updated
     * @param model
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
     * Shows list of employees with a particular skill
     * @param skillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEmployeeListWithSameSkill", method = RequestMethod.POST)
    public String getEmployeeList(@RequestParam(value = "id") int skillId,
            Model model)
    {
        model.addAttribute("employees",
                employeeSkillMappingService.getEmployeesWithSameSkill(skillId));
        return "admin/employees";
    }

    /**
     * To go to addSkillToEmployee
     * @param model
     * @return
     */
    @RequestMapping(value = "/addSkillToEmployee", method = RequestMethod.GET)
    public String addSkillToEmployee(Model model)
    {
        model.addAttribute("employeeSkillMapping", new EmployeeSkillMapping());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("skills", skillService.getAll());
        return "admin/addSkillToEmployee";
    }

    /**
     * Adds skill to employee 
     * @param employeeSkillMapping
     */
    @RequestMapping(value = "/addSkillToEmployee", method = RequestMethod.POST)
    public String addSkillToEmployee(
            @ModelAttribute("employeeSkillMapping") EmployeeSkillMapping employeeSkillMapping)
    {
        employeeSkillMappingService.create(employeeSkillMapping);
        return "redirect:/admin/showAllEmployeeSkillMappings";
    }

    /**
     * To show list of all employee skill mappings
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAllEmployeeSkillMappings", method = RequestMethod.GET)
    public String showAllEmployeeSkillMappings(Model model)
    {
        model.addAttribute("employeeSkillMappings",
                employeeSkillMappingService.getAll());
        return "admin/employeeSkillMappings";
    }
    
    /**
     * To go to add job details form
     * @param model
     */
    @RequestMapping(value = "/addJobDetails", method = RequestMethod.GET)
    public String addJobDetails(Model model)
    {
        model.addAttribute("jobDetails", new JobDetails());
        return "admin/addJobDetails";
    }

    /**
     * To add job details
     * @param job
     */
    @RequestMapping(value = "/addJobDetails", method = RequestMethod.POST)
    public String addJobDetails(@ModelAttribute("jobDetails") JobDetails jobDetails)
    {
        if (jobDetails.getId() == 0 && jobDetails != null)
        {
            jobDetailsService.create(jobDetails);
        } else
        {
            jobDetailsService.update(jobDetails);
        }
        return "redirect:/admin/showAllJobDetails";
    }

    /**
     * To delete job details
     * @param id
     */
    @RequestMapping(value = "/deleteJobDetails", method = RequestMethod.POST)
    public String deleteJobDetails(@RequestParam(value = "id") String id)
    {
        jobDetailsService.delete(Integer.parseInt(id));
        return "redirect:/admin/showAllJobDetails";
    }

    /**
     * To shows all jobs
     * @param model
     */
    @RequestMapping(value = "/showAllJobDetails", method = RequestMethod.GET)
    public String showAllJobDetails(Model model)
    {
        model.addAttribute("jobDetails", jobDetailsService.getAll());
        return "admin/jobDetails";
    }

    /**
     * To update job details
     * @param jobId
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateJobDetails", method = RequestMethod.GET)
    public String updateJobDetails(@RequestParam(value = "id") int jobDetailsId, Model model)
    {
        JobDetails jobDetails = jobDetailsService.getById(jobDetailsId);
        model.addAttribute("jobDetails", jobDetails);
        return "admin/addJobDetails";
    }
   
    /**
     * To go to admin dashboard
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String gotoDashboard()
    {
        return "admin/dashboard";
    }

    /**
     * To logout from admin dashboard
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout()
    {
        return "redirect:/";
    }
}
