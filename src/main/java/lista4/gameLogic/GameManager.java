package lista4.gameLogic;

import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.state.GameState;
import lista4.gameLogic.state.GameStateBehaviour;

public class GameManager {

    public enum Player {
        BLACK,
        WHITE
    }

    public static class Move {
        int x;
        int y;
        Player player;
    }

    // tutaj już tworzę instancje, konstruktor jest private (wziąłem z przykładu)
    private static GameManager instance;
    private final GameContext gameContext;
    private final Board board;
    private GameOutputAdapter outAdapter; // dodałem out Adapter do gry on później wyśle result do klientów


//----------------------------------------Sekcja techniczna------------------------------------------------------

    private GameManager(GameOutputAdapter outAdapter) {
        instance = this;
        gameContext = new GameContext(GameState.GAME_NOT_RUNNING);
        board = new Board();
        this.outAdapter = outAdapter;
    }

    public static GameManager getInstance() {
        return instance;
    }
    public void setAdapter(GameOutputAdapter adapter) {
        this.outAdapter = adapter;
    }
    public GameOutputAdapter getAdapter() { return outAdapter; }
    public Board getBoard() { return board; }

//---------------------------------------Sekcja start/stop gry--------------------------------------------------

    public void startGame(){
        gameContext.startGame();
    }

    public void endGame(){
        gameContext.endGame();
    }

//---------------------------------------Sekcja ruchów----------------------------------------------------------

    public void makeMove(){
        try {

        }
        catch (Exception e){

        }

    }



    // poniżej dodałem
    public String simulateMove(String message) { // może być voidem ale to do sprawdzania na razie
        try {
            Thread.sleep(100); // tutaj gra coś sobie sprawdza
        } catch (InterruptedException e) {
        }
        outAdapter.sendBoard(board, message);
        return "moved after: 1s";
    }



    // tu koniec dodawania

}
