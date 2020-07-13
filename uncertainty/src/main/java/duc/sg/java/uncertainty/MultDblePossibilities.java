package duc.sg.java.uncertainty;

import duc.sg.java.uncertainty.math.UMath;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class MultDblePossibilities implements Iterable<PossibilityDouble> {
    private final List<PossibilityDouble> possibilities;
    private final Map<Double, Integer> possIdx;

    public MultDblePossibilities() {
        possibilities = new ArrayList<>();
        possIdx = new HashMap<>();
    }

    public MultDblePossibilities(MultDblePossibilities other) {
        this();
        for(PossibilityDouble poss: other) {
            addOrReplace(new PossibilityDouble(poss));
        }
    }

    public void addOrReplace(PossibilityDouble value) {
        compute(value, current -> value);
    }

    public void add(PossibilityDouble value) {
        compute(value, (PossibilityDouble current) -> UMath.or(value, current));
    }

//    public void removeIf(double value, Predicate<PossibilityDouble> condition) {
//        Integer idx = possIdx.get(value);
//        if(idx != null && condition.test(possibilities.get(idx))) {
//            possibilities.remove((int) idx);
//            possIdx.remove(value);
//        }
//    }

    public void compute(double value, UnaryOperator<PossibilityDouble> toCompute) {
        compute(new PossibilityDouble(value, Confidence.MAX_PROBABILITY), toCompute);
    }

    public void compute(PossibilityDouble value, UnaryOperator<PossibilityDouble> toCompute) {
        Integer idx = possIdx.get(value.getValue());
        PossibilityDouble newVal;
        if(idx == null) {
            newVal = toCompute.apply(null);
            if(newVal != null) {
                possIdx.put(newVal.getValue(), possibilities.size());
                possibilities.add(newVal);
            }
        } else {
            newVal = toCompute.apply(possibilities.get(idx));
            if(newVal == null) {
                possibilities.remove((int) idx);
                possIdx.remove(value.getValue());
            } else {
                possIdx.put(newVal.getValue(), idx);
                possibilities.set(idx, newVal);
            }
        }
    }

    public List<PossibilityDouble> getPossibilities() {
        return Collections.unmodifiableList(possibilities);
    }


    @Override
    public String toString() {
        return "MultDblePossibilities(" +
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

    public Map<Category, Double> format() {
        Map<Category, Double> values = new EnumMap<>(Category.class);

        possibilities.forEach((PossibilityDouble poss) -> {
            Category category = Category.probToCategory(poss.getConfidence().getProbability());
            values.compute(category, (key, current) -> {
                double toAdd = poss.getValue() * poss.getConfidence().probability;
                return current == null ? toAdd : current + toAdd;
            });
        });
        return values;
    }
}
