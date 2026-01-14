package lista4.gameLogic;

import lista4.gameLogic.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameContextTest {

    private GameContext context;

    @BeforeEach
    void setup() {
        context = new GameContext(GameState.GAME_NOT_INITIALIZED);
        context.setCurPlayerColor(PlayerColor.BLACK);
    }

    @Test
    void testNextPlayer() {
        context.nextPlayer();
        assertEquals(PlayerColor.WHITE, context.getCurPlayerColor());
    }

    @Test
    void testCaptured() {
        context.addCaptured(PlayerColor.BLACK);
        assertEquals(1, context.getCaptured(PlayerColor.BLACK));
    }

    @Test
    void testTerritory() {
        context.addTerritory(PlayerColor.BLACK, 1, 1);
        assertEquals(1, context.blackPoints());
        context.removeTerritory(PlayerColor.BLACK, 1,1);
        assertEquals(0, context.blackPoints());
    }
}
