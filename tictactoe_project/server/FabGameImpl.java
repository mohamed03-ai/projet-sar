import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FabGameImpl extends UnicastRemoteObject implements FabGameInterface {
    private List<GameInterface> activeGames;

    public FabGameImpl() throws RemoteException {
        super();
        activeGames = new ArrayList<>();
    }

    public synchronized GameInterface newGame() throws RemoteException {
        // Look for a game that has only one player (waiting for second)
        for (GameInterface game : activeGames) {
            if (!((GameServer)game).isFull()) {
                return game;
            }
        }

        // All existing games are full or none exist — create a new one
        if (activeGames.size() < 5) { // max 5 games = 10 players
            try {
                GameInterface newGame = new GameServer();
                activeGames.add(newGame);
                return newGame;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new RemoteException("Server full. Cannot create more games at the moment.");
        }
    }
}
