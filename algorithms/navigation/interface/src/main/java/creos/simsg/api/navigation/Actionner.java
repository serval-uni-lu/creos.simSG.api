package creos.simsg.api.navigation;


@FunctionalInterface
public interface Actionner<T> {
    void act(T current);
}
