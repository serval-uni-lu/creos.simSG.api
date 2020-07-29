package duc.sg.java.extracter;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.bfs.BFSFuse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuseExtracter implements Extracter<Fuse> {
    private FuseExtracter(){}

    public static final FuseExtracter INSTANCE = new FuseExtracter();

    @Override
    public void extractAndSave(Substation substation) {
        List<Fuse> allFuses = new ArrayList<>();
        BFSFuse.INSTANCE.navigate(substation, allFuses::add);
        substation.getGrid()
                .save(ExtracterUtils.getKeyCertain(Fuse.class, substation), allFuses);
    }

    @Override
    public List<Fuse> getExtracted(Substation substation) {
        String key = ExtracterUtils.getKeyCertain(Fuse.class, substation);
        Optional<Object> optFuses = substation.getGrid().retrieve(key);
        if(optFuses.isEmpty()) {
            extractAndSave(substation);
            optFuses = substation.getGrid().retrieve(key);
        }

        return (List<Fuse>) optFuses.get();
    }
}
