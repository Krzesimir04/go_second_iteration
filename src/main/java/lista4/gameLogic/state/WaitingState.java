package lista4.gameLogic.state;

public class WaitingState implements GameStateBehaviour {

    @Override
    public GameState getState() {
        return GameState.WAITING_FOR_GAMERS;
    }

    @Override
    public GameStateBehaviour startGame() {
        return GameState.GAME_RUNNING.getStateBehaviour();
    }

    @Override
    public GameStateBehaviour stopGame() {
        return this;
    }
}
