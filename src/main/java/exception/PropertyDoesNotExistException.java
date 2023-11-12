package exception;

public class PropertyDoesNotExistException extends Exception{
    public PropertyDoesNotExistException (String message) {
        super(message);
    }
}