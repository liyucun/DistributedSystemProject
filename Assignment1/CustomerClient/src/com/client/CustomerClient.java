package com.client;

import com.bankinterface.Constant;
import com.bankinterface.CustomerInterface;
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
public class CustomerClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, IOException{
        
        CustomerClient client = new CustomerClient();
        //client.openAccount(Constant.BANK_TD_RMI_ID, "Yucun", "Li", "email@mail.ca", "514444", "123");
        //client.getLoan(Constant.BANK_TD_RMI_ID, 1+"", "123", 300);
        
        //client.openAccount(Constant.BANK_SCOTIA_RMI_ID, "Yucun", "Li", "email@mail.ca", "514444", "123");
        //client.getLoan(Constant.BANK_SCOTIA_RMI_ID, 2+"", "123", 300);
        System.out.println("Welcome to our bank system customer client!");
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
    
    public void console(){
        System.out.println("Here are two available operation you can do on our system:");
        System.out.println("1. Open Account");
        System.out.println("2. Get Loan");
        
        try{
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
                
                System.out.println("Please enter your information(FirstName,LastName,Email,Phone,Password)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                openAccount(bank, infoArray[0], infoArray[1], infoArray[2], infoArray[3], infoArray[4]);
                
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
                
                System.out.println("Please enter your information(AccountNumber,Password,LoanAmount)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                getLoan(bank, infoArray[0], infoArray[1], Integer.parseInt(infoArray[2]));
                
            }else{
                System.out.println("Illegal Input, please choice 1 or 2");
            }
	}
	catch(IOException e)
	{
            System.out.println(e.toString());
	}
        catch(Exception e){
            System.out.println(e.toString());
        }
        
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
