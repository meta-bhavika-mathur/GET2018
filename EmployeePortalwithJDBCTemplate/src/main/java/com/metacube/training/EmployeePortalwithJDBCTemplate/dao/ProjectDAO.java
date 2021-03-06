package com.metacube.training.EmployeePortalwithJDBCTemplate.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.metacube.training.EmployeePortalwithJDBCTemplate.mapper.ProjectMapper;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Project;

@Repository
public class ProjectDAO
{
   private JdbcTemplate jdbcTemplate;
   
   @Autowired
   public ProjectDAO(DataSource dataSource)
   {
       jdbcTemplate = new JdbcTemplate(dataSource);
   }
   
   private final String SQL_FIND_PROJECT = "select * from projectmaster where project_id = ?";
   private final String SQL_DELETE_PROJECT = "delete from projectmaster where project_id = ?";
   private final String SQL_UPDATE_PROJECT = "update projectmaster set project_logo = ?, description = ?, start_date  = ?, end_date  = ? where project_id = ?";
   private final String SQL_GET_ALL = "select * from projectmaster";
   private final String SQL_INSERT_PROJECT = "insert into projectmaster(project_logo, description, start_date, end_date) values(?,?,?,?)";
    /**
    * returns project by id
    */
   public Project getById(int id)
   {
       return jdbcTemplate.queryForObject(SQL_FIND_PROJECT,
               new Object[] { id }, new ProjectMapper());
   }
    /**
    * returns list of all projects
    */
   public List<Project> getAll()
   {
       return jdbcTemplate.query(SQL_GET_ALL, new ProjectMapper());
   }
    /**
    * deletes project
    */
   public boolean delete(Project project)
   {
       return jdbcTemplate.update(SQL_DELETE_PROJECT, project.getId()) > 0;
   }
    /**
    * updates project
    */
   public boolean update(Project project)
   {
       return jdbcTemplate.update(SQL_UPDATE_PROJECT, project.getLogo(),
               project.getDescription(), project.getStartDate(),
               project.getEndDate(), project.getId()) > 0;
   }
    /**
    * adds project
    */
   public boolean create(Project project)
   {
       return jdbcTemplate.update(SQL_INSERT_PROJECT, project.getLogo(),
               project.getDescription(), project.getStartDate(),
               project.getEndDate()) > 0;
   }
} 