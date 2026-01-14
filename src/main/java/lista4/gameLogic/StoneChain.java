package lista4.gameLogic;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a chain of connected stones of the same color on the board.
 * <p>
 * A StoneChain keeps track of all stones in the chain and allows operations
 * such as merging with another chain, checking if the chain is dead (no breaths),
 * and capturing (removing) the stones from the board.
 */
public class StoneChain {

    /** Set of stones belonging to this chain */
    private final Set<Stone> stones = new HashSet<>();

    /**
     * Creates a new stone chain with a single initial stone.
     *
     * @param initial The initial stone to start the chain
     */
    public StoneChain(Stone initial) {
        addStone(initial);
    }

    /**
     * Adds a stone to this chain.
     * Also sets the stone's chain reference to this.
     *
     * @param s The stone to add
     */
    public void addStone(Stone s) {
        stones.add(s);
        s.setChain(this);
    }

    /**
     * Merges another StoneChain into this one.
     * All stones in the other chain are added to this chain.
     *
     * @param other The other chain to merge
     */
    public void merge(StoneChain other) {
        for (Stone s : other.stones) addStone(s);
    }

    /**
     * Calculates the total number of "breaths" (empty neighboring fields)
     * for all stones in the chain.
     *
     * @return Number of breaths
     */
    public int getBreathCount() {
        Set<Field> breaths = new HashSet<>();
        for (Stone stoneElement : stones) {
            breaths.addAll(stoneElement.getBreaths());
        }
        return breaths.size();
    }

    /**
     * Checks if the chain is dead (no remaining breaths).
     *
     * @return true if the chain is dead, false otherwise
     */
    public boolean isDead() {
        return getBreathCount() == 0;
    }

    /**
     * Captures all stones in this chain by removing them from the board.
     */
    public void captureChain(){
        for (Stone stone : stones){
            int x = stone.getX();
            int y = stone.getY();
            stone.getBoard().removeStone(x, y);
            GameManager.getInstance().addCaptured(stone.getPlayerColor());
        }
    }

    /**
     * Returns the set of stones in this chain.
     *
     * @return Set of stones
     */
    public Set<Stone> getStones() { return stones; }
}
