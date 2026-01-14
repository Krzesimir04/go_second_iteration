package lista4.gameLogic;

import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NegotiationsTest {

    private GameContext context;
    private GameManager manager;
    private GameOutputAdapter adapter;

    @BeforeEach
    void setup() {
        context = new GameContext(GameState.GAME_NOT_INITIALIZED);
        context.setCurPlayerColor(PlayerColor.BLACK);

        manager = GameManager.getInstance();
        adapter = mock(GameOutputAdapter.class);
        manager.setAdapter(adapter);
    }

    // ------------------- GameContext Tests -------------------

    @Test
    void testNextPlayerAndCaptured() {
        context.nextPlayer();
        assertEquals(PlayerColor.WHITE, context.getCurPlayerColor());

        context.addCaptured(PlayerColor.BLACK);
        assertEquals(1, context.getCaptured(PlayerColor.BLACK));
    }

    @Test
    void testTerritoryAddRemovePoints() {
        context.addTerritory(PlayerColor.BLACK, 1,1);
        assertEquals(1, context.blackPoints());

        context.removeTerritory(PlayerColor.BLACK, 1,1);
        assertEquals(0, context.blackPoints());
    }

    // ------------------- GameManager Tests -------------------

    @Test
    void testGiveUpGameSendsWinMessage() {
        manager.giveUpGame(PlayerColor.BLACK);
        verify(adapter).sendWiningMassage(eq(PlayerColor.WHITE), eq(0), eq(0), eq(true));
    }

}
