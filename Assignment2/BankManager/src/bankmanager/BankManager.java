package bankmanager;

import BankApp.Client;
import BankApp.ClientHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.omg.CORBA.ORB;

/**
 *
 * @author yucunli
 */
public class BankManager {
    
    ORB orb;
    
    public BankManager(String[] args){
        orb = ORB.init(args, null);
    }

    public static void main(String[] args) throws IOException{
        
        BankManager client = new BankManager(args);
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
            
            if(request.equals("delayPayment")){
                System.out.println(client.delayPayment("", infoArray[0], -1, Integer.valueOf(infoArray[1])));
            }else if(request.equals("printCustomerInfo")){
                System.out.println(client.printCustomerInfo(""));
            }
            
        }catch(Exception ex){
        
        }
    }
    
    public void console(){
        try{
            System.out.println("Here are two available operation you can do on our system:");
            System.out.println("1. Delay Payment");
            System.out.println("2. Print Customer Info");
            
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String choice = bufferRead.readLine();
            
            if(!(choice.equals("1") || choice.equals("2"))){
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
                System.out.println("Please enter your information(Loan ID, New Due Date)");
                bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String info = bufferRead.readLine();
                String[] infoArray = info.split(",");
                
                sendRequest(bank_port, "delayPayment", infoArray);
            }else if(choice.equals("2")){
                String[] infoArray = {};
                
                sendRequest(bank_port, "printCustomerInfo", infoArray);
            }else{
                System.out.println("Illegal Input, please choice 1 or 2");
            }
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }
    
}
