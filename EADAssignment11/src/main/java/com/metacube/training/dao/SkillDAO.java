package com.metacube.training.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.training.mapper.SkillMapper;
import com.metacube.training.model.Job;
import com.metacube.training.model.Project;
import com.metacube.training.model.Skill;

@Repository
@Transactional
public class SkillDAO
{
   private JdbcTemplate jdbcTemplate;
   
   @Autowired
   private SessionFactory sessionFactory;
   
   private final String SQL_DELETE_SKILL = "delete from Skill where skill_id = :id";
   private final String SQL_UPDATE_SKILL = "update Skill set skill_name = :name where skill_id = :id";
   private final String SQL_INSERT_SKILL = "insert into Skill(skill_name) values (:name)";
   
   /**
    * To get skill by id
    */
   public Skill getById(int id)
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Skill.class);
       criteria.add(Restrictions.eq("id", id));
       return (Skill) criteria.uniqueResult();
   }
   
    /**
    * To get list of all skills
    */
   public List<Skill> getAll()
   {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Skill.class);
       return criteria.list();
   }
   
    /**
    * To delete skill
    */
   public boolean delete(Skill skill)
   {
       try
       {
           TypedQuery<Skill> query = sessionFactory.getCurrentSession().createQuery(SQL_DELETE_SKILL);
           query.setParameter("id", skill.getId());
           return query.executeUpdate() > 0;
       } catch (Exception e)
       {
           return false;
       }
   }
  
    /**
    * To update skill information
    */
   public boolean update(Skill skill)
   {
       try
       {
           TypedQuery<Skill> query = sessionFactory.getCurrentSession().createQuery(SQL_UPDATE_SKILL);
           query.setParameter("title", skill.getName()); 
           query.setParameter("id", skill.getId());
           
           return query.executeUpdate() > 0;
       } 
       catch (Exception e)
       {
           return false;
       }
   }
   
    /**
    * To add skill
    */
   public void create(Skill skill)
   {
       sessionFactory.getCurrentSession().save(skill);
   }
}
