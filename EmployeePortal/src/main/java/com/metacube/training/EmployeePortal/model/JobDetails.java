package com.metacube.training.EmployeePortal.model;

import java.util.Date;

public class JobDetails 
{
    int empCode, totalExperience, jobCode, reportingManager, teamLead, currentProjectId;
    Date dateOfJoining;
    
    public int getEmpCode() {
        return empCode;
    }
    public void setEmpCode(int empCode) {
        this.empCode = empCode;
    }
    public int getTotalExperience() {
        return totalExperience;
    }
    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }
    public int getJobCode() {
        return jobCode;
    }
    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }
    public int getReportingManager() {
        return reportingManager;
    }
    public void setReportingManager(int reportingManager) {
        this.reportingManager = reportingManager;
    }
    public int getTeamLead() {
        return teamLead;
    }
    public void setTeamLead(int teamLead) {
        this.teamLead = teamLead;
    }
    public int getCurrentProjectId() {
        return currentProjectId;
    }
    public void setCurrentProjectId(int currentProjectId) {
        this.currentProjectId = currentProjectId;
    }
    public Date getDateOfJoining() {
        return dateOfJoining;
    }
    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
    
  
}
