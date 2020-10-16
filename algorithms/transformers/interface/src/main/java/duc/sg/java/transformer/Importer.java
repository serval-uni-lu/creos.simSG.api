package duc.sg.java.transformer;

import java.io.File;
import java.io.Reader;
import java.util.Optional;

public interface Importer<T> {
    Optional<T> from(File file) throws ImportationException;
    Optional<T> from(Reader reader) throws ImportationException;
    Optional<T> from(String text) throws ImportationException;
}
