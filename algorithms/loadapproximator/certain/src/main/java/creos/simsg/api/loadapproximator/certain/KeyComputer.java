package creos.simsg.api.loadapproximator.certain;

import creos.simsg.api.model.Substation;

class KeyComputer {
    static String KEY_BASE = "CertainApproximator_";

    static String getKey(Substation substation) {
        return KEY_BASE + substation.getName();
    }
}
