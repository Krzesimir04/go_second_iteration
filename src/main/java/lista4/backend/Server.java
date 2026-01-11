package lista4.backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.ArrayList;

import lista4.gameLogic.GameManager;
import lista4.gameLogic.PlayerColor;
import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.GameOutputAdapter;
import lista4.adapters.InputGameAdapter;
import lista4.adapters.OutputGameAdapter;
import lista4.adapters.GUIInputGameAdapter;
import lista4.adapters.GUIOutputGameAdapter;

/**
 * Server creates a game and both (IN and OUT) adapters
 * creates a socket and wait for 2 ClientThreads
 */
public class Server {
    private static int GAMERS_NUMBER = 2;
    private static final int PORT = 12345;

    private static ArrayList<PlayerColor> gamers = new ArrayList<>();
    public static GameManager gameManager = GameManager.getInstance();

    private static GameOutputAdapter outAdapter = new OutputGameAdapter();
    private static GameInputAdapter inAdapter = new InputGameAdapter(gameManager, outAdapter);
    private static GameOutputAdapter GUIoutAdapter = new GUIOutputGameAdapter();
    private static GameInputAdapter GUIinAdapter = new GUIInputGameAdapter(gameManager, GUIoutAdapter);

    public static void main(String[] args) throws IOException {
        System.out.println("Wielowątkowy serwer jest uruchomiony na porcie " + PORT + "...");
        ArrayList<GameInputAdapter> inputAdapters = new ArrayList<>();
        ArrayList<GameOutputAdapter> outputAdapters = new ArrayList<>();

        inputAdapters.add(GUIinAdapter);
        inputAdapters.add(inAdapter);
        outputAdapters.add(GUIoutAdapter);
        outputAdapters.add(outAdapter);
        ExecutorService pool = Executors.newFixedThreadPool(GAMERS_NUMBER);
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = listener.accept();
                System.out.println(">> Połączono z klientem: " + clientSocket.getInetAddress());

                if (gamers.size() < 2 && !gamers.contains(PlayerColor.BLACK)) {
                    pool.execute(new ClientThread(clientSocket, inputAdapters,
                            outputAdapters, PlayerColor.BLACK, gamers));
                    gamers.add(PlayerColor.BLACK);
                } else if (gamers.size() < 2 && !gamers.contains(PlayerColor.WHITE)) {
                    pool.execute(new ClientThread(clientSocket, inputAdapters,
                            outputAdapters, PlayerColor.WHITE, gamers));
                    gameManager.startGame();
                    gamers.add(PlayerColor.WHITE);
                }
            }
        } finally {
            pool.shutdown();
        }
    }
}
