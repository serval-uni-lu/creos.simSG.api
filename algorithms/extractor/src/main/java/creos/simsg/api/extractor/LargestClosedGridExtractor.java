package creos.simsg.api.extractor;

import creos.simsg.api.model.Entity;
import creos.simsg.api.model.Substation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LargestClosedGridExtractor implements Extractor {
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
