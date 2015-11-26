package rm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import servant.BankServant;

public class ReplicaManager {
    
    HashMap<String, BankServant> BankServantMap;

    public ReplicaManager(){
        
        //3 bank servants
        BankServantMap = new HashMap<String, BankServant>();
        //BankServant para: Bank port, other two bank port, AccountID_UniqueBase, LoanID_UniqueBase
        BankServantMap.put("A", new BankServant());
        BankServantMap.put("B", new BankServant());
        BankServantMap.put("C", new BankServant());
        
        //receiver thread
        RMReceiver rmReceiver = new RMReceiver(9999);
        rmReceiver.run();
    }
    
    //recovery method
    private void recover(){
        //delete bank servant instance
        BankServantMap.clear();
        
        //obtain correct bank servant port
        
        //recovery
        //BankServant para: corresponding right bank servant
        BankServantMap.put("A", new BankServant());
        BankServantMap.put("B", new BankServant());
        BankServantMap.put("C", new BankServant());
    }
    
    private class RMReceiver implements Runnable{
        
        int RMport;
        
        public RMReceiver(int RMport){
            this.RMport = RMport;
        }

        @Override
        public void run() {
            
            try {
                DatagramSocket serverSocket = new DatagramSocket(RMport);
                
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                
                while(true){
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData());
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    
                    //***************************
                    //UDP message processing...
                    //***************************
                }
            
            } catch (SocketException ex) {
                System.out.println(ex.toString());
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
        
    }
}
