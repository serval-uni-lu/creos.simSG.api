package duc.sg.java.loadapproximator.uncertain.multisubs.brainstorm;

import duc.sg.java.extractor.FuseExtractor;
import duc.sg.java.model.*;
import duc.sg.java.navigation.bfs.BFSEntity;
import duc.sg.java.transformer.ImportationException;
import duc.sg.java.transformer.json.importer.JsonGridImporter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * First implementation of the rule that checks if two substations are connected or not.
 */
public class ValidConfiguration {
    private ValidConfiguration(){}

    private static boolean existPath(Substation sub1, Substation sub2, Configuration configuration) {
        final var areDisconnected = new boolean[]{true};
        BFSEntity.INSTANCE.navigate(sub1,
                (Entity curr) -> areDisconnected[0] = areDisconnected[0] && !curr.equals(sub2),
                (Fuse curr) -> areDisconnected[0] &&
                        configuration.isClosed(curr) &&
                        configuration.isClosed(curr.getOpposite())
        );
        return !areDisconnected[0];
    }

    public static boolean isValid(List<Substation> component, Configuration configuration) {
        for (int i = 0; i < component.size(); i++) {
            for (int j = i + 1; j < component.size(); j++) {
                Substation sub1 = component.get(i);
                Substation sub2 = component.get(j);

                if(existPath(sub1, sub2, configuration)) {
                    return false;
                }

            }
        }

        return true;
    }

    public static void main(String[] args) throws ImportationException {
        // Import from Json
        InputStream is = ExtractComponents.class
                .getClassLoader()
                .getResourceAsStream("6Subs2Components.json");
        if(is == null) {
            System.err.println("Eror wile loading the file.");
            return;
        }
        var isReader = new InputStreamReader(is);

        Optional<SmartGrid> optGrid = JsonGridImporter.INSTANCE.from(isReader);
        if(optGrid.isEmpty()) {
            System.err.println("No grid to analyse.");
            return;
        }

        SmartGrid grid = optGrid.get();
        Collection<List<Substation>> components = ExtractComponents.getConnectedSubstations(grid);

       for(List<Substation> component: components) {
           String names = component.stream()
                   .map(Substation::getName)
                   .reduce("", (s, s2) -> s + ", " + s2);
           System.out.println("[" + names + "]");
           if(!component.isEmpty()) {
               List<Fuse> fuses = FuseExtractor.INSTANCE.getExtracted(component.get(0));
               var configuration = new HashMap<Fuse, State>();
               for(Fuse f: fuses) {
                   if(f.getName().equals("Fuse 38") || f.getName().equals("Fuse 15") || f.getName().equals("Fuse 1") || f.getName().equals("Fuse 7") || f.getName().equals("Fuse 33") || f.getName().equals("Fuse 23")) {
                       configuration.put(f, State.OPEN);
                   } else {
                       configuration.put(f, State.CLOSED);
                   }
               }
               System.out.println("isValid?: " + isValid(component, new Configuration(configuration)));
           }
       }

    }

}
