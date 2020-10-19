package creos.simsg.api.navigation;

import creos.simsg.api.model.Substation;

public interface Navigate<T> {
    void navigate(Substation substation, Actionner<T> actionner, Condition condition);

    default void navigate(Substation substation, Actionner<T> actionner) {
        navigate(substation, actionner, (ignored) -> true);
    }
}
