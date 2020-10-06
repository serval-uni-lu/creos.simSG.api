package duc.sg.java.extracter;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.bfs.BFSFuse;

import java.util.*;

public class EntityExtracter implements Extracter<Entity> {
    public static final EntityExtracter INSTANCE = new EntityExtracter();

    private EntityExtracter(){}

    @Override
    public void extractAndSave(Substation substation) {
        Set<Entity> allEntities = new HashSet<>();
        BFSFuse.INSTANCE.navigate(substation, (Fuse fuse) -> allEntities.add(fuse.getOwner()));
        substation.getGrid()
                .save(ExtracterUtils.getKeyCertain(Entity.class, substation), new ArrayList<>(allEntities));
    }

    @Override
    public List<Entity> getExtracted(Substation substation) {
        String key = ExtracterUtils.getKeyCertain(Entity.class, substation);
        Optional<Object> optEntities = substation.getGrid().retrieve(key);
        if(optEntities.isEmpty()) {
            extractAndSave(substation);
            optEntities = substation.getGrid().retrieve(key);
        }
        return (List<Entity>) optEntities.get();
    }
}
