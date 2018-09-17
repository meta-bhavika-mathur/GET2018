package com.metacube.training.EmployeePortal.dao;

public class Queries
{
    public static final String ADD_SKILL = "INSERT INTO Skills(skill_name) " +
                                            "VALUES (?)";
    public static final String ADD_JOB_TITLE = "INSERT INTO JobTitle(job_title) " +
                                               "VALUES (?)";
    public static final String ADD_PROJECT = "INSERT INTO ProjectMaster (description, start_date, end_date, project_logo) " +
                                              "VALUES ('?', '?', '?','?');";    
}
