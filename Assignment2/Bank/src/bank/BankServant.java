package bank;

import BankApp.Account;
import BankApp.ClientPOA;
import BankApp.Loan;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import org.omg.CORBA.ORB;

/**
 *
 * @author yucunli
 */
public class BankServant extends ClientPOA{
    
    private static final int DEFAULT_CREDIT = 500;
    private static final int DEFAULT_DUEDATE = 100;
    
    HashMap<String, ArrayList<Account>> account_HashMap;
    HashMap<String, Loan> loan_HashMap;

    private ORB orb;
    
    public int port;
    public int[] rest_port;
    public static int uniqueAccountID = 1;
    public static int loanID = 1;

    public BankServant(ORB orb) {
        this.orb = orb;
        account_HashMap = new HashMap<String, ArrayList<Account>>();
        loan_HashMap = new HashMap<String, Loan>();
        
        BankAsReceiver receiver = new BankAsReceiver();
        receiver.start();
    }

    @Override
    public Account openAccount(String bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        Account account = null;
        
        boolean foundAccount = false;
        ArrayList<Account> list = account_HashMap.get(lastName.toLowerCase().substring(0, 1));
        for(Account temp : list){
            if(temp.emailAddress.equals(emailAddress)){
                foundAccount = true;
            }
        }
        
        if(!foundAccount){
            account = new Account(""+uniqueAccountID++, firstName, lastName, emailAddress, phoneNumber, password, DEFAULT_CREDIT);
            list.add(account);
            
            //log(firstName + lastName + " " + " create account : " + account.accountNumber);
            //logCustomer(account.getCustomerAccountNumber(), "account created");
            
        }
        
        return account;
    }

    @Override
    public Loan getLoan(String bank, String accountNumber, String password, int loanAmount) {
        
        Account foundAccount = null;
        Loan loan = null;
        
        for(ArrayList<Account> account_list : account_HashMap.values()){
            for(Account account : account_list){
                if(account.accountNumber.equals(accountNumber)){
                    foundAccount = account;
                }
            }
        }
        
        if(foundAccount != null && foundAccount.password.equals(password)){
            
            boolean isExceedLimit = false;
            
            BankAsClient client0 = new BankAsClient(foundAccount.lastName, foundAccount.emailAddress, rest_port[0]);
            BankAsClient client1 = new BankAsClient(foundAccount.lastName, foundAccount.emailAddress, rest_port[1]);
            
            client0.start();
            client1.start();
            client0.join();
            client1.join();
            
            if(client0.getResult() && client1.getResult()){
                loan = new Loan();
                loan.ID = "" + loanID++;
                loan.accountNumber = accountNumber;
                loan.amount = loanAmount;
                loan.dueDate = DEFAULT_DUEDATE;
                
                loan_HashMap.put(loan.ID, loan);
                
                synchronized(foundAccount){
                    foundAccount.creditLimit -= loanAmount;
                }
            }
        }
        
        return loan;
    }

    @Override
    public String transferLoan(String loanID, String currentBank, String otherBank) {
        
        if(loan_HashMap.get(loanID) == null){
            return "Not found loan";
        }
        
        Loan loan = loan_HashMap.get(loanID);
        
        Thread transferThread = new Thread(){
            
            @Override
            public void run(){
                
                //send loan information to specific server
                //receive result from them
                
                
            }
            
        };
        
        transferThread.start();
        
        try{
            transferThread.join();
        }catch(Exception ex){
        
        }
        // return result Yes/True
        // operate on local database
        
        //if operation done well
        //if not well -> roll back
        
        return "";
    }

    @Override
    public String delayPayment(String bank, String loanID, int currentDueDate, int newDueDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String printCustomerInfo(String bank) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    class BankAsClient implements Runnable{

        private volatile boolean isExceedLimit = false;
        private String lastName;
        private String emailAddress;
        private int otherBankPort;
        
        public BankAsClient(String lastName, String emailAddress, int otherBankPort){
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.otherBankPort = otherBankPort;
        }
        
        public void start(){
            new Thread(this).start();
        }
        
        public void join(){
            this.join();
        }
        
        public boolean getResult(){
            return isExceedLimit;
        }
        
        @Override
        public void run() {
            try{
                    DatagramSocket clientSocket = new DatagramSocket();
                    InetAddress IPAddress = InetAddress.getByName("localhost");
                    byte[] sendData = new byte[1024];
                    byte[] receiveData = new byte[1024];

                    sendData = (lastName + "," + emailAddress + ",").getBytes();

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, otherBankPort);
                    clientSocket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String reply = new String(receivePacket.getData());
                    clientSocket.close();

                    String[] reply_array = reply.split(",");
                    if(reply_array[0].equals("Exceed")){
                        isExceedLimit = true; 
                    }

                }catch(Exception e){
                    System.out.println("**********************");
                    System.out.println("getLoan");
                    System.out.println("**********************");
                    System.out.println(e.toString());
                }
        }
    
    };
    
    class BankAsReceiver implements Runnable{
        
        public void start(){
            new Thread(this).start();
        }
        
        public void join(){
            this.join();
        }

        @Override
        public void run() {
            try{
            
                DatagramSocket serverSocket = new DatagramSocket(port);

                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];

                while(true){
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData());
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    String[] account_info = sentence.split(",");
                    ArrayList<Account> list = account_HashMap.get(account_info[0].toLowerCase().substring(0, 1));
                    Account foundAccount = null;
                    
                    for(Account account : list){
                        if(account.emailAddress.equals(account_info[1])){
                            foundAccount = account;
                            break;
                        }
                    }
                    
                    synchronized(foundAccount){
                        // if its limit is not below 0
                        if(foundAccount.creditLimit >= 0){
                            sendData = "NotExceed,".getBytes();
                        }else{
                            sendData = "Exceed,".getBytes();
                        }
                    }

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                }
            
            }catch(Exception e){
                System.out.println("Server "+" has problem!!!!");
                System.out.println(e.toString());
            }
        }
        
    
    }

}
