package duc.sg.java.extracter;

import duc.sg.java.model.Substation;

import java.util.List;

public interface Extracter<T> {
    void extractAndSave(Substation substation);
    List<T> getExtracted(Substation substation);
}
