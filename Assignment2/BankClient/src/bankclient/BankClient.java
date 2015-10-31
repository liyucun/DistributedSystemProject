/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankclient;

import BankApp.Account;
import BankApp.Client;
import BankApp.ClientHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 *
 * @author yucunli
 */
public class BankClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            String path = System.getProperty("user.dir");
            path = path.substring(0, path.lastIndexOf("/") + 1);
            
            File f = new File(path + "bankCIOR.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String IOR = br.readLine();
            br.close();
            
            System.out.println(IOR);
            
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object obj = orb.string_to_object(IOR); 
            Client client = ClientHelper.narrow(obj);
            
            System.out.println(client.transferLoan("100", "10", "1"));
            
        }catch(Exception ex){
        
        }
    }
    
}
