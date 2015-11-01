package bank;

import BankApp.Client;
import BankApp.ClientHelper;
import java.io.PrintWriter;
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
            
            BankServant Bank_A = new BankServant(orb);
            Bank_A.port = 2298;
            Bank_A.uniqueAccountID_Base = 1;
            Bank_A.loanID_Base = 1;
            Bank_A.rest_port[0] = 5298;
            Bank_A.rest_port[1] = 7298;
            
            BankServant Bank_B = new BankServant(orb);
            Bank_B.port = 5298;
            Bank_B.uniqueAccountID_Base = 1000;
            Bank_B.loanID_Base = 1000;
            Bank_B.rest_port[0] = 2298;
            Bank_B.rest_port[1] = 7298;
            
            BankServant Bank_C = new BankServant(orb);
            Bank_C.port = 7298;
            Bank_C.uniqueAccountID_Base = 2000;
            Bank_C.loanID_Base = 2000;
            Bank_C.rest_port[0] = 2298;
            Bank_C.rest_port[1] = 5298;
            
            String path = System.getProperty("user.dir");
            path = path.substring(0, path.lastIndexOf("/") + 1);
            
            byte[] _id = rootpoa.activate_object(Bank_A);
            org.omg.CORBA.Object _ref = rootpoa.id_to_reference(_id);
            String _ior = orb.object_to_string(_ref);
            // Print IOR in the file
            PrintWriter _file = new PrintWriter(path + "bankAIOR.txt");
            _file.println(_ior);
            _file.close();
            
            _id = rootpoa.activate_object(Bank_B);
            _ref = rootpoa.id_to_reference(_id);
            _ior = orb.object_to_string(_ref);
            // Print IOR in the file
            _file = new PrintWriter(path + "bankBIOR.txt");
            _file.println(_ior);
            _file.close();
            
            _id = rootpoa.activate_object(Bank_C);
            _ref = rootpoa.id_to_reference(_id);
            _ior = orb.object_to_string(_ref);
            // Print IOR in the file
            _file = new PrintWriter(path + "bankCIOR.txt");
            _file.println(_ior);
            _file.close();
            
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
