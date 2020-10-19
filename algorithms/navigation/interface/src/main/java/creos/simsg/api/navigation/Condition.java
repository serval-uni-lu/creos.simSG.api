package creos.simsg.api.navigation;

import creos.simsg.api.model.Fuse;

@FunctionalInterface
public interface Condition {
    boolean evaluate(Fuse entFuse);
}
