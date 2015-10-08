package com.bankinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author yucunli
 */
public interface CustomerInterface extends Remote{
    
    public String openAccount(String bank, String firstName, String lastName, 
            String emailAddress, String phoneNumber, String password) throws RemoteException;
    
    public String getLoan(String bank, String accountNumber, String password, int loanAmount) throws RemoteException;
}
