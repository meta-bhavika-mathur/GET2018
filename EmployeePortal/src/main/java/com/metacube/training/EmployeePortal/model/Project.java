package com.metacube.training.EmployeePortal.model;

import java.util.Date;

public class Project 
{
    int id;
    String description;
    Date startDate, endDate;
    String projecLogo;
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getProjecLogo() {
        return projecLogo;
    }
    public void setProjecLogo(String projecLogo) {
        this.projecLogo = projecLogo;
    }
    public int getId() {
        return id;
    }
   
}
