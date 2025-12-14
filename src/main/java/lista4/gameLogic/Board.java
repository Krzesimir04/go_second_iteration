package lista4.gameLogic;

public class Board {
    private int boardSize = 19;
    private final Stone[][] board;

    public Board() {
        board = new Stone[boardSize][boardSize];
    }

    public boolean isFieldAvailable(int x, int y, GameManager.Player player){
        if(x >= 0 && y >= 0 && x < boardSize && y < boardSize){
            return (board[x][y] == null);
        }
        return false;
    }

    public void PutStone(int x, int y, Stone stone){
        if( !isFieldAvailable(x, y, stone.) )
        board[x][y] = stone;
    }

}
