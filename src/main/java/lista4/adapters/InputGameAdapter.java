package lista4.adapters;

import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.IOExceptions.WrongMoveFormat;
import lista4.gameLogic.GameManager;

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

    public String makeMove(String input) throws WrongMoveFormat {
        if (input.matches("[a-zA-Z] [1-9]") || input.matches("[a-zA-Z] 1[0-3]")) {
            String to_ret = gameManager.simulateMove(input);
            return to_ret;
        }
        throw new WrongMoveFormat("zla komenda");
    };

}
