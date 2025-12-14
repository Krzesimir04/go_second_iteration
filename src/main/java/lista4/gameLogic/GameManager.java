package lista4.gameLogic;

import lista4.gameInterface.GameOutputAdapter;

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
    private static GameManager instance = new GameManager();
    private Board board;
    private GameOutputAdapter outAdapter; // dodałem out Adapter do gry on później wyśle result do klientów

    private GameManager() {
    }

    public static GameManager getInstance() {
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
