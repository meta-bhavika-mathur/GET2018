package com.metacube.training.EmployeePortal.model;

public class Address
{
    int employeeCode;
    String empCode, addressLine1, addressLine2, city, state, pincode;
    
    public int getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getEmpCode() {
        return empCode;
    }
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    
}
