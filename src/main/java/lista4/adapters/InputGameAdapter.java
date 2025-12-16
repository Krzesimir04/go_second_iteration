package lista4.adapters;

import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.IOExceptions.WrongMoveFormat;
import lista4.gameLogic.GameManager;
import lista4.gameLogic.PlayerColor;
import lista4.gameLogic.Move;

/**
 * Final adapter for console application
 * It is used in ClientThread and sends command to the game
 * Later game uses OutputGameAdapter and it will send info about result to
 * client
 */
public class InputGameAdapter implements GameInputAdapter<String> {
    private final GameManager gameManager; // Wstrzyknięta instancja Game

    public InputGameAdapter(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void makeMove(String input, PlayerColor color) throws WrongMoveFormat {
        if (input.matches("[a-mA-M] [1-9]") || input.matches("[a-mA-M] 1[0-3]")) {
            gameManager.makeMove(new Move(2, 4, color));
        } else {
            throw new WrongMoveFormat("zła komenda podaj w formacie a-m 1-13");
        }
    };
}
