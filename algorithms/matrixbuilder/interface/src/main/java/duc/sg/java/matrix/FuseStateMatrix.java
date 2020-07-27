package duc.sg.java.matrix;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;

public interface FuseStateMatrix {
    double[] getData();
    int getNbColumns();
    Fuse getFuse(int idx);
    Cable[] getCables();
}
