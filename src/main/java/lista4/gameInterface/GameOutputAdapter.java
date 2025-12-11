package lista4.gameInterface;

import java.io.PrintWriter;

import lista4.gameLogic.Board;
import lista4.gameLogic.Game;

// będzie miało observerów
public interface GameOutputAdapter<OutputType> {

    void sendBoard(Board board, String mes);

    void registerClient(Game.Player color, PrintWriter out); // to dodałem bo client thread używał a i był błąd (tam
    // widać dlaczego)

    // void sendStatus(...);
    void sendExceptionMessage(Exception exception);

}
