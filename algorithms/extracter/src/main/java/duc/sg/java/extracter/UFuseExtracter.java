package duc.sg.java.extracter;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.List;
import java.util.stream.Collectors;

public class UFuseExtracter implements Extracter<Fuse> {
    private UFuseExtracter(){}

    public static final UFuseExtracter INSTANCE = new UFuseExtracter();


    @Override
    public void extractAndSave(Substation substation) {
        List<Fuse> uFuses = FuseExtracter.INSTANCE
                .getExtracted(substation)
                .stream()
                .filter((Fuse f) -> f.getStatus().isUncertain())
                .collect(Collectors.toList());
        substation.getGrid()
            .save(ExtracterUtils.getKeyUFuse(Fuse.class, substation), uFuses);
    }

    @Override
    public List<Fuse> getExtracted(Substation substation) {
        return null;
    }
}
