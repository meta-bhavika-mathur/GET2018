package com.metacube.training.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metacube.training.dao.SkillDAO;
import com.metacube.training.model.Skill;

@Service
public class SkillService
{
   @Autowired
   private SkillDAO skillDAO;
  
   /**
    * To return skill by id
    */
   public Skill getById(int id)
   {
        return skillDAO.getById(id);
   }
   
    /**
    * To return list of all skills
    */
   public List<Skill> getAll()
   {
       return skillDAO.getAll();
   }
   
    /**
    * To delete skill
    */
   public boolean delete(int id)
   {
       Skill skill = skillDAO.getById(id);
       return skillDAO.delete(skill);
   }
   
    /**
    * To update skill
    */
   public boolean update(Skill skill)
   {
       return skillDAO.update(skill);
   }
   
    /**
    * To add new skill
    */
   public void create(Skill skill)
   {
       skillDAO.create(skill);
   }
}
