package com.client;

import com.bankinterface.Constant;
import com.bankinterface.ManagerInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author yucunli
 */
public class ManagerClient {

    public static void main(String[] args) throws RemoteException, NotBoundException{
        ManagerClient client = new ManagerClient();
        
        client.delayPayment(Constant.BANK_SCOTIA_RMI_ID, 2+"", 200, 300);
        client.printCustomerInfo(Constant.BANK_SCOTIA_RMI_ID);
    }
    
    public void delayPayment(String bank, String loanID, int currentDueDate, int newDueDate) 
            throws RemoteException, NotBoundException{
    
        Registry registry = null;
        ManagerInterface lookup = null;
        
        if(bank.equals(Constant.BANK_SCOTIA_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_SCOTIA_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_SCOTIA_RMI_ID);
        }else if(bank.equals(Constant.BANK_BMO_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_BMO_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_BMO_RMI_ID);
        }else if(bank.equals(Constant.BANK_TD_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        }
        
        System.out.println(lookup.delayPayment(bank, loanID, currentDueDate, newDueDate));
        
    }
    
    
    public void printCustomerInfo(String bank) throws RemoteException, NotBoundException {
        Registry registry = null;
        ManagerInterface lookup = null;
        
        if(bank.equals(Constant.BANK_SCOTIA_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_SCOTIA_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_SCOTIA_RMI_ID);
        }else if(bank.equals(Constant.BANK_BMO_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_BMO_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_BMO_RMI_ID);
        }else if(bank.equals(Constant.BANK_TD_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
            lookup = (ManagerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        }
        
        System.out.println(lookup.printCustomerInfo(bank));
    }
    
}
