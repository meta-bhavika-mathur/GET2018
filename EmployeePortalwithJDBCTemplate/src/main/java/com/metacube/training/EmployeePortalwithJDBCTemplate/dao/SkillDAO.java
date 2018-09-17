package com.metacube.training.EmployeePortalwithJDBCTemplate.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.metacube.training.EmployeePortalwithJDBCTemplate.mapper.SkillMapper;
import com.metacube.training.EmployeePortalwithJDBCTemplate.model.Skill;

@Repository
public class SkillDAO
{
   private JdbcTemplate jdbcTemplate;
    @Autowired
   public SkillDAO(DataSource dataSource)
   {
       jdbcTemplate = new JdbcTemplate(dataSource);
   }
    private final String SQL_FIND_SKILL = "select * from skills where skill_id = ?";
   private final String SQL_DELETE_SKILL = "delete from skills where skill_id = ?";
   private final String SQL_UPDATE_SKILL = "update skills set skill_name = ? where skill_id = ?";
   private final String SQL_GET_ALL = "select * from skills";
   private final String SQL_INSERT_SKILL = "insert into skills(skill_name) values(?)";
    /**
    * returns skill by id
    */
   public Skill getById(int id)
   {
       return jdbcTemplate.queryForObject(SQL_FIND_SKILL, new Object[] { id },
               new SkillMapper());
   }
    /**
    * returns list of all skills
    */
   public List<Skill> getAll()
   {
       return jdbcTemplate.query(SQL_GET_ALL, new SkillMapper());
   }
    /**
    * deletes skill
    */
   public boolean delete(Skill skill)
   {
       return jdbcTemplate.update(SQL_DELETE_SKILL, skill.getId()) > 0;
   }
    /**
    * updates skill
    */
   public boolean update(Skill skill)
   {
       return jdbcTemplate.update(SQL_UPDATE_SKILL, skill.getName(),
               skill.getId()) > 0;
   }
    /**
    * adds skill
    */
   public boolean create(Skill skill)
   {
       return jdbcTemplate.update(SQL_INSERT_SKILL, skill.getName()) > 0;
   }
}