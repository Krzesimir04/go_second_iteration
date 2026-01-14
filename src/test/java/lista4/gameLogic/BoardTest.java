package lista4.gameLogic;

import lista4.gameLogic.*;
import lista4.gameLogic.gameExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void testIsEmpty() {
        assertTrue(board.isEmpty(0,0));
        Stone stone = new Stone(0,0,PlayerColor.BLACK, board);
        board.putStone(0,0,stone);
        assertFalse(board.isEmpty(0,0));
    }

    @Test
    void testInBoardBoundries() {
        assertTrue(board.inBoardBoundries(0,0));
        assertTrue(board.inBoardBoundries(18,18));
        assertFalse(board.inBoardBoundries(19,0));
        assertFalse(board.inBoardBoundries(-1,5));
    }

    @Test
    void testPutStoneOccupied() throws IllegalStoneOfBothColorsException {
        Stone black = new Stone(0,0,PlayerColor.BLACK, board);
        board.putStone(0,0,black);

        Stone white = new Stone(0,0,PlayerColor.WHITE, board);
        assertThrows(FieldOcupiedException.class, () -> board.putStone(0,0,white));
    }
}
