package duc.sg.java.extractor;

import duc.sg.java.model.Substation;

class ExtracterUtils {
    private ExtracterUtils(){}

    static String KEY_FUSE_BASE = "Extracter_Certain_";
    static String KEY_UFUSE_BASE = "Extracter_Uncertain_";
    static String KEY_CONF_BASE = "Extracter_Configuration_";

    static String getKeyCertain(Class<?> extractedClassElmt, Substation substation) {
        return KEY_FUSE_BASE + extractedClassElmt.getSimpleName() + substation.getName();
    }

    static String getKeyUncertain(Class<?> extractedClassElmt, Substation substation) {
        return KEY_UFUSE_BASE + extractedClassElmt.getSimpleName() + substation.getName();
    }

    static String getKeyConf(Substation substation) {
        return KEY_CONF_BASE + substation.getName();
    }

}
