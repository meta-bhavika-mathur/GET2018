package com.metacube.training.EmployeePortal.service;

import com.metacube.training.EmployeePortal.dao.SkillDAO;
import com.metacube.training.EmployeePortal.model.Skills;

public class SkillService 
{
    SkillDAO skillDAO = new SkillDAO();
    public boolean addSkill(Skills skill)
    {
        return skillDAO.add(skill);
    }
}
