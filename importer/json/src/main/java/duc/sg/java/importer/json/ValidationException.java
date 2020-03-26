package duc.sg.java.importer.json;

public class ValidationException extends Exception {
    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
