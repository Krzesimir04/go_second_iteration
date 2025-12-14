package lista4.gameLogic.state;

//TODO Potencjalnie niepotrzebne, sprawdzić, czy nie trza usunąć
public class ProcessingState implements GameStateBehaviour {

    @Override
    public GameState getState() {
        return GameState.GAME_PROCESSING;
    }

    @Override
    public GameStateBehaviour startGame() {
        return this;
    }

    @Override
    public GameStateBehaviour stopGame() {
        return GameState.GAME_NOT_RUNNING.getStateBehaviour();
    }

    @Override
    public GameStateBehaviour nextPlayer() {
        return this;
    }
}
