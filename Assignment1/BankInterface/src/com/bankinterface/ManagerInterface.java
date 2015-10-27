package com.bankinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author yucunli
 */
public interface ManagerInterface extends Remote{
    
    public String delayPayment(String bank, String loanID, int currentDueDate, int newDueDate) throws RemoteException;
    
    public String printCustomerInfo(String bank) throws RemoteException;
}
