package com.bankserver.model;

import com.bankinterface.Constant;

/**
 *
 * @author yucunli
 */
public class Loan {
    
    private static int uniqueID = 0;

    private String ID;
    private String customerAccountNumber;
    private int amount;
    private int dueDate;

    public Loan(String customerAccountNumber, int amount) {
        this.ID = (++uniqueID) + "";
        
        this.customerAccountNumber = customerAccountNumber;
        this.amount = amount;
        
        this.dueDate = Constant.DEFAULT_DUEDATE;
        
        System.out.println("Loan :" + ID + " created");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }
    
    
}
