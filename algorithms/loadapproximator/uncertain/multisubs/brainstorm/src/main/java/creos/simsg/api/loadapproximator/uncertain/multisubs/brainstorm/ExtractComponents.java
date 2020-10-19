package creos.simsg.api.loadapproximator.uncertain.multisubs.brainstorm;

import creos.simsg.api.model.Entity;
import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.model.Substation;
import creos.simsg.api.navigation.bfs.BFSEntity;
import creos.simsg.api.transformer.ImportationException;
import creos.simsg.api.transformer.json.importer.JsonGridImporter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Extract all components of a smart grid. We use the definition of
 * <a href="https://www.baeldung.com/cs/graph-connected-components">connected components</a> in a graph.
 *
 * The algorithm extracts and group all the substation of the different components. We consider the substations as
 * the entry points of the smart grid.
 */
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

                BFSEntity.INSTANCE.navigate(current, (Entity entity) -> {
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


    /**
     * Manual test
     */
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
