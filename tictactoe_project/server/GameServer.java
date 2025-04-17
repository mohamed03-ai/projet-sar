

import java.rmi.RemoteException;

public class GameServer extends java.rmi.server.UnicastRemoteObject implements GameInterface {
    private final char[][] board= new char[3][3];
    private String status="Waiting for players to join in..............";
    private String winner="No Winner Yet";
    private int numPlayer=0;
    private boolean player1Turn=true;
    private String player1=null,player2=null;

    public GameServer() throws Exception{
        super();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j]='-';
            }
        }
    }
    

    public synchronized String joinGame(String name) throws RemoteException{
        if (player1 == null) {
            player1 = name;
            status = "Waiting for Player 1 turn.";
            return "Joined as Player 1.";
        } else if (player2 == null) {
            player2 = name;
            status = "Game started!"+player1+" turn.";
            return "Joined as Player 2.";
        } else {
            return "Game is full.";
        }
        
    }
    private boolean checkWinner(){
        for(int i=0;i<3;i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!='-'){
                return true;
            }
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!='-'){
                return true;
            }
        }
        return (board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[0][0]!='-') 
                || (board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[0][2]!='-');
    };
    private boolean fullBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='-'){
                    return false;
                }
            }
        }
        return true;
    }
    public synchronized String getBoard() throws RemoteException{
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public String getStatus() throws RemoteException{
        return status;
    }

    // making move
    public synchronized String makeMove(int x, int y, String name) throws RemoteException{
        //check for player
        if(player1==null||player2==null){
            return "No player has joined yet. Waiting for player to join.";
        }
        //check for turn
        if((player1Turn && !name.equals(player1)) || (!player1Turn && !name.equals(player2))){
            return "It's not your turn";
        }
        //check for valid move
        if(x<0 || x>2 || y<0 || y>2 || board[x][y]!='-'){
            return "Invalid move.";
        }

        //make move
        board[x][y]=player1Turn?'X':'O';
        player1Turn=!player1Turn;
        if(checkWinner()){
            winner=player1Turn?player2:player1;
            status=winner+" wins. Game Over!!!";
            return status;
        }
        

        if(fullBoard()){
            status="Game Over!!! It's a Draw";
            return status;
        }

        status = "Player " + (player1Turn ? "1" : "2") + "'s turn.";
        return "Move accepted.";
    }

    @Override
    public String sayGameStart() throws RemoteException {
        return numPlayer == 2 ? "Game is ready to start!" : "Waiting for players to join...";
    }

    @Override
    public String getWinner() throws RemoteException {
        return winner;
    }

    @Override
    public String getPlayer1() throws RemoteException {
        return player1;
    }

    @Override
    public String getPlayer2() throws RemoteException {
        return player2;
    }
    public boolean isFull() throws RemoteException {
    return player1 != null && player2 != null;
}
}
