package bank;

import BankApp.Client;
import BankApp.ClientHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 *
 * @author yucunli
 */
public class Main {

    public static void main(String[] args) {
        try {
            
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            
            BankServant bankobj = new BankServant(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bankobj);
            Client href = ClientHelper.narrow(ref);
            
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name("ABC");
            ncRef.rebind(path, href);
            
            System.out.println("Bank Server ready and waiting ...");
            
            // wait for invocations from clients
            for (;;){
                orb.run();
            }
            
        } catch (Exception ex) {
            
        }
        
        System.out.println("Bank Server Exiting ...");
    }
    
}
