package lista4.adapters;

import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.IOExceptions.WrogMoveFormat;
import lista4.gameLogic.Game;
import lista4.gameInterface.IOExceptions.WrogMoveFormat;

/**
 * Final adapter for console application
 * It is used in ClientThread and sends command to the game
 * Later game uses OutputGameAdapter and it will send info about result to
 * client
 */
public class InputGameAdapter implements GameInputAdapter {
    private final Game game; // Wstrzyknięta instancja Game

    public InputGameAdapter(Game game) {
        this.game = game;
    }

    public String makeMove(String input) throws WrogMoveFormat {
        if (input.equals("blad")) {
            throw new WrongThreadException("zla komenda");
        }
        String to_ret = game.simulateMove(input);
        return to_ret;
    };

}
