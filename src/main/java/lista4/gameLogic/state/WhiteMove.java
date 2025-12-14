package lista4.gameLogic.state;

import lista4.gameLogic.GameContext;

public class WhiteMove implements GameStateBehaviour {

    @Override
    public GameState getState() {
        return GameState.WHITE_MOVE;
    }

    @Override
    public GameStateBehaviour startGame(GameContext context) {
        return null;
    }

    @Override
    public GameStateBehaviour stopGame(GameContext context) {
        return null;
    }

    @Override
    public void nextPlayer(GameContext context) {
        return GameState.BLACK_MOVE.getStateBehaviour();
    }
}
