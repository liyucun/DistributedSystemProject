package bankclient;

import BankApp.Account;
import BankApp.Client;
import BankApp.ClientHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 *
 * @author yucunli
 */
public class BankClient {
    
    ORB orb;
    
    public BankClient(String[] args){
        orb = ORB.init(args, null);
    }

    public static void main(String[] args) throws IOException {
        
        BankClient client = new BankClient(args);
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
    
    public void sendRequest(int bank, String request, String[] infoArray){
        try{
            String path = System.getProperty("user.dir");
            path = path.substring(0, path.lastIndexOf("/") + 1);
            
            File f = null;
            if(bank == 2298){
                f = new File(path + "bankAIOR.txt");
            }else if(bank == 5298){
                f = new File(path + "bankBIOR.txt");
            }else if(bank == 7298){
                f = new File(path + "bankCIOR.txt");
            }
            
            BufferedReader br = new BufferedReader(new FileReader(f));
            String IOR = br.readLine();
            br.close();
            
            org.omg.CORBA.Object obj = orb.string_to_object(IOR); 
            Client client = ClientHelper.narrow(obj);
            
            if(request.equals("openAccount")){
                System.out.println(client.openAccount("", infoArray[0], infoArray[1], infoArray[2], infoArray[3], infoArray[4]));
            }else if(request.equals("getLoan")){
                System.out.println(client.getLoan("", infoArray[0], infoArray[1], Integer.valueOf(infoArray[2])));
            }else if(request.equals("transferLoan")){
                System.out.println(client.transferLoan(infoArray[0], "", infoArray[1]));
            }
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }
    
    public void console(){
        System.out.println("Here are two available operation you can do on our system:");
        System.out.println("1. Open Account");
        System.out.println("2. Get Loan");
        System.out.println("3. Transfer Loan");
        
        try{
	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String choice = bufferRead.readLine();
            
            if(!(choice.equals("1") || choice.equals("2") || choice.equals("3"))){
                System.out.println("Illegal Input, please choice 1 or 2 or 3");
                return;
            }
            
            System.out.println("Please choose a bank:");
            System.out.println("1. " + 2298 + "\n2. "+5298
            +"\n3. "+7298);

            bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String bank = bufferRead.readLine();
            int bank_port;

            if(bank.equals("1")){
                bank_port = 2298;
            }else if(bank.equals("2")){
                bank_port = 5298;
            }else if(bank.equals("3")){
                bank_port = 7298;
            }else{
                System.out.println("Illegal Input, please choice 1 or 2 or 3");
                return;
            }
	      
            if(choice.equals("1")){
                
                System.out.println("Please enter your information(FirstName, LastName, Email, Phone, Password)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                sendRequest(bank_port, "openAccount", infoArray);
                
            }else if(choice.equals("2")){
                
                System.out.println("Please enter your information(AccountNumber, Password, LoanAmount)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                sendRequest(bank_port, "getLoan", infoArray);
                
            }else if(choice.equals("3")){
                System.out.println("Please enter your information(loan ID, target bank)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                sendRequest(bank_port, "transferLoan", infoArray);
                
            }else{
                System.out.println("Illegal Input, please choice 1 or 2 or 3");
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
    
}
