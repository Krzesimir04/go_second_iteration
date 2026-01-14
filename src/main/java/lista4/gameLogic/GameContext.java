package lista4.gameLogic;

import lista4.gameLogic.state.GameState;

import java.util.HashSet;
import java.util.Set;

public class GameContext {

    PlayerColor curPlayerColor;
    int consecutivePasses;

    Set<Integer> blackTeritory = new HashSet<>();
    Set<Integer> whiteTeritory = new HashSet<>();

    GameState curGameState;

    GameContext(GameState initialState) {
        curGameState = initialState;
    }

    public GameState getGameState() {
        return curGameState;
    }

    public void setGameState(GameState gameState) {
        curGameState = gameState;
    }

    public void startGame() {
        curGameState.getStateBehaviour().startGame(this);
    }

    public void finishGame() {
        curGameState.getStateBehaviour().finishGame(this);
    }

    public void startNegotiations() {
        curGameState.getStateBehaviour().startNegotiations(this);
    }

    public void resumeGame() {
        curGameState.getStateBehaviour().resumeGame(this);
    }

    public void nextPlayer() {

        curPlayerColor = curPlayerColor.other();
    }

    public PlayerColor getCurPlayerColor() {
        return curPlayerColor;
    }

    public void setCurPlayerColor(PlayerColor playerColor) {
        this.curPlayerColor = playerColor;
    }

    public void passNextPlayer() {
        nextPlayer();
        consecutivePasses++;
    }

    public int getConsecutivePasses() {
        return consecutivePasses;
    }

    public void resetPasses() {
        consecutivePasses = 0;
    }

    public void addTeritory(PlayerColor playerColor, int x, int y) {
        int cordsCode = 100 * y + x;
        if (playerColor == PlayerColor.WHITE) {
            if (!blackTeritory.contains(cordsCode))
                whiteTeritory.add(cordsCode);
        }
        if (playerColor == PlayerColor.BLACK) {
            if (!whiteTeritory.contains(cordsCode))
                blackTeritory.add(cordsCode);
        }
    }

    public void removeTeritory(PlayerColor playerColor, int x, int y) {
        int cordsCode = 100 * y + x;
        if (playerColor == PlayerColor.WHITE) {
            whiteTeritory.remove(cordsCode);
        }
        if (playerColor == PlayerColor.BLACK) {
            blackTeritory.remove(cordsCode);
        }
    }

    public void clearTeritories() {
        blackTeritory.clear();
        whiteTeritory.clear();
    }

    public int blackPoints() {
        return blackTeritory.size();
    }

    public int whitePoints() {
        return whiteTeritory.size();
    }

}
