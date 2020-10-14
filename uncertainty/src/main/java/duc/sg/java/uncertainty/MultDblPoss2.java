package duc.sg.java.uncertainty;

import duc.sg.java.uncertainty.math.UMath;

import java.util.*;
import java.util.function.Consumer;

public class MultDblPoss2 implements Iterable<PossibilityDouble> {
    private final List<PossibilityDouble> possibilities;

    public MultDblPoss2() {
        possibilities = new ArrayList<>();
    }

    public MultDblPoss2(List<PossibilityDouble> possibilities) {
        this.possibilities = possibilities;
    }

    public void addPossibility(PossibilityDouble toAdd) {
        possibilities.add(toAdd);
    }

    public List<PossibilityDouble> getPossibilities() {
        return Collections.unmodifiableList(possibilities);
    }


    public MultDblPoss2 format() {
        var res = new ArrayList<PossibilityDouble>(possibilities.size());
        var possSet = new HashMap<Double, Integer>(possibilities.size());

        for(PossibilityDouble poss: possibilities) {
            double value = Math.round(poss.getValue()*100)/100.; //dirty workaround....
            PossibilityDouble rounded = new PossibilityDouble(value, poss.getConfidence());

            if(possSet.containsKey(value)) {
                int idx = possSet.get(value);
                PossibilityDouble current = res.get(idx);
                res.set(idx, UMath.or(current, rounded));
            } else {
                res.add(rounded);
                possSet.put(value, res.size() - 1);
            }
        }


        return new MultDblPoss2(res);
    }

    @Override
    public String toString() {
        return "MultDblPoss2(" +
                "possibilities=" + Arrays.toString(format().possibilities.toArray()) +
                ')';
    }

    @Override
    public Iterator<PossibilityDouble> iterator() {
        return possibilities.iterator();
    }

    @Override
    public void forEach(Consumer<? super PossibilityDouble> action) {
        possibilities.forEach(action);
    }

    @Override
    public Spliterator<PossibilityDouble> spliterator() {
        return possibilities.spliterator();
    }
}
