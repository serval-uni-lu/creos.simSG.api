package duc.sg.java.loadapproximator.uncertain.multisubs.naive;

import duc.sg.java.importer.ImportationException;
import duc.sg.java.importer.json.JsonGridImporter;
import duc.sg.java.model.Entity;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.bfs.BFSEntity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ExtractComponents {
    private ExtractComponents(){}


    public static Collection<List<Substation>> getConnectedSubstations(SmartGrid grid) {
        var processedSubs = new HashSet<Substation>();

        var res = new ArrayList<List<Substation>>();

        for(Substation current: grid.getSubstations()) {
            if(!processedSubs.contains(current)) {
                processedSubs.add(current);

                final var component = new HashSet<Substation>();
                component.add(current);

                BFSEntity.INSTANCE.navigate(current, (Entity entity, Set<Entity> visited) -> {
                    if(entity instanceof Substation) {
                        var casted = (Substation) entity;
                        component.add(casted);
                        processedSubs.add(casted);
                    }
                });

                res.add(new ArrayList<>(component));
            }
        }

        return res;
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

        // Scenario
        /*Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .chooseScenario(ScenarioName.CABINET)
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .chooseScenario(ScenarioName.PARA_CABINET)
                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build();
        SmartGrid grid = scenario.getGrid();*/


        Collection<? extends Collection<Substation>> components = ExtractComponents.getConnectedSubstations(grid);

        System.out.println("Components: ");
        for(Collection<Substation> component: components) {
            System.out.print("[");
            for (Substation subs: component) {
                System.out.print(subs.getName() + ", ");
            }
            System.out.println("]");
        }
    }



}
