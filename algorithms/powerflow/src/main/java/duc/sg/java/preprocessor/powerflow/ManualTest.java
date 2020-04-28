package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class ManualTest {

    private static boolean contain(Fuse[] src, Fuse toSearch) {
        for(var s: src) {
            if(s.equals(toSearch)) {
                return true;
            }
        }

        return false;
    }


    private static void printShouldBeClosed(Substation substation) {
        InitPowerFlows2.initPowerFlow(substation);


        Collection<Fuse> fuses = substation.extractFuses();
        var shouldBeOpen = new HashSet<Fuse>();
        for(var fuse: fuses) {
            if(!(fuse.getOwner() instanceof Substation)) {
                Fuse[] powerSrc = fuse.getPowerFrom();
                Fuse[] powerDest = fuse.givePowerTo();

//                System.out.println(fuse.getName() + " -> ");
//                System.out.println("\t (src) " + Arrays.toString(Stream.of(powerSrc).map(Fuse::getName).toArray()));
//                System.out.println("\t (dest) " + Arrays.toString(Stream.of(powerDest).map(Fuse::getName).toArray()));

                var filteredPowerSrc = new ArrayList<Fuse>(powerSrc.length);

                for(Fuse src: powerSrc) {
                    if(!contain(powerDest, src) && !(src.getOwner() instanceof Substation)) {
                        filteredPowerSrc.add(src);
                    }
                }
                if(filteredPowerSrc.size() == 1) {
                    shouldBeOpen.add(filteredPowerSrc.get(0));
                }
            }

        }

        System.out.println();
        shouldBeOpen.stream().map(Fuse::getName).forEach(System.out::println);

    }




    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
//                .chooseScenario(ScenarioName.CABINET)
//                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
//                .chooseScenario(ScenarioName.PARA_CABINET)
//                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .build();

        printShouldBeClosed(sc.getSubstation());
        System.out.println();

        Fuse[] toOPen = new PowerFLow().getFuseOnMandatoryPF(sc.getSubstation());
        Stream.of(toOPen)
                .map(Fuse::getName)
                .forEach(System.out::println);

//        InitPowerFlows.initPowerFlow(sc.getSubstation());
//        InitPowerFlows2.initPowerFlow(sc.getSubstation());
//
//        Fuse[] fuses = sc.extractFuses();
//        for(var f: fuses) {
//            var printPowerSrc = new StringBuilder();
//            printPowerSrc.append(f.getName())
//                    .append(" <- [");
//
//            Fuse[] powerFrom = f.getPowerFrom();
//            for (int i = 0; i < powerFrom.length; i++) {
//                printPowerSrc.append(powerFrom[i].getName());
//                if(i < powerFrom.length - 1) {
//                    printPowerSrc.append(", ");
//                }
//            }
//            printPowerSrc.append("]");
//
//
//
//            var printPowerDest = new StringBuilder();
//            printPowerDest.append(f.getName())
//                    .append(" -> [");
//
//            Fuse[] powerDest = f.givePowerTo();
//            for (int i = 0; i < powerDest.length; i++) {
//                printPowerDest.append(powerDest[i].getName());
//                if(i < powerDest.length - 1) {
//                    printPowerDest.append(", ");
//                }
//            }
//            printPowerDest.append("]");
//
//
//
//
//
//            System.out.println(printPowerSrc.toString());
//            System.out.println(printPowerDest.toString());
//            System.out.println();
//        }


    }
}
