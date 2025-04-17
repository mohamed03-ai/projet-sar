

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServersideMain {
    
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);  // starts RMI registry
            FabGameImpl game = new FabGameImpl();                // creates factory
            registry.rebind("Fabrique", game);  
            System.out.println("Tic-Tac-Toe game server is running............");
        } catch (Exception e) {
            System.out.println("Server failed to start");
            e.printStackTrace();
        }
    }
}
