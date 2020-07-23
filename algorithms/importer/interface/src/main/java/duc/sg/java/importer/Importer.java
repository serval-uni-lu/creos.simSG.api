package duc.sg.java.importer;

import java.io.File;
import java.io.Reader;
import java.util.Optional;

public interface Importer<T> {
    Optional<T> from(File jsonFile) throws ImportationException;
    Optional<T> from(Reader jsonReader) throws ImportationException;
}
