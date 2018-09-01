package com.model;

import java.util.Date;

public class User 
{
    int userId, age;
    String firstName, lastName, emailId, password, organization, image, phoneNumber;
    Date dateOfBirth;
    
    // Constructor for user with user id known
    public User(int userId, String firstName, String lastName, String emailId, String passwword, String organisation, int age, Date dateOfBirth, String image, String phoneNumber)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = passwword;
        this.organization = organisation;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
    
    // Constructor of User without user id
    public User(String firstName, String lastName, String emailId, String passwword, String organisation, int age,  Date dateOfBirth, String image, String phoneNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = passwword;
        this.organization = organisation;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
    
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public String getFirstName() {
        return firstName;
        
    }
    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }
    public String getLastName() 
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getEmailId()
    {
        return emailId;
    }
    public void setEmailId(String emailId) 
    {
        this.emailId = emailId;
    }
    public String getPassword() 
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getOrganization()
    {
        return organization;
    }
    public void setOrganization(String organization) 
    {
        this.organization = organization;
    }
    public String getImage()
    {
        return image;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }
    public Date getDateOfBirth() 
    {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) 
    {
        this.dateOfBirth = dateOfBirth;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getMobileNumber() 
    {
        return phoneNumber;
    }
    
    public void setMobileNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}

