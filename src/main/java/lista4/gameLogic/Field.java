package lista4.gameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single intersection (field) on the Go board.
 * 
 * Each Field can hold a Stone or be empty. Provides access to neighboring fields
 * and coordinates on the board.
 */
public class Field {
    private Stone stone;
    private final Board board;
    private final int x;
    private final int y;

    /**
     * Creates a new field at the specified coordinates on the given board.
     *
     * @param x X-coordinate of the field
     * @param y Y-coordinate of the field
     * @param board Board this field belongs to
     */
    Field(int x, int y, Board board){
        this.board = board;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the stone placed on this field.
     *
     * @return Stone on this field, or null if empty
     */
    public Stone getStone() {
        return stone;
    }

    /**
     * Places a stone on this field. Can overwrite the existing stone.
     *
     * @param stone Stone to place on this field
     */
    public  void putStone(Stone stone) {
        this.stone = stone;
    }

    /**
     * Returns a list of neighboring fields (up, down, left, right) that are within
     * the bounds of the board.
     *
     * @return List of neighboring Field objects
     */
    public List<Field> getNeighbours(){
        ArrayList<Field> neighbours = new ArrayList<Field>();

        for(Board.Direction direction : Board.Direction.values()){
            Field neighbour = board.getField(x + direction.getX(), y + direction.getY());
            if(neighbour == null) continue;

            neighbours.add(neighbour);
        }
        return neighbours;
    }

    /**
     * Returns the X-coordinate of this field.
     *
     * @return X-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y-coordinate of this field.
     *
     * @return Y-coordinate
     */
    public int getY() {
        return y;
    }
}
