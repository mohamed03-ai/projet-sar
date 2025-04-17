
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {
    String sayGameStart() throws RemoteException;
    String joinGame(String name) throws RemoteException;
    String makeMove(int x, int y,String name) throws RemoteException;
    String getBoard() throws RemoteException;
    String getWinner() throws RemoteException;
    String getStatus() throws RemoteException;
    public String getPlayer1() throws RemoteException;
    public String getPlayer2() throws RemoteException;
    public boolean isFull() throws RemoteException;
}
