package com.metacube.training.EmployeePortalwithJDBCTemplate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Project;

public class ProjectMapper implements RowMapper<Project>
{
    public Project mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Project project = new Project();
        project.setId(resultSet.getInt("project_id"));
        project.setLogo(resultSet.getString("project_logo"));
        project.setDescription(resultSet.getString("description"));
        project.setStartDate(resultSet.getDate("start_date"));
        project.setEndDate(resultSet.getDate("end_date"));
        return project;
    }
}