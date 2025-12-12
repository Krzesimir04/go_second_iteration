package lista4.gameLogic.state;

public interface GameStateBehaviour {
    GameState getState();

    GameStateBehaviour startGame();

    GameStateBehaviour stopGame();

    // GameStateBehaviour changePlayer();
}
