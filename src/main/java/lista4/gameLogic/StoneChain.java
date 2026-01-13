package lista4.gameLogic;

import java.util.HashSet;
import java.util.Set;

public class StoneChain {
    private final Set<Stone> stones = new HashSet<>();

    public StoneChain(Stone initial) {
        addStone(initial);
    }

    public void addStone(Stone s) {
        stones.add(s);
        s.setChain(this);
    }

    public void merge(StoneChain other) {
        for (Stone s : other.stones) addStone(s);
    }

    public int getBreathCount() {
        Set<Field> breaths = new HashSet<>();
        for (Stone s : stones) {
            breaths.addAll(s.getBreaths());
        }
        return breaths.size();
    }

    public boolean isDead() {
        return getBreathCount() == 0;
    }

    public Set<Stone> getStones() { return stones; }
}
