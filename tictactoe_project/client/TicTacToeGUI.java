
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Arrays;


public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameInterface game;
    private String playerName;

    public TicTacToeGUI(GameInterface game, String playerName) {
        this.game = game;
        this.playerName = playerName;

        setTitle("Tic-Tac-Toe - " + playerName);
        setLayout(new GridLayout(3, 3));
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Build 3x3 board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = i, y = j;
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].addActionListener(e -> {
                    try {
                        String response = game.makeMove(x, y, playerName);
                        JOptionPane.showMessageDialog(this, response);
                       new Timer(500, evt -> {
                        try {
                            refreshBoard();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        ((Timer) evt.getSource()).stop();
                        }).start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                add(buttons[i][j]);
            }
        }

        setVisible(true);

        // Start polling in background
        new Timer(2000, e -> {
            try {
                refreshBoard();
                String status = game.getStatus();
                setTitle("Tic-Tac-Toe - " + playerName + " | " + status);
                if (!status.endsWith("turn.")) {
                    JOptionPane.showMessageDialog(this, status);
                    ((Timer)e.getSource()).stop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

  private void refreshBoard() throws RemoteException {
        String board = game.getBoard(); 
        String[] rows = board.split("\n");
        for (int i = 0; i < 3; i++) {
            String[] cells = rows[i].split(" ");  // Split by space instead of "|"
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(cells[j].trim()); 
            }
        }
        String status = game.getStatus();
        if (!status.endsWith("turn.")) {
            JOptionPane.showMessageDialog(this, status); // Display result
            dispose(); 
            System.exit(0);// Close the GUI
        }
    }



}
