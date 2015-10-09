package com.bankserver.model;

/**
 *
 * @author yucunli
 */
public class CustomerAccount {
    
    private static int uniqueID = 0;
    
    private String customerAccountNumber;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password; // at least 6 characters
    private int creditLimit;

    public CustomerAccount(String firstName, String lastName, String emailAddress, String password) {
        this.customerAccountNumber = (++uniqueID) + "";
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        
        this.creditLimit = 500;
        
        System.out.println("Customer Account: " + customerAccountNumber + " created");
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    
}
