package lista4.adapters;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.Board;
import lista4.gameLogic.gameExceptions.OutputException;
import lista4.gameLogic.state.GameState;
import lista4.gameLogic.PlayerColor;

/**
 * Class uses Observers (ClientThread) to communicate with client
 * It is use in Game to send
 */
public class OutputGameAdapter implements GameOutputAdapter<String> {
    private static final ConcurrentMap<PlayerColor, PrintWriter> activeWriters = new ConcurrentHashMap<>();

    public void registerPlayer(PlayerColor color, PrintWriter out) {
        activeWriters.put(color, out);
    }

    @Override
    public void sendState(GameState gameState, PlayerColor target) {

    }

    @Override
    public void sendExceptionMessage(OutputException exception, PlayerColor target) {

    }

    public void unregisterPlayer(PlayerColor color) {
        activeWriters.remove(color);
    }

    public void sendBroadcast(String message) {
        for (PrintWriter out : activeWriters.values()) {
            if (out != null) {
                out.println(message);
            }
        }
    }

    public void sendBoard(Board board, PlayerColor target) { // wysyła na razie zwykłego stringa
        if (target == PlayerColor.BOTH) {
            for (PrintWriter out : activeWriters.values()) {
                if (out != null) {
                    out.println("plansza: ");
                }
            }
        } else {
            PrintWriter out = activeWriters.get(target);
            out.println("plansza: ");

        }
    };

    // void sendStatus(...);
    public void sendExceptionMessage(Exception exception, PlayerColor target) {
        PrintWriter out = activeWriters.get(target);
        out.println(exception.getMessage());
        out.println("blad");
    };
}
