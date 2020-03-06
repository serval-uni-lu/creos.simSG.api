package duc.aintea.loadapproximation;

import duc.aintea.sg.Substation;
import duc.aintea.sg.importer.JSONImporter;
import duc.aintea.sg.importer.ValidationException;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;

public class ApproximatorRunner {
    private static final Options clOptions;
    private static final Option scBasedOpt;
    private static final Option loadType;

    static {
        clOptions = new Options();
        scBasedOpt = new Option("i", "input", true, "Input file that describes the grid");
        scBasedOpt.setRequired(true);
        scBasedOpt.setType(PatternOptionBuilder.FILE_VALUE);
        clOptions.addOption(scBasedOpt);

        loadType = new Option("t", "type", true, "Defines which approximator should be executed: certain (c) or uncertain (uc) one.");
        loadType.setType(PatternOptionBuilder.STRING_VALUE);
        loadType.setRequired(true);
        clOptions.addOption(loadType);
    }

    private static void printHelp(String errMsg) {
        var helper = new HelpFormatter();
        System.err.println(errMsg);

        PrintWriter pw = new PrintWriter(System.err);
        helper.printHelp(pw, 80, "./ducPropagationJava.jar", null, clOptions, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, null, false);
        pw.flush();


    }

    public static void main(String[] args) throws ValidationException {
        CommandLineParser parser = new DefaultParser();

        String type;
        File jsonFIle;
        try {
            CommandLine cmd = parser.parse(clOptions, args);

            type = (String) cmd.getParsedOptionValue(loadType.getOpt());
            if(!type.equals("c") && !type.equals("uc")) {
                printHelp("Type \"" + type + "\" is not a correct type.");
            }

            jsonFIle = (File) cmd.getParsedOptionValue(scBasedOpt.getOpt());
            if(jsonFIle.isDirectory() || !jsonFIle.exists()) {
                printHelp(jsonFIle + " is not an existing file.");
            }

        } catch (ParseException pe) {
            printHelp("Error while parsing the command: " + pe.getMessage());
            System.exit(2);
            return;
        }

        Optional<Substation> optSubs = JSONImporter.from(jsonFIle);
        if(optSubs.isPresent()) {
            Substation substation = optSubs.get();
            if(type.equals("c")) {
                LoadApproximator.approximate(substation);
            } else {
                UncertainLoadApproximator.approximate(substation);
            }
        } else {
            System.err.println("Json file do not contain a valid request.");
        }
    }
}
