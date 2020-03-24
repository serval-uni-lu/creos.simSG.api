package duc.aintea.loadapproximation.test;

import duc.aintea.sg.Substation;
import duc.aintea.sg.importer.JsonImporter;
import duc.aintea.sg.importer.ValidationException;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonImporterHelper {
    private JsonImporterHelper() {}

    public static Substation importSubs(String fileName) throws ValidationException {
        final String jsonPath = JsonImporterHelper.class
                .getClassLoader()
                .getResource("validJson/sg/" + fileName)
                .getPath();
        final var file = new File(jsonPath);

        Optional<List<Substation>> optionalSubstations = JsonImporter.from(file);
        assertTrue(optionalSubstations.isPresent());
        List<Substation> substations = optionalSubstations.get();

        return substations.get(0);
    }

}
