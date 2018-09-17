package com.metacube.training.EmployeePortal.model;

import java.util.Date;

public class Employee 
{
    int employeeCode;
    String firstName, secondName, lastName, gender, primaryContactNumber, secondaryContactNumber;
    String emailId, skypeId, profilePicture, password;
    Date dob;
    boolean enabled;
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }
    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }
    public String getSecondaryContactNumber() {
        return secondaryContactNumber;
    }
    public void setSecondaryContactNumber(String secondaryContactNumber) {
        this.secondaryContactNumber = secondaryContactNumber;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getSkypeId() {
        return skypeId;
    }
    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public int getEmployeeCode() {
        return employeeCode;
    }
    
}
