package lista4.gameLogic;

import java.lang.Object.*;

import lista4.gameInterface.GameOutputAdapter;

public class Game {

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
    private static Game instance = new Game();
    private Board board;
    private GameOutputAdapter outAdapter; // dodałem out Adapter do gry on później wyśle result do klientów

    private Game() {
    }

    public static Game getInstance() {
        return instance;
    }

    public boolean validateMove(Move move) {
        return board.isFieldAvailable(move.x, move.y, move.player);
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

    public void addAdapter(GameOutputAdapter adapter) {
        this.outAdapter = adapter;
    }
    // tu koniec dodawania

}
