package lista4.gameLogic.state;

public class RunningState implements GameStateBehaviour {

    @Override
    public GameState getState() {
        return GameState.GAME_RUNNING;
    }

    @Override
    public GameStateBehaviour startGame() {
        return this;
    }

    @Override
    public GameStateBehaviour stopGame() {
        return GameState.WAITING_FOR_GAMERS.getStateBehaviour();
    }
}
