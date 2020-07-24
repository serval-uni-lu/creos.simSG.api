package duc.sg.java.navigation;

import duc.sg.java.model.Substation;

public interface Navigate<T> {
    void navigate(Substation substation, Actionner<T> actionner);
}
