package creos.simsg.api.transformer.json.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import creos.simsg.api.transformer.ImportationException;

import java.io.*;

/**
 * Class to check the conformance of the JSON to a given schema
 */
class SchemaValidator {
    private SchemaValidator(){}

    private static File extractSchema(String schemaFilePath) throws IOException{
        InputStream schemaStream = SchemaValidator.class
                .getClassLoader()
                .getResourceAsStream(schemaFilePath);

        var reader = new BufferedReader(new InputStreamReader(schemaStream));
        String line;

        var tmpFile = File.createTempFile("schema",".json");
        var writer = new FileWriter(tmpFile);
        while ((line=reader.readLine())!=null) {
            writer.append(line);
        }
        writer.flush();
        writer.close();
        reader.close();

        return tmpFile;
    }

    static boolean isInvalid(JsonNode toCheck, String schemaFilePath) throws ImportationException {
        try {
            File schemaFile = extractSchema(schemaFilePath);
            JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaFile.toURI().toString());

            ProcessingReport report = schema.validate(toCheck);
            return !report.isSuccess();
        } catch (ProcessingException | IOException ex) {
            throw new ImportationException(ex);
        }
    }

}
