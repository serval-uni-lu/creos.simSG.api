package duc.sg.java.navigation;

import duc.sg.java.model.Fuse;

@FunctionalInterface
public interface Condition {
    boolean evaluate(Fuse entFuse);
}
