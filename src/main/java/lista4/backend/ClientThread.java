package lista4.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;

import lista4.gameInterface.GameInputAdapter;
import lista4.gameInterface.GameOutputAdapter;
import lista4.gameLogic.GameManager;
import lista4.gameLogic.PlayerColor;
import lista4.gameInterface.IOExceptions.WrongMoveFormat;

/**
 * Class responsible for communication between Client and the game.
 * It is runnig as thread in server - now can be only 2 ClientThreads
 * It (should) send moves and gives answare to Client
 */
class ClientThread implements Runnable {
    private Socket socket;
    GameInputAdapter inAdapter;
    GameOutputAdapter outAdapter;
    ArrayList<GameInputAdapter> inputAdapters = new ArrayList<>();
    ArrayList<GameOutputAdapter> outputAdapters = new ArrayList<>();
    PlayerColor color;
    ArrayList gamers;

    ClientThread(Socket socket, ArrayList inAdapters, ArrayList outAdapters,
            PlayerColor color, ArrayList gamers) {
        this.socket = socket;
        this.inputAdapters = inAdapters;
        this.outputAdapters = outAdapters;
        this.color = color;
        this.gamers = gamers;
    }

    @Override
    public void run() {

        try (Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            if (in.hasNextLine()) {
                String clientMessage = in.nextLine();
                switch (clientMessage) {
                    case "console":
                        this.inAdapter = inputAdapters.get(1);
                        this.outAdapter = outputAdapters.get(1);
                        break;
                    case "GUI":
                        this.inAdapter = inputAdapters.get(0);
                        this.outAdapter = outputAdapters.get(0);
                        break;
                    default:
                        this.inAdapter = inputAdapters.get(0);
                        this.outAdapter = outputAdapters.get(0);
                        break;
                }
            }
            out.println(color);
            outAdapter.registerPlayer(color, out);
            // out.println("WELCOME " + color + ". Waiting for command.");
            while (in.hasNextLine()) {
                String clientMessage = in.nextLine();
                try {
                    inAdapter.makeMove(clientMessage, color);
                } catch (Exception wrongmove) {
                    out.println(wrongmove.getMessage());
                }
                if (clientMessage.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd komunikacji z klientem " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                gamers.remove(color);
                socket.close();
                System.out.println(">> Połączenie zakończone z klientem: " + socket.getInetAddress());
            } catch (IOException e) {
            }
        }
    }
}