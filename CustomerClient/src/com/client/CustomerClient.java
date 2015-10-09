package com.client;

import com.bankinterface.Constant;
import com.bankinterface.CustomerInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author yucunli
 */
public class CustomerClient {

    public static void main(String[] args) throws RemoteException, NotBoundException{
        
        CustomerClient client = new CustomerClient();
        client.openAccount(Constant.BANK_TD_RMI_ID, "Yucun", "Li", "email@mail.ca", "514444", "123");
        client.getLoan(Constant.BANK_TD_RMI_ID, 1+"", "123", 300);
    }
    
    public void openAccount(String bank, String firstName, String lastName, 
            String email, String phone, String password) throws RemoteException, NotBoundException{
        Registry registry = null;
        CustomerInterface lookup = null;
        
        if(bank.equals(Constant.BANK_SCOTIA_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_SCOTIA_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_SCOTIA_RMI_ID);
        }else if(bank.equals(Constant.BANK_BMO_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_BMO_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_BMO_RMI_ID);
        }else if(bank.equals(Constant.BANK_TD_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        }
        
        System.out.println(lookup.openAccount(bank, firstName, lastName, email, phone, password));
        
    }
    
    public void getLoan(String bank, String accountNumber, String password, int loanAmount) 
            throws RemoteException, NotBoundException{
        Registry registry = null;
        CustomerInterface lookup = null;
        
        if(bank.equals(Constant.BANK_SCOTIA_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_SCOTIA_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_SCOTIA_RMI_ID);
        }else if(bank.equals(Constant.BANK_BMO_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_BMO_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_BMO_RMI_ID);
        }else if(bank.equals(Constant.BANK_TD_RMI_ID)){
            registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
            lookup = (CustomerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        }
        
        System.out.println(lookup.getLoan(bank, accountNumber, password, loanAmount));
    }
    
}
