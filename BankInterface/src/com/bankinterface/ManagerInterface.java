package com.bankinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author yucunli
 */
public interface ManagerInterface extends Remote{
    
    public void delayPayment(String bank, String loanID, String currentDueDate, String newDueDate) throws RemoteException;
    
    public String printCustomerInfo(String bank) throws RemoteException;
}
