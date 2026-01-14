package lista4.gameLogic;

/**
 * Represents a single move in the game.
 * 
 * Stores the coordinates on the board and the color of the player making the move.
 */
public class Move {
    /** X-coordinate on the board (column) */
    int x;

    /** Y-coordinate on the board (row) */
    int y;

    /** Color of the player making the move */
    PlayerColor playerColor;

    /**
     * Constructs a Move object with the specified coordinates and player color.
     *
     * @param x           X-coordinate of the move
     * @param y           Y-coordinate of the move
     * @param playerColor Color of the player making the move
     */
    public Move(int x, int y, PlayerColor playerColor) {
        this.x = x;
        this.y = y;
        this.playerColor = playerColor;
    }

    /**
     * Returns the X-coordinate of this move.
     *
     * @return X-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y-coordinate of this move.
     *
     * @return Y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the color of the player making this move.
     *
     * @return PlayerColor of the move
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }
}
