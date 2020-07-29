package duc.sg.java.loadapproximator.loadapproximation;

import duc.sg.java.model.Substation;

class KeyComputer {
    static String KEY_BASE = "CertainApproximator_";

    static String getKey(Substation substation) {
        return KEY_BASE + substation.getName();
    }
}
