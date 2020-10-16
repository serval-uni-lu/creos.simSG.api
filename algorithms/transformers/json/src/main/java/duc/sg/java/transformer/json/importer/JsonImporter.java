package duc.sg.java.transformer.json.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import duc.sg.java.transformer.ImportationException;
import duc.sg.java.transformer.Importer;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

abstract class JsonImporter<T> implements Importer<T> {
    @Override
    public Optional<T> from(File jsonFile) throws ImportationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromFile(jsonFile);
        } catch (IOException e) {
            throw new  ImportationException(e);
        }
        return extract(toImport);
    }

    @Override
    public Optional<T> from(Reader jsonReader) throws ImportationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromReader(jsonReader);
        } catch (IOException e) {
            throw new  ImportationException(e);
        }
        return extract(toImport);
    }

    @Override
    public Optional<T> from(String jsonText) throws ImportationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromString(jsonText);
        } catch (IOException e) {
            throw new  ImportationException(e);
        }
        return extract(toImport);
    }

    protected abstract Optional<T> extract(JsonNode root) throws ImportationException;
}
