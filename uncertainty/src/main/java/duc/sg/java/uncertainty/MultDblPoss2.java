package duc.sg.java.uncertainty;

import java.util.*;
import java.util.function.Consumer;

public class MultDblPoss2 implements Iterable<PossibilityDouble> {
    private List<PossibilityDouble> possibilities;

    public MultDblPoss2() {
        possibilities = new ArrayList<>();
    }

    public void addPossibility(PossibilityDouble toAdd) {
        possibilities.add(toAdd);
    }

    public List<PossibilityDouble> getPossibilities() {
        return Collections.unmodifiableList(possibilities);
    }

    @Override
    public String toString() {
        return "MultDblPoss2(" +
                "possibilities=" + Arrays.toString(possibilities.toArray()) +
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
