package lista4.gameLogic;

import lista4.gameLogic.state.GameState;

/**
 * Represents the current context of a Go game.
 * 
 * Keeps track of the current player, game state, and consecutive passes.
 * Provides methods to control game flow (start, stop, next player, pass).
 */
import java.util.HashSet;
import java.util.Set;

public class GameContext {

    /** Current player's color */
    PlayerColor curPlayerColor;

    /** Number of consecutive passes made by both players */
    int consecutivePasses;

    Set<Integer> blackTerritory = new HashSet<>();
    Set<Integer> whiteTerritory = new HashSet<>();

    int whiteCaptured = 0;
    int blackCaptured = 0;

    GameState curGameState;

    /**
     * Creates a new GameContext with the specified initial state.
     *
     * @param initialState The initial state of the game
     */
    GameContext(GameState initialState) {
        curGameState = initialState;
    }

    /**
     * Returns the current game state.
     *
     * @return Current GameState
     */
    public GameState getGameState() {
        return curGameState;
    }

    /**
     * Sets the current game state.
     *
     * @param gameState New GameState to set
     */
    public void setGameState(GameState gameState) {
        curGameState = gameState;
    }

    /**
     * Starts the game by delegating to the current state's behaviour.
     */
    public void startGame() {
        curGameState.getStateBehaviour().startGame(this);
    }

    /**
     * Stops the game by delegating to the current state's behaviour.
     */
    public void stopGame() {
        curGameState.getStateBehaviour().finishGame(this);
    }

    /**
     * Switches to the next player.
     */
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

    /**
     * Returns the current player's color.
     *
     * @return PlayerColor of current player
     */
    public PlayerColor getCurPlayerColor() {
        return curPlayerColor;
    }

    /**
     * Sets the current player's color.
     *
     * @param playerColor PlayerColor to set as current
     */
    public void setCurPlayerColor(PlayerColor playerColor) {
        this.curPlayerColor = playerColor;
    }

    /**
     * Passes the turn to the next player and increments consecutive passes counter.
     */
    public void passNextPlayer() {
        nextPlayer();
        consecutivePasses++;
    }

    /**
     * Returns the number of consecutive passes made by both players.
     *
     * @return Number of consecutive passes
     */
    public int getConsecutivePasses() {
        return consecutivePasses;
    }

    /**
     * Resets the consecutive passes counter to zero.
     */
    public void resetPasses() {
        consecutivePasses = 0;
    }

    public void addTerritory(PlayerColor playerColor, int x, int y) {
        int cordsCode = 100 * y + x;
        if (playerColor == PlayerColor.WHITE) {
            if (!blackTerritory.contains(cordsCode))
                whiteTerritory.add(cordsCode);
        }
        if (playerColor == PlayerColor.BLACK) {
            if (!whiteTerritory.contains(cordsCode))
                blackTerritory.add(cordsCode);
        }
    }

    public void removeTerritory(PlayerColor playerColor, int x, int y) {
        int cordsCode = 100 * y + x;
        if (playerColor == PlayerColor.WHITE) {
            whiteTerritory.remove(cordsCode);
        }
        if (playerColor == PlayerColor.BLACK) {
            blackTerritory.remove(cordsCode);
        }
    }

    public void clearTerritories() {
        blackTerritory.clear();
        whiteTerritory.clear();
    }

    public int blackPoints() {
        return blackTerritory.size() + whiteCaptured;
    }

    public int whitePoints() {
        return whiteTerritory.size() + blackCaptured;
    }

    public void addCaptured(PlayerColor playerColor) {
        if (playerColor == PlayerColor.WHITE) {
            whiteCaptured++;
        }
        if (playerColor == PlayerColor.BLACK) {
            blackCaptured++;
        }
    }

    public int getCaptured(PlayerColor playerColor) {
        if (playerColor == PlayerColor.WHITE) {
            return whiteCaptured;
        }
        if (playerColor == PlayerColor.BLACK) {
            return blackCaptured;
        }
        return 0;
    }

    // public int whitePoints() {
    // return whiteTerritory.size();
    // }

}
