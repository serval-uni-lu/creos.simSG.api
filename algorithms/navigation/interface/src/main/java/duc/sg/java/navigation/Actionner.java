package duc.sg.java.navigation;


import java.util.Set;

@FunctionalInterface
public interface Actionner<T> {
    void act(T current, Set<T> visited);
}
