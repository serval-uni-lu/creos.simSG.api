package duc.sg.java.navigation.bfs;

@FunctionalInterface
public interface Condition<CT> {
    boolean evaluate(CT element);
}
