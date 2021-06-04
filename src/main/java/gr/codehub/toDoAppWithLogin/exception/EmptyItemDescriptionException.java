package gr.codehub.toDoAppWithLogin.exception;

public class EmptyItemDescriptionException extends RuntimeException {
    public EmptyItemDescriptionException(String description) {
        super(description);
    }
}
