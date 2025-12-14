package lista4.adapters;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.Board;
import lista4.gameLogic.GameManager;

/**
 * Class uses Observers (ClientThread) to communicate with client
 * It is use in Game to send
 */
public class OutputGameAdapter implements GameOutputAdapter<String> {
    private static final ConcurrentMap<GameManager.Player, PrintWriter> activeWriters = new ConcurrentHashMap<>();

    public void registerPlayer(GameManager.Player color, PrintWriter out) {
        activeWriters.put(color, out);
    }

    public void unregisterPlayer(GameManager.Player color) {
        activeWriters.remove(color);
    }

    public void sendBroadcast(String message) {
        for (PrintWriter out : activeWriters.values()) {
            if (out != null) {
                out.println("message");
            }
        }
    }

    public void sendBoard(Board board, String mes) { // wysyła na razie zwykłego stringa
        for (PrintWriter out : activeWriters.values()) {
            if (out != null) {
                out.println("message: " + mes);
            }
        }
    };

    // void sendStatus(...);
    public void sendExceptionMessage(Exception exception) {

    };
}
