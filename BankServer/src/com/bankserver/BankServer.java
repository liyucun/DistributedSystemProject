package com.bankserver;

import com.bankinterface.Constant;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author yucunli
 */
public class BankServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException{
        BankServerImpl impl = new BankServerImpl();
        Registry registry = LocateRegistry.createRegistry(Constant.BANK_TD_RMI_PORT);
        registry.bind(Constant.BANK_TD_RMI_ID, impl);
        System.out.println("server start");
    }
}
