package duc.sg.java.matrix.certain;

import duc.sg.java.model.Fuse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class Utils {
    private Utils(){}

    static Optional<Fuse> getOtherFusePara(Fuse current, List<Fuse> entFuses) {
        if(entFuses.size() < 2) return Optional.empty();

        var otherEntity = current.getOpposite().getOwner();
        for (var fuse: entFuses) {
            if(!fuse.equals(current)) {
                var oppEntity = fuse.getOpposite().getOwner();
                if(oppEntity.equals(otherEntity)) {
                    return Optional.of(fuse);
                }
            }
        }

        return Optional.empty();
    }

    static int getOrCreateIdx(Fuse fuse, Map<Fuse, Integer> map, int[] last) {
        map.computeIfAbsent(fuse, keyFuse -> ++last[0]);
        return map.get(fuse);
    }
}
