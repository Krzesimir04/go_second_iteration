package lista4.adapters;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.Board;
import lista4.gameLogic.state.GameState;
import lista4.gameLogic.PlayerColor;
import lista4.gameLogic.Stone;

/**
 * Class uses Observers (ClientThread) to communicate with client
 * It is use in Game to send
 */
public class GUIOutputGameAdapter implements GameOutputAdapter<String> {
    private static final ConcurrentMap<PlayerColor, PrintWriter> activeWriters = new ConcurrentHashMap<>();

    public void registerPlayer(PlayerColor color, PrintWriter out) {
        activeWriters.put(color, out);
        // out.println("Welcome " + color);
        if (activeWriters.size() == 2) {
            // sendState(, color);
        }
    }

    @Override
    public void sendState(GameState gameState, PlayerColor target) {
        sendToTarget("STATUS Gra trwa. Tura: " + gameState.toString(), target);
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

    public void sendBoard(Board board, PlayerColor target) {
        if (target == PlayerColor.BOTH) {
            for (PrintWriter out : activeWriters.values()) {
                if (out != null) {
                    sendSpecificStoneUpdates(out, board); // GUI
                }
            }
        } else {
            PrintWriter out = activeWriters.get(target);
            sendSpecificStoneUpdates(out, board); // GUI
        }
    };

    public void sendExceptionMessage(Exception exception, PlayerColor target) {
        PrintWriter out = activeWriters.get(target);
        out.println(exception.getMessage());
        out.println("blad");
    };

    private void sendToTarget(String message, PlayerColor target) {
        if (target == PlayerColor.BOTH) {
            sendBroadcast(message);
        } else {
            PrintWriter out = activeWriters.get(target);
            if (out != null)
                out.println(message);
        }
    }

    // GUI
    private void sendSpecificStoneUpdates(PrintWriter out, Board board) {
        // Najpierw czyścimy widok u klienta (opcjonalnie, zależy od logiki GUI)
        // out.println("CLEAR_BOARD");

        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                Stone stone = board.getStone(x, y);
                if (stone != null) {
                    String color = (stone.getPlayerColor() == PlayerColor.WHITE) ? "WHITE" : "BLACK";
                    // Format: UPDATE [COLOR] [X] [Y] wysyła do GUI aby wiedziało jak kolorować
                    out.println("UPDATE " + color + " " + x + " " + y);
                }
            }
        }
    }

}
