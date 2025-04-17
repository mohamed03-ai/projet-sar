
import java.rmi.*;

public interface FabGameInterface extends Remote {
    GameInterface newGame() throws RemoteException;
}
