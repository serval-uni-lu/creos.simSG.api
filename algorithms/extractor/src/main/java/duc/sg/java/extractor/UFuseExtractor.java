package duc.sg.java.extractor;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Extracts fuses with uncertain status in a <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a> order.
 */
public class UFuseExtractor implements Extractor<Fuse> {
    private UFuseExtractor(){}

    public static final UFuseExtractor INSTANCE = new UFuseExtractor();


    @Override
    public void extractAndSave(Substation substation) {
        List<Fuse> uFuses = FuseExtractor.INSTANCE
                .getExtracted(substation)
                .stream()
                .filter((Fuse f) -> f.getStatus().isUncertain())
                .collect(Collectors.toList());
        substation.getGrid()
            .save(ExtracterUtils.getKeyUncertain(Fuse.class, substation), uFuses);
    }

    @Override
    public List<Fuse> getExtracted(Substation substation) {
        String key = ExtracterUtils.getKeyUncertain(Fuse.class, substation);
        Optional<Object> optFuses = substation.getGrid().retrieve(key);
        if(optFuses.isEmpty()) {
            extractAndSave(substation);
            optFuses = substation.getGrid().retrieve(key);
        }

        return (List<Fuse>) optFuses.get();
    }
}
