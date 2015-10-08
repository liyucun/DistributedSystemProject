package com.bankserver;

import com.bankinterface.Constant;
import com.bankinterface.CustomerInterface;
import com.bankinterface.ManagerInterface;
import com.bankserver.model.CustomerAccount;
import com.bankserver.model.Loan;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author yucunli
 */
public class BankServerImpl extends UnicastRemoteObject implements CustomerInterface, ManagerInterface{

    HashMap<String, ArrayList<CustomerAccount>> customerAccount_HashMap;
    HashMap<String, Loan> loan_HashMap;
    
    
    
    public BankServerImpl() throws RemoteException{
        super();
        
        customerAccount_HashMap = new HashMap<String, ArrayList<CustomerAccount>>();
        loan_HashMap = new HashMap<String, Loan>();
        
        for(String ch : Constant.ALPHABET){
            customerAccount_HashMap.put(ch, new ArrayList<CustomerAccount>());
        }
        
    }

    @Override
    public String openAccount(String bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * the server associated with the bank (Bank) attempts to create an account 
         * with the information passed if the customer does not already have an account 
         * by inserting the account at the appropriate location in the hash table.The server 
         * returns the created account number to the customer.
         */
        
        boolean foundAccount = false;
        ArrayList<CustomerAccount> list = customerAccount_HashMap.get(lastName.toLowerCase().substring(0, 1));
        for(CustomerAccount account : list){
            if(account.getEmailAddress().equals(emailAddress)){
                foundAccount = true;
            }
        }
        
        if(!foundAccount){
            CustomerAccount account = new CustomerAccount(firstName, lastName, emailAddress, password);
            list.add(account);
            
            return account.getCustomerAccountNumber();
        }else{
            return "Your email "+ emailAddress + " has been used by another account";
        }
        
    }

    @Override
    public boolean getLoan(String bank, String accountNumber, String password, int loanAmount) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * If the account exists and if the outstanding loans at all the banks do not exceed 
         * the customer’s credit limit then the specified bank server (Bank) issues to loan 
         * by creating an appropriate loan object.A bank finds out whether the customer has 
         * outstanding loans in the other banks using UDP/IP messages.
         */ 
        
        
        
        return true;
    }

    @Override
    public void delayPayment(String bank, String loanID, String currentDueDate, String newDueDate) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * When Manager runs the delayPayment though the ManagerClient program, 
         * the specified bank (Bank) server modifies the due date of the specified loan (LoanID) 
         * in that bank from the CurrentDueDate to the NewDueDate.The manager File 
         * is updated with this information.
         */
        
        // need login process?
        
        Loan loan = loan_HashMap.get(loanID);
        if(loan != null){
            loan.setDueDate(newDueDate);
        }
        
        
    }

    @Override
    public String printCustomerInfo(String bank) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * When Manager runs the printCustomerInfo though the ManagerClient program, 
         * the specified bank (Bank) server displays the complete information about 
         * all customer accounts and loans in that bank on the console.
         */
        
        StringBuilder result = new StringBuilder();
        result.append("CustomerAccount Info:"+"\n");
        
        for(String ch : Constant.ALPHABET){
            ArrayList<CustomerAccount> list = customerAccount_HashMap.get(ch);
            for(CustomerAccount account : list){
                result.append(account.getCustomerAccountNumber() + " "
                                + account.getFirstName() + " " + account.getLastName()
                                + account.getEmailAddress() + " " + account.getPhoneNumber()
                                + " credit limit: " + account.getCreditLimit() + "\n");
            }
            
        }
        
        result.append("Loan Info:"+"\n");
        for(Loan loan : loan_HashMap.values()){
            result.append(loan.getID() + " " + loan.getCustomerAccountNumber()
                            + " " + loan.getDueDate() + " " + loan.getAmount());
        }
        
        return result.toString();
    }
}
