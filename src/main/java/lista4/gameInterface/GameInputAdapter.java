package lista4.gameInterface;

import lista4.gameInterface.IOExceptions.WrogMoveFormat;

public interface GameInputAdapter<InputType> {

    String makeMove(String input) throws WrogMoveFormat;

}
