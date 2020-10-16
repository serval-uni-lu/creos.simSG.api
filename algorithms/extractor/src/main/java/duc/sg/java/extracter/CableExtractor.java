package duc.sg.java.extracter;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.bfs.BFSFuse;

import java.util.*;

/**
 * Extract all cables in a <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a> order
 */
public class CableExtractor implements Extractor<Cable> {
    public static final CableExtractor INSTANCE = new CableExtractor();

    private CableExtractor(){}

    @Override
    public void extractAndSave(Substation substation) {
        Set<Cable> allCables = new HashSet<>();
        BFSFuse.INSTANCE.navigate(substation, (Fuse fuse) -> allCables.add(fuse.getCable()));
        substation.getGrid()
                .save(ExtracterUtils.getKeyCertain(Cable.class, substation),
                        new ArrayList<>(allCables)
                );
    }

    @Override
    public List<Cable> getExtracted(Substation substation) {
        String key = ExtracterUtils.getKeyCertain(Cable.class, substation);
        Optional<Object> optCables = substation.getGrid().retrieve(key);
        if(optCables.isEmpty()) {
            extractAndSave(substation);
            optCables = substation.getGrid().retrieve(key);
        }

        return (List<Cable>) optCables.get();
    }
}
