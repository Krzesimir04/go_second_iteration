package lista4.gameInterface;

import java.io.PrintWriter;

import lista4.gameLogic.Board;
import lista4.gameLogic.GameManager;
import lista4.gameLogic.gameExceptions.OutputException;
import lista4.gameLogic.state.GameState;

// będzie miało observerów
public interface GameOutputAdapter<OutputType> {

    void sendBoard(Board board, String mes, GameManager.PlayerColor target);

    void registerPlayer(GameManager.PlayerColor color, PrintWriter out); // to dodałem bo client thread używał i był błąd bo po
                                                             // prostu tego brakowało(tam
    // widać dlaczego)

    void sendState(GameState gameState, GameManager.PlayerColor target);

    void sendExceptionMessage(OutputException exception, GameManager.PlayerColor target);

}
