package com.metacube.training.EmployeePortal.dao;


public class Queries
{
    public static final String addProject = "INSERT INTO ProjectMaster(description, project_logo) " +
                                              "VALUES (?, ?)";
    public static final String addSkill = "INSERT INTO Skills(skill_name) " +
                                            "VALUES (?)";
    public static String addJobTitle = "INSERT INTO JobTitle(job_title) " +
                                        "VALUES (?)";
    
}


