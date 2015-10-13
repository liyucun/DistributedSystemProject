package com.client;

import com.bankinterface.Constant;
import com.bankinterface.ManagerInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author yucunli
 */
public class ManagerClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, IOException{
        ManagerClient client = new ManagerClient();
        
        //client.delayPayment(Constant.BANK_SCOTIA_RMI_ID, 2+"", 200, 300);
        //client.printCustomerInfo(Constant.BANK_SCOTIA_RMI_ID);
        System.out.println("Welcome to our bank system manager client!");
        boolean login = true;
        while(login){
            client.console();
            System.out.println("Do you have any other operation to do?\n1. Yes\n2. No");
            
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String choice = bufferRead.readLine();
            
            if(choice.equals("2")){
                login = false;
                System.out.println("Have a nice day!");
            }
            System.out.println("---------------------------");
        }
    }
    
    public void console() throws IOException, RemoteException, NotBoundException{
        System.out.println("Here are two available operation you can do on our system:");
        System.out.println("1. Delay Payment");
        System.out.println("2. Print Customer Info");
        
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	String choice = bufferRead.readLine();
        
        if(choice.equals("1")){
            System.out.println("Please choose a bank:");
            System.out.println("1. " + Constant.BANK_BMO_RMI_ID + "\n2. "+Constant.BANK_SCOTIA_RMI_ID
            +"\n3. "+Constant.BANK_TD_RMI_ID);

            bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String bank = bufferRead.readLine();

            if(bank.equals("1")){
                bank = Constant.BANK_BMO_RMI_ID;
            }else if(bank.equals("2")){
                bank = Constant.BANK_SCOTIA_RMI_ID;
            }else if(bank.equals("3")){
                bank = Constant.BANK_TD_RMI_ID;
            }else{
                System.out.println("Illegal Input, please choice 1 or 2 or 3");
                return;
            }
            
            System.out.println("Please enter your information(LoanID,CurrentDueDate,NewDueDate)");
            bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String info = bufferRead.readLine();
            String[] infoArray = info.split(",");
            
            delayPayment(bank, infoArray[0], Integer.parseInt(infoArray[1]), Integer.parseInt(infoArray[2]));
            
        }else if(choice.equals("2")){
            System.out.println("Please choose a bank:");
            System.out.println("1. " + Constant.BANK_BMO_RMI_ID + "\n2. "+Constant.BANK_SCOTIA_RMI_ID
            +"\n3. "+Constant.BANK_TD_RMI_ID);

            bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String bank = bufferRead.readLine();

            if(bank.equals("1")){
                bank = Constant.BANK_BMO_RMI_ID;
            }else if(bank.equals("2")){
                bank = Constant.BANK_SCOTIA_RMI_ID;
            }else if(bank.equals("3")){
                bank = Constant.BANK_TD_RMI_ID;
            }else{
                System.out.println("Illegal Input, please choice 1 or 2 or 3");
                return;
            }
            
            printCustomerInfo(bank);
            
        }else{
            System.out.println("Illegal Input, please choice 1 or 2");
        }
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
