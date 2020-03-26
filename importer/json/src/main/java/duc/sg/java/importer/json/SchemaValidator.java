package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.*;

public class SchemaValidator {
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

    public static boolean validate(JsonNode toCheck, String schemaFilePath) throws ValidationException {
        try {
            File schemaFile = extractSchema(schemaFilePath);
            JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaFile.toURI().toString());

            ProcessingReport report = schema.validate(toCheck);
            return report.isSuccess();
        } catch (ProcessingException | IOException ex) {
            throw new ValidationException(ex);
        }
    }

}
