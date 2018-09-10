package com.metacube.training.EmployeePortal.service;

import com.metacube.training.EmployeePortal.dao.SkillDAO;
import com.metacube.training.EmployeePortal.model.Skills;

public class EmployeeService 
{
    public boolean add(Skills skill)
    {
        SkillDAO skillDAO = new SkillDAO();
        boolean isAdded =  skillDAO.add(skill);
        return isAdded;
    }
}
