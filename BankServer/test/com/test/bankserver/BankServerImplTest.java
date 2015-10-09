package com.test.bankserver;

import com.bankinterface.Constant;
import com.bankserver.BankServerImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yucunli
 */
public class BankServerImplTest {
    
    BankServerImpl implBMO = null;
    BankServerImpl implScotia = null;
    BankServerImpl implTD = null;
    
    public BankServerImplTest() {
        try{
            implBMO = new BankServerImpl(Constant.BANK_BMO_RMI_ID, Constant.BANK_BMO_RMI_PORT);
            implScotia = new BankServerImpl(Constant.BANK_SCOTIA_RMI_ID, Constant.BANK_SCOTIA_RMI_PORT);
            implTD = new BankServerImpl(Constant.BANK_TD_RMI_ID, Constant.BANK_TD_RMI_PORT);
            
            
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

            
        }catch(Exception e){
            System.out.println("Woooooo, Error!!!");
        }
        
    }

    @Test
    public void hello() throws RemoteException {
        
        implBMO.openAccount("BMO", "Y", "A", "a@gmail.com", "123", "pass");
        implBMO.openAccount("BMO", "Y", "B", "b@gmail.com", "123", "pass");
        implBMO.openAccount("BMO", "Y", "C", "c@gmail.com", "123", "pass");
        implBMO.openAccount("BMO", "Y", "D", "d@gmail.com", "123", "pass");
        
        implScotia.openAccount("Scotia", "Y", "A", "a@gmail.com", "123", "pass");
        implScotia.openAccount("Scotia", "Y", "B", "b@gmail.com", "123", "pass");
        implScotia.openAccount("Scotia", "Y", "C", "c@gmail.com", "123", "pass");
        implScotia.openAccount("Scotia", "Y", "D", "d@gmail.com", "123", "pass");
        
        implTD.openAccount("TD", "Y", "A", "a@gmail.com", "123", "pass");
        implTD.openAccount("Scotia", "Y", "B", "b@gmail.com", "123", "pass");
        implTD.openAccount("Scotia", "Y", "C", "c@gmail.com", "123", "pass");
        implTD.openAccount("Scotia", "Y", "D", "d@gmail.com", "123", "pass");
        
        System.out.println(implBMO.getLoan("BMO", "1", "pass", 500));
        System.out.println(implBMO.getLoan("BMO", "2", "pass", 500));
        System.out.println(implBMO.getLoan("BMO", "3", "pass", 300));
        
        System.out.println(implScotia.getLoan("Scotia", "5", "pass", 500));
        System.out.println(implScotia.getLoan("Scotia", "6", "pass", 600));
        System.out.println(implScotia.getLoan("Scotia", "7", "pass", 300));
        
        System.out.println(implTD.getLoan("Scotia", "9", "pass", 500));
        System.out.println(implTD.getLoan("Scotia", "10", "pass", 600));
        System.out.println(implTD.getLoan("Scotia", "11", "pass", 300));
    }
}
