package com.metacube.training.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.metacube.training.model.Job;

public class JobMapper implements RowMapper<Job>
{
    @Override
    public Job mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Job job = new Job();
        job.setId(resultSet.getInt("job_code"));
        job.setTitle(resultSet.getString("job_title"));
        return job;
    }
}