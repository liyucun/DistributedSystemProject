/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankclient;

import BankApp.Account;
import BankApp.Client;
import BankApp.ClientHelper;
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
            
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            Client client = (Client) ClientHelper.narrow(ncRef.resolve_str("ABC"));
        
            Account account = client.openAccount("yucun", "yucun", "yucun", "yucun", "yucun", "yucun");
        
            System.out.println(account.accountNumber);
        
        }catch(Exception ex){
        
        }
    }
    
}
