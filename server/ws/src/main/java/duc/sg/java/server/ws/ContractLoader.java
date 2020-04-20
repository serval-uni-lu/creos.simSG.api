package duc.sg.java.server.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContractLoader {
    private static final String FILE_PATH = "./";
    private static final String FILE_NAME = "contract.json";

    private ContractLoader() {}

    public static String load() throws JsonLoaderException {
        InputStream contractStream = WSHandler.class
                .getClassLoader()
                .getResourceAsStream(FILE_PATH + FILE_NAME);
        if(contractStream == null) {
            String directory = WSHandler.class
                    .getClassLoader()
                    .getResource(FILE_PATH)
                    .getFile();
            throw new JsonLoaderException(FILE_NAME + " not found in " + directory + ". Relative path: " + FILE_PATH + FILE_NAME);
        }

        var content = new StringBuilder();
        try(var reader = new BufferedReader(new InputStreamReader(contractStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
           throw new JsonLoaderException(e);
        }

    }

}
