package lista4.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GUIClient extends Application {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private TextArea logArea;
    private Label playerInfoLabel;
    private Label blackCapturedLabel;
    private Label whiteCapturedLabel;
    private Canvas boardCanvas;
    private final int BOARD_SIZE = 19;
    private final int CELL_SIZE = 30;

    @Override
    public void start(Stage primaryStage) {
        playerInfoLabel = new Label("Czekanie na gracza...");
        playerInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Informacje o pionkach
        Circle blackIcon = new Circle(8, Color.BLACK);
        blackCapturedLabel = new Label("0"); // zmienne z ClientThread które wyświetlą ilość posiadanych pionków itp
        Circle whiteIcon = new Circle(8, Color.WHITE);
        whiteIcon.setStroke(Color.BLACK);
        whiteCapturedLabel = new Label("0");

        HBox scoreBox = new HBox(10, playerInfoLabel, blackIcon, blackCapturedLabel, whiteIcon, whiteCapturedLabel);
        scoreBox.setAlignment(Pos.CENTER_LEFT);
        scoreBox.setPadding(new Insets(10));

        // Przyciski
        Button passButton = new Button("Pomiń ruch");
        passButton.setOnAction(e -> sendCommand("pass"));

        Button resignButton = new Button("Poddaj się");
        resignButton.setOnAction(e -> sendCommand("resign"));

        HBox buttonBox = new HBox(10, passButton, resignButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(scoreBox);
        topBar.setRight(buttonBox);
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");
        // --- Budowa Interfejsu ---
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(10);
        logArea.setStyle("-fx-font-size: 13px;");
        boardCanvas = new Canvas(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        drawGrid();
        // Obsługa kliknięcia w planszę
        boardCanvas.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / CELL_SIZE);
            int y = (int) (event.getY() / CELL_SIZE) + 1;
            char column = (char) ('a' + x);

            String command = column + " " + y;
            sendCommand(command);
        });

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(boardCanvas);
        root.setBottom(logArea);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Go Client - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- Połączenie z serwerem ---
        connectToServer(primaryStage);
    }

    // z Client.java z main łączenie z serwerem
    private void connectToServer(Stage stage) {
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_ADDRESS, PORT);
                out = new PrintWriter(socket.getOutputStream(), true);

                Platform.runLater(() -> logArea.setText("Połączono z serwerem!\n"));

                Thread listener = new Thread(new ServerListener(socket, stage));
                listener.setDaemon(true); // Zamknie się razem z aplikacją
                listener.start();
                out.println("GUI"); // print GUI to know which adapter to use
            } catch (IOException e) {
                Platform.runLater(() -> logArea.setText("Błąd połączenia: " + e.getMessage() + "\n"));
            }
        }).start();
    }

    private void sendCommand(String cmd) {
        if (out != null) {
            out.println(cmd);
            logArea.setText("Wysłano: " + cmd + "\n");
        }
    }

    private void drawGrid() {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.setFill(Color.web("#DEB887")); // Kolor BurlyWood (ładny beżowy/drewniany)
        gc.fillRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for (int i = 0; i < BOARD_SIZE; i++) {
            double offset = CELL_SIZE / 2.0;
            // Linie poziome i pionowe
            gc.strokeLine(CELL_SIZE / 2.0, CELL_SIZE / 2.0 + i * CELL_SIZE, (BOARD_SIZE - 0.5) * CELL_SIZE,
                    CELL_SIZE / 2.0 + i * CELL_SIZE);
            gc.strokeLine(CELL_SIZE / 2.0 + i * CELL_SIZE, CELL_SIZE / 2.0, CELL_SIZE / 2.0 + i * CELL_SIZE,
                    (BOARD_SIZE - 0.5) * CELL_SIZE);
        }
    }

    public void drawStone(int x, int y, Color color) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();

        double radius = CELL_SIZE * 0.4; // Kamień nieco mniejszy niż pole
        // Środek przecięcia linii:
        double centerX = CELL_SIZE / 2.0 + x * CELL_SIZE;
        double centerY = CELL_SIZE / 2.0 + y * CELL_SIZE; // (BOARD_SIZE-1-y) dopasowuje y do układu
                                                          // GUI

        gc.setFill(color);
        // Rysujemy kółko
        gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // Opcjonalnie: obramowanie kamienia dla lepszej widoczności
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    // Wewnętrzna klasa słuchacza, zmodyfikowana pod GUI
    private class ServerListener implements Runnable {
        private final Socket socket;
        private final Stage stage;

        public ServerListener(Socket socket, Stage stage) {
            this.socket = socket;
            this.stage = stage;
        }

        @Override
        public void run() {
            try (Scanner in = new Scanner(socket.getInputStream())) {
                String name;
                if (in.hasNextLine()) {
                    name = in.nextLine();
                    // stage.setTitle("Go Client - " + name);
                    Platform.runLater(() -> {
                        playerInfoLabel.setText("Grasz jako: " + name);
                        stage.setTitle("Go Client - " + name);
                    });
                }
                while (in.hasNextLine()) {
                    String message = in.nextLine();

                    Platform.runLater(() -> {
                        logArea.setText("" + message + "\n");

                        // parser komunikatów z serwera
                        if (message.equals("CLEAR_BOARD")) {
                            drawGrid(); // Przerysowuje czystą planszę (beż + linie)
                        } else if (message.startsWith("UPDATE")) {
                            try {

                                String[] parts = message.split(" ");
                                String colorStr = parts[1];
                                int x = Integer.parseInt(parts[2]);
                                int y = Integer.parseInt(parts[3]);

                                Color stoneColor = colorStr.equalsIgnoreCase("BLACK") ? Color.BLACK : Color.WHITE;
                                drawStone(x, y, stoneColor);
                            } catch (Exception e) {
                                logArea.setText("Błąd rysowania: " + e.getMessage() + "\n");
                            }
                        }
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> logArea.setText("Utracono połączenie.\n"));

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}