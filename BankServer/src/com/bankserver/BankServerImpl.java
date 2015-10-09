package com.bankserver;

import com.bankinterface.Constant;
import com.bankinterface.CreditLimitState;
import com.bankinterface.CustomerInterface;
import com.bankinterface.ManagerInterface;
import com.bankserver.model.CustomerAccount;
import com.bankserver.model.Loan;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author yucunli
 */
public class BankServerImpl extends UnicastRemoteObject implements CustomerInterface, ManagerInterface, Runnable{

    HashMap<String, ArrayList<CustomerAccount>> customerAccount_HashMap;
    HashMap<String, CustomerAccount> customerAccount_HashMap_Internal;
    HashMap<String, Loan> loan_HashMap;
    
    private int bank_server_rmi_port;
    private String bank_server_rmi_id;
    
    public BankServerImpl(String bank_server_rmi_id, int bank_server_rmi_port) throws RemoteException{
        super();
        
        this.bank_server_rmi_id = bank_server_rmi_id;
        this.bank_server_rmi_port = bank_server_rmi_port;
        
        customerAccount_HashMap = new HashMap<String, ArrayList<CustomerAccount>>();
        loan_HashMap = new HashMap<String, Loan>();
        customerAccount_HashMap_Internal = new HashMap<String, CustomerAccount>();
        
        for(String ch : Constant.ALPHABET){
            customerAccount_HashMap.put(ch, new ArrayList<CustomerAccount>());
        }
        
    }

    @Override
    public String openAccount(String bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * the server associated with the bank (Bank) attempts to create an account 
         * with the information passed if the customer does not already have an account 
         * by inserting the account at the appropriate location in the hash table.The server 
         * returns the created account number to the customer.
         */
        
        boolean foundAccount = false;
        ArrayList<CustomerAccount> list = customerAccount_HashMap.get(lastName.toLowerCase().substring(0, 1));
        for(CustomerAccount account : list){
            if(account.getEmailAddress().equals(emailAddress)){
                foundAccount = true;
            }
        }
        
        if(!foundAccount){
            CustomerAccount account = new CustomerAccount(firstName, lastName, emailAddress, phoneNumber, password);
            list.add(account);
            
            customerAccount_HashMap_Internal.put(account.getCustomerAccountNumber(), account);
            
            return account.getCustomerAccountNumber();
        }else{
            return "Your email "+ emailAddress + " has been used by another account";
        }
        
    }

    @Override
    public String getLoan(String bank, String accountNumber, String password, int loanAmount) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * If the account exists and if the outstanding loans at all the banks do not exceed 
         * the customer’s credit limit then the specified bank server (Bank) issues to loan 
         * by creating an appropriate loan object.A bank finds out whether the customer has 
         * outstanding loans in the other banks using UDP/IP messages.
         */ 
        
        String result = bank_server_rmi_id + ": getLoan() has been called";
        
        // check if account is existed
        boolean foundAccount = customerAccount_HashMap_Internal.get(accountNumber) == null ? false : true;
        
