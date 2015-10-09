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
        BankServerImpl implBMO = new BankServerImpl(Constant.BANK_BMO_RMI_ID, Constant.BANK_BMO_RMI_PORT);;
        BankServerImpl implScotia = new BankServerImpl(Constant.BANK_SCOTIA_RMI_ID, Constant.BANK_SCOTIA_RMI_PORT);
        BankServerImpl implTD = new BankServerImpl(Constant.BANK_TD_RMI_ID, Constant.BANK_TD_RMI_PORT);
        
        Registry registry = LocateRegistry.createRegistry(Constant.BANK_TD_RMI_PORT);
        registry.bind(Constant.BANK_TD_RMI_ID, implTD);
        implTD.start();
        System.out.println(Constant.BANK_TD_RMI_ID + " server start");

        registry = LocateRegistry.createRegistry(Constant.BANK_SCOTIA_RMI_PORT);
        registry.bind(Constant.BANK_SCOTIA_RMI_ID, implScotia);
        implScotia.start();
        System.out.println(Constant.BANK_SCOTIA_RMI_ID + " server start");

        registry = LocateRegistry.createRegistry(Constant.BANK_BMO_RMI_PORT);
        registry.bind(Constant.BANK_BMO_RMI_ID, implBMO);
        implBMO.start();
        System.out.println(Constant.BANK_BMO_RMI_ID + " server start");
    }
}
