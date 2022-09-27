package application;

public class CellAlreadyTakenException extends RuntimeException {

    public CellAlreadyTakenException(String s) {
        super(s);
    }
}