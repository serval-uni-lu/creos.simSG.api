package duc.sg.java.grid.uncertainty.configuration;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

import java.text.DecimalFormat;
import java.util.*;

/**
 * List of UConfigurationMatrix
 * The sum of the confidence levels should not exceed 1.
 */
public class UConfigurationList implements Iterable<UConfiguration>{
    protected State[] states;
    protected Fuse[] columns;
    protected List<Double> confidences;

    private int idxLineMaxClosed = -1;
    private int maxClosedFuses = -1;

    /**
     * Init an empty configuration.
     *
     * **WARNING**: after having initialised the uconfiguration list with the default constructor, one cannot
     * add a configuration with fuses.
     * But it can be combined with another configuration using {@link UConfigurationList#add(UConfigurationList)}
     *
     */
    public UConfigurationList() {
        this.confidences = new ArrayList<>();
        this.states = new State[0];
        this.columns = new Fuse[0];
    }



    /**
     * Init the uconfiguration list for the given fuses
     *
     * @param fuses Fuses implied in the configuration
     */
    public UConfigurationList(Fuse[] fuses) {
        this.states = new State[0];
        this.columns = fuses;
        this.confidences = new ArrayList<>();
    }

    public List<Double> getConfidences() {
        return confidences;
    }

    @Override
    public Iterator<UConfiguration> iterator() {
        return new Iterator<>() {
            int idxLine;

            @Override
            public boolean hasNext() {
                return idxLine < nbConfigurations();
            }

            @Override
            public UConfiguration next() {
                int currIdx = idxLine;
                idxLine++;

                State[] line = new State[columns.length];
                System.arraycopy(states, currIdx*columns.length, line, 0, columns.length);
                return new UConfiguration(columns, line, confidences.get(currIdx));
            }
        };
    }

    public int nbConfigurations() {
        return confidences.size();
    }

    /**
     * Add a configuration to the list. The configuration should contain all fuses with which the list has been
     * initialised.
     *
     * @param configuration configuration
     * @param confidence confidence of the configuration
     */
    public void add(Configuration configuration, double confidence) {
        //create a new line
        State[] newStates = new State[states.length + columns.length];
        System.arraycopy(states, 0, newStates, 0, states.length);
        states = newStates;

        int idxLine = (states.length / columns.length) - 1;
        int nbClosedFuses = 0;


        // copy the values
        for (int idxColumn = 0; idxColumn < columns.length; idxColumn++) {
            State state = configuration.getState(columns[idxColumn]);
            states[idxLine*columns.length + idxColumn] = state;

            if(state == State.CLOSED) {
                nbClosedFuses++;
            }
        }

        //save confidence
        confidences.add(confidence);

        // update max
        if(nbClosedFuses> maxClosedFuses) {
            maxClosedFuses = nbClosedFuses;
            idxLineMaxClosed = idxLine;
        }

    }

    public void addConfToMaxClosed(double toAdd) {
        if(idxLineMaxClosed != -1) {
            double current = confidences.get(idxLineMaxClosed);
            confidences.set(idxLineMaxClosed, current + toAdd);
        }
    }

    /**
     * Combine the given configuration to the current one. This combination is done by computing the cartesian
     * product of the two configurations. The new confidence is computed by multiplying the confidence of each
     * configuration.
     *
     * @param other the other configuration
     */
    public void add(UConfigurationList other) {
        if(this.confidences.isEmpty()) {
            this.columns = other.columns;
            this.confidences = other.confidences;
            this.states = other.states;
            return;
        }


        // merge columns
        var newColumns = new Fuse[columns.length + other.columns.length];
        System.arraycopy(columns, 0, newColumns, 0, columns.length);
        System.arraycopy(other.columns, 0, newColumns, columns.length, other.columns.length);

        int nbNewLinew = confidences.size() * other.confidences.size();
        int nbNewCols = newColumns.length;

        var newStates = new State[nbNewLinew * nbNewCols];
        var newConf= new ArrayList<Double>();
        for (int idxLineThis = 0; idxLineThis < confidences.size(); idxLineThis++) {
            for (int idxLineOther = 0; idxLineOther < other.confidences.size(); idxLineOther++) {
                int idxLine = idxLineThis*other.confidences.size() + idxLineOther;
                //Copy line this
                System.arraycopy(
                        states,
                        idxLineThis * columns.length,
                        newStates,
                        idxLine * newColumns.length,
                        columns.length
                );
                System.arraycopy(
                        other.states,
                        idxLineOther*other.columns.length,
                        newStates,
                        idxLine * newColumns.length + columns.length,
                        other.columns.length
                );

                newConf.add(confidences.get(idxLineThis) * other.confidences.get(idxLineOther));
            }
        }

        this.confidences = newConf;
        this.columns = newColumns;
        this.states = newStates;

    }


    @Override
    public String toString() {
       StringBuilder string = new StringBuilder();

       String format = "%10s";
       DecimalFormat dblFormat = new DecimalFormat("#.00");

       for(Fuse f: columns) {
           string.append(String.format(format, f.getName()));
       }
       string.append(String.format(format, "Conf. (%)"));
       string.append("\n");

        for (int i = 0; i < states.length; i++) {
            string.append(String.format(format, states[i]));

            if((i+1) % columns.length == 0) {
                double confidence = confidences.get((int) i / columns.length) * 100;
                string.append(String.format(format, dblFormat.format(confidence)));
                string.append("\n");
            }

        }

       return string.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UConfigurationList that = (UConfigurationList) o;

        // Check equality of columns
        var currentHashed = new HashSet<Fuse>();
        Collections.addAll(currentHashed, columns);
        for(Fuse oCol: that.columns) {
            if(!currentHashed.contains(oCol)) {
                return false;
            }
        }

        // Check equality of confidence
        if(confidences.size() != that.confidences.size()) {
            return false;
        }

        for(UConfiguration conf: this) {
            boolean exists = false;
            for(UConfiguration confThat: that) {
                if(confThat.equals(conf)) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                return false;
            }

        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxClosedFuses);
    }
}
