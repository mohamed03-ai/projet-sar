

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class clientsideMain {

    public static void main(String[] args) {
        System.out.println("Hello from clientsideMain");

        try {
            // Connect to RMI Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FabGameInterface fabrique = (FabGameInterface) registry.lookup("Fabrique");

            GameInterface newgame = fabrique.newGame();

            // Ask for player name using GUI input
            String playername = JOptionPane.showInputDialog(null, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);

            if (playername == null || playername.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter a name to play.");
                return;
            }

            // Join the game
            String response = newgame.joinGame(playername);
            System.out.println(response);
            JOptionPane.showMessageDialog(null, response);

            if (response.startsWith("Joined")) {
                // Wait until game starts
                while (true) {
                    String status = newgame.getStatus();
                    if (status.contains("Game started!")) {
                        break;
                    }
                    Thread.sleep(1000);
                }

                // Launch the GUI in the Swing Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    new TicTacToeGUI(newgame, playername);
                });

            } else {
                JOptionPane.showMessageDialog(null, "Unable to join the game.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the server.");
            e.printStackTrace();
        }
    }
}