        if(foundAccount){
            CustomerAccount account = customerAccount_HashMap_Internal.get(accountNumber);
            if(account.getPassword().equals(password)){
                
                ArrayList<Integer> other_bank_servers = new ArrayList<Integer>();
                if(bank_server_rmi_id != Constant.BANK_BMO_RMI_ID){
                    other_bank_servers.add(Constant.BANK_BMO_RMI_PORT);
                }
                
                if(bank_server_rmi_id != Constant.BANK_SCOTIA_RMI_ID){
                    other_bank_servers.add(Constant.BANK_SCOTIA_RMI_PORT);
                }
                
                if(bank_server_rmi_id != Constant.BANK_TD_RMI_ID){
                    other_bank_servers.add(Constant.BANK_TD_RMI_PORT);
                }
                
                boolean isExceedLimit = false;
                
                for(Integer port : other_bank_servers){
                    try{
                        DatagramSocket clientSocket = new DatagramSocket();
                        InetAddress IPAddress = InetAddress.getByName("localhost");
                        byte[] sendData = new byte[1024];
                        byte[] receiveData = new byte[1024];
                        
                        sendData = (account.getLastName() + Constant.SEPERATOR + account.getEmailAddress() + Constant.SEPERATOR).getBytes();
                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        clientSocket.send(sendPacket);
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(receivePacket);
                        String reply = new String(receivePacket.getData());
                        clientSocket.close();
                        
                        String[] reply_array = reply.split(Constant.SEPERATOR);
                        if(reply_array[0].equals(CreditLimitState.EXCEED_LIMIT.toString())){
                            isExceedLimit = true;
                            break;
                        }
                        
                    }catch(Exception e){
                        System.out.println("**********************");
                        System.out.println(bank_server_rmi_id + "getLoan");
                        System.out.println("**********************");
                        System.out.println(e.toString());
                    }
                }
                
                if(!isExceedLimit){
                    
                    if(account.getCreditLimit() >= 0){
                        // get loan from bank
                        account.setCreditLimit(account.getCreditLimit() - loanAmount);
                        Loan loan = new Loan(account.getCustomerAccountNumber(), loanAmount);
                        loan_HashMap.put(loan.getID(), loan);
                        
                        result = bank_server_rmi_id + ": " + account.getFirstName() + " "
                                + account.getLastName() + " We have successfully dealt with your loan!!! Amazing job!!!";
                    }else{
                        result = bank_server_rmi_id + ": " + "Your rest credit is negative, please give Yucun cash ASAP!";
                    }
                    
                }else{
                    result = bank_server_rmi_id + ": " + account.getFirstName() + " "
                            + account.getLastName() + " Sorry, your credit limit is exceeded bank limit"
                            + ", please contact with manager Mr.Yucun";
                }
                
            }else{
                result = bank_server_rmi_id + ": Your password is wrong, please check another one";
            }
        }else{
            result = bank_server_rmi_id + ": Can not find your account in our bank";
        }
        
        
        return result;
    }

    @Override
    public void delayPayment(String bank, String loanID, int currentDueDate, int newDueDate) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * When Manager runs the delayPayment though the ManagerClient program, 
         * the specified bank (Bank) server modifies the due date of the specified loan (LoanID) 
         * in that bank from the CurrentDueDate to the NewDueDate.The manager File 
         * is updated with this information.
         */
        
        // need login process?
        
        Loan loan = loan_HashMap.get(loanID);
        if(loan != null){
            loan.setDueDate(newDueDate);
        }
        
        
    }

    @Override
    public String printCustomerInfo(String bank) throws RemoteException {
        // server side do not need know bank name
        
        /**
         * When Manager runs the printCustomerInfo though the ManagerClient program, 
         * the specified bank (Bank) server displays the complete information about 
         * all customer accounts and loans in that bank on the console.
         */
        
        StringBuilder result = new StringBuilder();
        result.append("CustomerAccount Info:"+"\n");
        result.append("ID FIrstName LastName Email Phone Credit"+"\n");
        for(String ch : Constant.ALPHABET){
            ArrayList<CustomerAccount> list = customerAccount_HashMap.get(ch);
            for(CustomerAccount account : list){
                result.append(account.getCustomerAccountNumber() + " "
                                + account.getFirstName() + " " + account.getLastName() + " "
                                + account.getEmailAddress() + " " + account.getPhoneNumber()
                                + " " + account.getCreditLimit() + "\n");
            }
            
        }
        
        result.append("Loan Info:"+"\n");
        result.append("ID CustomerID DueDate LoanAmount"+"\n");
        for(Loan loan : loan_HashMap.values()){
            result.append(loan.getID() + " " + loan.getCustomerAccountNumber()
                            + " " + loan.getDueDate() + " days left" + " " + loan.getAmount() + "\n");
        }
        
        return result.toString();
    }
    
    /** Start background thread to track bank operations. */
    public void start() {
            new Thread(this).start();
    }

    @Override
    public void run() {
        
        try{
            
            DatagramSocket serverSocket = new DatagramSocket(bank_server_rmi_port);
        
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                String[] account_info = sentence.split(Constant.SEPERATOR);
                ArrayList<CustomerAccount> list = customerAccount_HashMap.get(account_info[0].toLowerCase().substring(0, 1));
                for(CustomerAccount account : list){
                    
                    
                    
                    if(account.getEmailAddress().equals(account_info[1])){
                        // if its limit is not below 0
                        if(account.getCreditLimit() >= 0){
                            sendData = (CreditLimitState.NOT_EXCEED_LIMIT + Constant.SEPERATOR).toString().getBytes();
                        }else{
                            sendData = (CreditLimitState.EXCEED_LIMIT + Constant.SEPERATOR).toString().getBytes();
                        }
                        
                    }
                }
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
            
        }catch(Exception e){
            System.out.println("Server "+bank_server_rmi_port+" has problem!!!!");
            System.out.println(e.toString());
        }
        
    }
}
