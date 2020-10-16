package duc.sg.java.matrix.certain;

import duc.sg.java.extracter.FuseExtractor;
import duc.sg.java.matrix.EquationMatrix;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public abstract class MatrixBuilderTest {
    protected Substation substation;
    private Map<String, Fuse> fusesMap;


    @BeforeEach
    public void init() {
        createSubstation();
        initFuseMap();
    }

    protected abstract void createSubstation();

    protected void initFuseMap() {
        fusesMap = new HashMap<>();

        FuseExtractor.INSTANCE
                .getExtracted(substation)
                .forEach((Fuse f) -> fusesMap.put(f.getName(), f));
    }

    protected void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    private Configuration generateConfiguration(String[] toOpen) {
        var conf = new HashMap<Fuse, State>();

        FuseExtractor.INSTANCE
                .getExtracted(substation)
                .forEach((Fuse f) -> conf.put(f, State.CLOSED));

        for(String to: toOpen) {
            conf.put(fusesMap.get(to), State.OPEN);
        }


        return new Configuration(conf);
    }

    protected void genericTest(double[] expected, String... toOpen) {
        CertainMatrixBuilder builder = new CertainMatrixBuilder();

        Configuration configuration = generateConfiguration(toOpen);
        EquationMatrix matrix = builder.build(substation, configuration)[0];
        Assertions.assertArrayEquals(expected,  matrix.getValues());


        openFuses(toOpen);
        matrix = new CertainMatrixBuilder().build(substation)[0];
        Assertions.assertArrayEquals(expected,  matrix.getValues());
    }
}
