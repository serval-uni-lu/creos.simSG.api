package duc.sg.java.extracter;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Substation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LargestClosedGridExtractor implements Extracter {
    private Map<Entity,List<Entity>> grid_graph;

    public LargestClosedGridExtractor() {
        grid_graph = new HashMap<>();
    }


    @Override
    public void extractAndSave(Substation substation) {

    }

    @Override
    public List getExtracted(Substation substation) {
        return null;
    }
}
