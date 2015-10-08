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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
        CustomerInterface lookup = (CustomerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        System.out.println(lookup.openAccount("Bank Name", "First name", "Last name", 
                "my email", "5144308601", "password"));
        System.out.println(lookup.openAccount("Bank Name", "First name", "Last name", 
                "my email", "5144308601", "password"));
        
    }
    
}
