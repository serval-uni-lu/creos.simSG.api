package duc.sg.java.matrix;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;

public interface FuseStateMatrix {
    double[] getData();
    int getNbColumns();
    Integer getFuseIdx(Fuse fuse);
    Fuse getFuse(int idx);
    Cable[] getCables();
}
