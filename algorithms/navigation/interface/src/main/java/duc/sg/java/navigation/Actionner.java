package duc.sg.java.navigation;


@FunctionalInterface
public interface Actionner<T> {
    void act(T current);
}
