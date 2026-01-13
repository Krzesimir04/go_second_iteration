package lista4.gameLogic.gameExceptions;

import lista4.gameLogic.Move;

public class FieldNotInBoardException extends FieldNotAvailable {
    public FieldNotInBoardException(Move move) {
        super("To pole jest poza planszą.");
        this.move = move;
    }

    private final Move move;

    public Move getMove() {
        return move;
    }
}
