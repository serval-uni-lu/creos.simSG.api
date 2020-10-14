package duc.sg.java.uncertainty;

import duc.sg.java.uncertainty.math.UMath;
import duc.sg.java.utils.Pair;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class to represent an uncertain double with a set of values and their confidence.
 * The total of the confidence might not equal 1. As for {@link PossibilityDouble}, the remaining confidence
 * (1 - sum(confidences)) represent the unknown part of the value.
 */
public class MultiplePossibilities implements Iterable<PossibilityDouble> {
    private final List<PossibilityDouble> possibilities;

    public MultiplePossibilities() {
        possibilities = new ArrayList<>();
    }

    public MultiplePossibilities(List<PossibilityDouble> possibilities) {
        this.possibilities = possibilities;
    }

    public void addPossibility(PossibilityDouble toAdd) {
        possibilities.add(toAdd);
    }

    public List<PossibilityDouble> getPossibilities() {
        return Collections.unmodifiableList(possibilities);
    }


    /**
     * The values are just append trough the "addPossibility". However, several possibilities may share a common value.
     * This function merges the common values.
     *
     * @return a formatted version of the uncertain value
     */
    public MultiplePossibilities format() {
        var res = new ArrayList<PossibilityDouble>(possibilities.size());
        var possSet = new HashMap<Double, Integer>(possibilities.size());

        for(PossibilityDouble poss: possibilities) {
            double value = Math.round(poss.getValue()*100)/100.; //todo fix this dirty workaround....
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


        return new MultiplePossibilities(res);
    }

    /**
     * Format the uncertain value and map the confidence levels to a {@link Category}
     *
     * @return formatted uncertian double
     */
    public List<Pair<Double, Category>> formatWithCategory() {
        var res = new ArrayList<Pair<Double, Category>>();

        for(PossibilityDouble poss: format()) {
           res.add(new Pair<>(poss.getValue(), Category.probToCategory(poss.getConfidence().probability)));
        }

        return res;
    }

    @Override
    public String toString() {
        return "MultiplePossibilities(" +
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
