package creos.simsg.api.matrix.certain;

import creos.simsg.api.model.*;
import creos.simsg.api.model.*;
import org.junit.jupiter.api.Test;

public class ParaCableInnerIndirectCircle extends MatrixBuilderTest {
    private static final String F1_NAME = "f1";
    private static final String F2_NAME = "f2";
    private static final String F3_NAME = "f3";
    private static final String F4_NAME = "f4";
    private static final String F5_NAME = "f5";
    private static final String F6_NAME = "f6";
    private static final String F7_NAME = "f7";
    private static final String F8_NAME = "f8";
    private static final String F9_NAME = "f9";
    private static final String F10_NAME = "f10";
    private static final String F11_NAME = "f11";
    private static final String F12_NAME = "f12";
    private static final String F13_NAME = "f13";
    private static final String F14_NAME = "f14";

    @Override
    protected void createSubstation() {
        SmartGrid grid = new SmartGrid();
        substation = new Substation("subs1");
        grid.addSubstations(substation);

        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c4 = new Cabinet("c4");
        Cabinet c5 = new Cabinet("c5");

        Fuse f1 = new Fuse(F1_NAME);
        Fuse f2 = new Fuse(F2_NAME);
        Fuse f3 = new Fuse(F3_NAME);
        Fuse f4 = new Fuse(F4_NAME);
        Fuse f5 = new Fuse(F5_NAME);
        Fuse f6 = new Fuse(F6_NAME);
        Fuse f7 = new Fuse(F7_NAME);
        Fuse f8 = new Fuse(F8_NAME);
        Fuse f9 = new Fuse(F9_NAME);
        Fuse f10 = new Fuse(F10_NAME);
        Fuse f11 = new Fuse(F11_NAME);
        Fuse f12 = new Fuse(F12_NAME);
        Fuse f13 = new Fuse(F13_NAME);
        Fuse f14 = new Fuse(F14_NAME);

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl6 = new Cable();
        Cable cbl7 = new Cable();

        substation.addFuses(f1);
        c1.addFuses(f2, f3, f7);
        c2.addFuses(f4, f5);
        c3.addFuses(f8, f9, f11);
        c4.addFuses(f10, f12, f13);
        c5.addFuses(f6, f14);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl6.setFuses(f11, f12);
        cbl7.setFuses(f13, f14);
    }

    @Test
    public void sc1_allClosed() {
        var expected = new double[]{
                1,1,0,0,0,0,0,0,0,0,0,0,0,0,
                0,1,1,0,1,0,0,0,0,0,0,0,0,0,
                0,0,1,1,0,0,0,0,0,0,0,0,0,0,
                0,0,1,0,-1,0,0,0,0,0,0,0,0,0,
                0,0,0,1,0,1,0,0,0,0,0,0,0,0,
                0,0,0,0,0,1,1,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,1,1,0,1,0,0,0,
                0,0,0,0,1,0,0,1,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,1,1,0,0,0,0,
                0,0,0,0,0,0,0,0,1,0,-1,0,0,0,
                0,0,0,0,0,0,1,0,0,0,0,1,0,0,
                0,0,0,0,0,0,0,0,0,0,0,1,1,0,
                0,0,0,0,0,0,0,0,0,1,0,0,1,1,
                0,0,0,0,0,0,0,0,0,0,1,0,0,1
        };
        genericTest(expected);
    }
}
