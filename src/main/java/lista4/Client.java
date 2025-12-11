package lista4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Łączenie z serwerem " + SERVER_ADDRESS + ":" + PORT + "...");

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Scanner in = new Scanner(socket.getInputStream());
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {
            Thread listenerThread = new Thread(new ServerListener(socket));
            listenerThread.start(); // Rozpoczynamy nasłuchiwanie w tle
            // System.out.println(in.nextLine()); // says Client WHITE or BLACK (who you are
            // at the game)

            String lineToSend;

            while ((lineToSend = consoleIn.readLine()) != null) {

                // 1. Wysyłanie do serwera
                out.println(lineToSend);

                // if (lineToSend.equalsIgnoreCase("quit")) {
                // break;
                // }

                // 2. Oczekiwanie na odpowiedź
                // if (in.hasNextLine()) {
                // String response = in.nextLine();
                // System.out.println("[Serwer] " + response);
                // } else {
                // System.out.println("Serwer zamknął połączenie.");
                // break;
                // }
            }

        } catch (IOException e) {
            System.err.println("Błąd połączenia/komunikacji: " + e.getMessage());
        }
        System.out.println("Klient zakończył działanie.");
    }

    private static class ServerListener implements Runnable {
        private final Socket socket;

        public ServerListener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (Scanner in = new Scanner(socket.getInputStream())) {

                // Pętla ciągle nasłuchuje strumienia wejściowego z serwera
                while (in.hasNextLine()) {
                    String message = in.nextLine();

                    // WYŚWIETLANIE ASYNCHRONICZNYCH BROADCASTÓW I SYNC. ODPOWIEDZI
                    System.out.println("\n[Serwer] " + message);

                    // Opcjonalnie: Dodaj logikę do przetwarzania specjalnych komunikatów
                    if (message.contains("GOODBYE")) {
                        break;
                    }
                }
            } catch (IOException e) {
                // To jest typowy sposób zakończenia, gdy socket jest zamknięty
                // z innej strony (np. przez wątek główny po komendzie QUIT)
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("ListenerThread: Błąd odczytu: " + e.getMessage());
                }
            } finally {
                // Upewniamy się, że wątek główny też wie o zamknięciu
                try {
                    if (!socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}