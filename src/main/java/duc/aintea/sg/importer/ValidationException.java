package duc.aintea.sg.importer;

public class ValidationException extends Exception {
    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
