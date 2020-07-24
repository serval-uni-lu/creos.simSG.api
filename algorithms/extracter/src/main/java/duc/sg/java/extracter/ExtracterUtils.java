package duc.sg.java.extracter;

import duc.sg.java.model.Substation;

class ExtracterUtils {
    private ExtracterUtils(){}

    static String KEY_BASE = "CycleFinder_";

    static String getKey(Class extractedClassElmt, Substation substation) {
        return KEY_BASE + extractedClassElmt.getSimpleName() + "_" + substation.getName();
    }

}
