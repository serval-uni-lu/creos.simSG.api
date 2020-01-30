package duc.aintea.tests.utils;

import duc.aintea.tests.sg.Fuse;

import java.util.HashSet;
import java.util.Stack;

public class CycleDetection {

    public static boolean detectCircle(Fuse start) {
        var waitingFuses = new Stack<Fuse>();
        waitingFuses.add(start);

        var visitedFuses = new HashSet<Fuse>();

        var circle = new Stack<Fuse>();

        while(!waitingFuses.isEmpty()) {
            var current = waitingFuses.pop();
            var oppCurrent = current.getOpposite();
            if(!visitedFuses.contains(current)) {
                visitedFuses.add(current);
                visitedFuses.add(oppCurrent);
//                System.out.println("Visiting: " + current.getName() + "; Circle?: " + oppCurrent.getOwner().equals(start.getOwner()));

                if(oppCurrent.getOwner().equals(start.getOwner())) {
                    return true;
                }

            }


            var owner = oppCurrent.getOwner();
            for (var f: owner.getFuses()) {
                if(!visitedFuses.contains(f)) {
                    waitingFuses.add(f);
                }
            }

        }
        System.out.println();


       return false;
    }
}
