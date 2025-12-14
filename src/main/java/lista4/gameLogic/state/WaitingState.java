package lista4.gameLogic.state;

public class WaitingState implements GameStateBehaviour {

    @Override
    public GameState getState() {
        return GameState.GAME_NOT_RUNNING;
    }

    @Override
    public GameStateBehaviour startGame() {
        return GameState.BLACK_MOVE.getStateBehaviour();
    }

    @Override
    public GameStateBehaviour stopGame() {
        return this;
    }

    @Override
    public GameStateBehaviour nextPlayer() {
        return this;
    }
}
