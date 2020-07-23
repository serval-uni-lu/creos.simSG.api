package duc.sg.java.importer;

public class ImportationException extends Exception {
    public ImportationException(Throwable cause) {
        super(cause);
    }

    public ImportationException(String message) {
        super(message);
    }
}
