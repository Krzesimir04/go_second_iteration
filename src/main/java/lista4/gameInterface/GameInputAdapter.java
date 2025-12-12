package lista4.gameInterface;

import lista4.gameInterface.IOExceptions.WrongMoveFormat;

public interface GameInputAdapter<InputType> {

    /**
     * checks if input is valid and send it or throw error
     * 
     * @param input
     * @return
     * @throws WrongMoveFormat
     */
    String makeMove(String input) throws WrongMoveFormat;

}
