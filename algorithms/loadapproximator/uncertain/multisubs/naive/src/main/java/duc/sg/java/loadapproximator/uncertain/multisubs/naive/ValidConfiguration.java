package duc.sg.java.loadapproximator.uncertain.multisubs.naive;

import duc.sg.java.importer.ImportationException;
import duc.sg.java.importer.json.JsonGridImporter;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ValidConfiguration {
    private ValidConfiguration(){}

    private static boolean existPath(Substation sub1, Substation sub2, Map<Fuse, State> configuration) {
        var waiting = new Stack<Fuse>();
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        if(!sub1.getFuses().isEmpty()) {
            waiting.add(sub1.getFuses().get(0));
        }

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            if(current.getOwner().equals(sub2)) {
                return true;
            }

            if(configuration.get(current.getOpposite()) == State.CLOSED) {
                var ownerOpp = current.getOpposite().getOwner();
                for(var f: ownerOpp.getFuses()) {
                    if(configuration.get(f) == State.CLOSED && !visited.contains(f) && !inWaitingList.contains(f)) {
                        waiting.add(f);
                        inWaitingList.add(f);
                    }
                }
            }
        }

        return false;

    }

    public static boolean isValid(List<Substation> component, Map<Fuse, State> configuration) {

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
               Collection<Fuse> fuses = component.get(0).extractFuses();
               var configuration = new HashMap<Fuse, State>();
               for(Fuse f: fuses) {
                   if(f.getName().equals("Fuse 38") || f.getName().equals("Fuse 47")) {
                       configuration.put(f, State.OPEN);
                   } else {
                       configuration.put(f, State.CLOSED);
                   }
               }
               System.out.println("isValid?: " + isValid(component, configuration));
           }
       }

    }

}
