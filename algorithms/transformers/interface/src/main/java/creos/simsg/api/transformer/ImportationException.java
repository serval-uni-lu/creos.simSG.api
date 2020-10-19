package creos.simsg.api.transformer;

public class ImportationException extends Exception {
    public ImportationException(Throwable cause) {
        super(cause);
    }

    public ImportationException(String message) {
        super(message);
    }
}
