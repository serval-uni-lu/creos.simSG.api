package duc.aintea.sg.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.net.URISyntaxException;
import java.net.URL;

public class SchemaValidator {
    private SchemaValidator(){}

    public static boolean validate(JsonNode toCheck, String schemaFilePath) throws ValidationException {
        try {
            URL schemaURL = ScBasedJson.class.getClassLoader().getResource(schemaFilePath);
            if(schemaURL == null) {
                throw new ValidationException("Schema scenario-schema.json not found in the root resource folder.");
            }
            JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaURL.toURI().toString());

            ProcessingReport report = schema.validate(toCheck);
            return report.isSuccess();
        } catch (URISyntaxException | ProcessingException ex) {
            throw new ValidationException(ex);
        }
    }

}
