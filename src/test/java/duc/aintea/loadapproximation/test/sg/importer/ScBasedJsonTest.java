package duc.aintea.loadapproximation.test.sg.importer;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.importer.ScBasedJson;
import duc.aintea.sg.scenarios.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ScBasedJsonTest {


    public static Arguments[] getInvalidJsonFiles() {
        var rootFolder = ScBasedJsonTest.class
                .getClassLoader()
                .getResource("invalidJson")
                .getPath();
        var path = Paths.get(rootFolder);

        final var arsList = new ArrayList<Arguments>();

        try {
            Files.walkFileTree(path, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                   arsList.add(Arguments.of(file.toFile()));
                   return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    throw exc;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            //should not happen
        }

       return arsList.toArray(new Arguments[0]);

    }



    @ParameterizedTest
    @MethodSource("getInvalidJsonFiles")
    public void testInvalidFile(File jsonFile) {
        assertDoesNotThrow(() -> {
            Optional<Substation> substation = ScBasedJson.from(jsonFile);
            assertTrue(substation.isEmpty());
        });
    }

    private File getValidFile(String fileName) {
        String jsonPath = ScBasedJsonTest.class
                .getClassLoader()
                .getResource("validJson/scBased/" + fileName)
                .getPath();
        return new File(jsonPath);
    }

    private Substation testNoException(String fileName) {
        File jsonFIle = getValidFile(fileName);
        final Substation[] substation = new Substation[1];
        assertDoesNotThrow(() -> {
            Optional<Substation> opt = ScBasedJson.from(jsonFIle);
            assertTrue(opt.isPresent());
            substation[0] = opt.get();
        });
        return substation[0];
    }

    private void genericTest(Cable[] cables, double[] consumptions, Fuse[] fuses, boolean[] fuseStatus, boolean[] uncertainFuses) {
        for (int i = 0; i < cables.length; i++) {
            assertEquals(consumptions[i], cables[i].getConsumption());
        }

        for (int i = 0; i < fuses.length; i++) {
            assertEquals(fuses[i].isClosed(), fuseStatus[i], i + "");
            assertEquals(fuses[i].getStatus().isUncertain(), uncertainFuses[i], i + "");
        }
    }

    @Test
    public void testValidSc1() {
        Substation substation = testNoException("sc1.json");

        Cable[] cables = SingleCableBuilder.extractCables(substation);
        double[] expCons = new double[]{14.0};

        var fuseStatus = new boolean[]{true, false};
        var fIsUc = new boolean[]{true, false};

        Fuse[] fuses = SingleCableBuilder.extractFuses(substation);

        genericTest(cables, expCons, fuses, fuseStatus, fIsUc);

        assertEquals(0.5, fuses[0].getStatus().getConfClosedAsProb());
    }


    @Test
    public void testValidSc2() {
        Substation substation = testNoException("sc2.json");

        Cable[] cables = CabinetBuilder.extractCables(substation);
        double[] expCons = new double[]{14.0, 20.6, 168.2};

        var fuseStatus = new boolean[6];
        Arrays.fill(fuseStatus, true);
        fuseStatus[0] = fuseStatus[3] = false;

        var fIsUc = new boolean[6];
        Arrays.fill(fIsUc, false);
        fIsUc[2] = true;

        Fuse[] fuses = CabinetBuilder.extractFuses(substation);

        genericTest(cables, expCons, fuses, fuseStatus, fIsUc);

        assertEquals(0.8, fuses[2].getStatus().getConfClosedAsProb());
    }


    @Test
    public void testValidSc3() {
        Substation substation = testNoException("sc3.json");

        Cable[] cables = ParaTransformerBuilder.extractCables(substation);
        double[] expCons = new double[]{14.0, 20.6, 168.2};

        var fuseStatus = new boolean[6];
        Arrays.fill(fuseStatus, true);
        fuseStatus[0] = fuseStatus[1] = fuseStatus[5] =false;

        var fIsUc = new boolean[6];
        Arrays.fill(fIsUc, false);
        fIsUc[1] = true;

        Fuse[] fuses = ParaTransformerBuilder.extractFuses(substation);

        genericTest(cables, expCons, fuses, fuseStatus, fIsUc);

        assertEquals(0.8, fuses[1].getStatus().getConfOpenAsProb());
    }

    @Test
    public void testValidSc4() {
        Substation substation = testNoException("sc4.json");

        Cable[] cables = ParaCabinetBuilder.extractCables(substation);
        double[] expCons = new double[]{14.0, 20.6, 168.2, 658};

        var fuseStatus = new boolean[8];
        Arrays.fill(fuseStatus, true);
        fuseStatus[0] = fuseStatus[5] = fuseStatus[2] = fuseStatus[1] = fuseStatus[3] =false;

        var fIsUc = new boolean[8];
        Arrays.fill(fIsUc, false);
        fIsUc[1] = fIsUc[3] = true;

        Fuse[] fuses = ParaCabinetBuilder.extractFuses(substation);

        genericTest(cables, expCons, fuses, fuseStatus, fIsUc);

        assertEquals(0.8, fuses[1].getStatus().getConfOpenAsProb());
        assertEquals(0.1, fuses[3].getStatus().getConfOpenAsProb());
    }

    @Test
    public void testValidSc5() {
        Substation substation = testNoException("sc5.json");

        Cable[] cables = IndirectParaBuilder.extractCables(substation);
        double[] expCons = new double[]{14.0, 20.6, 168.2, 658, 12.0};

        var fuseStatus = new boolean[10];
        Arrays.fill(fuseStatus, true);
        fuseStatus[0] = fuseStatus[5] = fuseStatus[2] = fuseStatus[1] = false;

        var fIsUc = new boolean[10];
        Arrays.fill(fIsUc, false);
        fIsUc[1] = fIsUc[3] = true;

        Fuse[] fuses = IndirectParaBuilder.extractFuses(substation);

        genericTest(cables, expCons, fuses, fuseStatus, fIsUc);

        assertEquals(0.8, fuses[1].getStatus().getConfOpenAsProb());
        assertEquals(0.1, fuses[3].getStatus().getConfClosedAsProb());
    }

}
