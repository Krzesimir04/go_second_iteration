package lista4.gameLogic.gameExceptions;

public abstract class FieldNotAvailable extends RuntimeException {
    public FieldNotAvailable(String message) {
        super(message);
    }
}
