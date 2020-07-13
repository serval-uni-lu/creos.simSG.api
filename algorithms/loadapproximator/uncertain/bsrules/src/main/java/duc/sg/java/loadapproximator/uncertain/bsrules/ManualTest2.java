package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

import java.util.HashMap;

public class ManualTest2 {

    public static void main(String[] args) {

        var cols = new Fuse[] {
                new Fuse("i1"),
                new Fuse("i4")
        };
        ConfigurationMatrix conf1 = new ConfigurationMatrix(cols);

        var line1 = new HashMap<Fuse, State>();
        line1.put(cols[0], State.CLOSED);
        line1.put(cols[1], State.CLOSED);
        conf1.add(line1, 0.6856);

        var line2 = new HashMap<Fuse, State>();
        line2.put(cols[0], State.CLOSED);
        line2.put(cols[1], State.OPEN);
        conf1.add(line2, 0.1422);

        var line3 = new HashMap<Fuse, State>();
        line3.put(cols[0], State.OPEN);
        line3.put(cols[1], State.CLOSED);
        conf1.add(line3, 0.1722);

        System.out.println(conf1);

        var cols2 = new Fuse[] {
                new Fuse("i7")
        };
        ConfigurationMatrix conf2 = new ConfigurationMatrix(cols2);

        var line12 = new HashMap<Fuse, State>();
        line12.put(cols2[0], State.CLOSED);
        conf2.add(line12, 0.64);

        var line22 = new HashMap<Fuse, State>();
        line22.put(cols2[0], State.OPEN);
        conf2.add(line22, 0.36);


        System.out.println(conf2);

        conf1.add(conf2);

        System.out.println(conf1);


    }
}
