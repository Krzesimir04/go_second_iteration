package lista4.adapters;

import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.GameOutputAdapter;
import lista4.gameInterface.IOExceptions.WrongMoveFormat;
import lista4.gameLogic.GameManager;
import lista4.gameLogic.PlayerColor;
import lista4.gameLogic.Move;

/**
 * Final adapter for GUI application
 * It is used in ClientThread and sends command to the game
 * Later game uses OutputGameAdapter and it will send info about result to
 * client
 */
public class GUIInputGameAdapter implements GameInputAdapter<String> {
    private final GameManager gameManager; // Wstrzyknięta instancja Game

    public GUIInputGameAdapter(GameManager gameManager, GameOutputAdapter outAdapter) {
        this.gameManager = gameManager;
        this.gameManager.setAdapter(outAdapter);
    }

    public void makeMove(String input, PlayerColor color) throws WrongMoveFormat {
        if (input.matches("[a-sA-S] [1-9]") || input.matches("[a-sA-S] 1[0-9]")) {
            int base = (int) 'a';
            int y = Integer.parseInt(input.substring(2)) - 1;
            int x = (int) input.toLowerCase().charAt(0) - base;
            gameManager.makeMove(new Move(x, y, color));
        } else {
            throw new WrongMoveFormat("Błąd wysyłania ruchu przez GUI.");
        }
    };
}
