package duc.sg.java.navigation.bfs;

import java.util.Set;

@FunctionalInterface
public interface Condition<CT, ST> {
    boolean evaluate(CT element, Set<ST> visited);
}
