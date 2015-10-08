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
        Registry registry = LocateRegistry.getRegistry("localhost", Constant.BANK_TD_RMI_PORT);
        ManagerInterface lookup = (ManagerInterface) registry.lookup(Constant.BANK_TD_RMI_ID);
        
        System.out.print(lookup.printCustomerInfo("TD"));
    }
    
}
