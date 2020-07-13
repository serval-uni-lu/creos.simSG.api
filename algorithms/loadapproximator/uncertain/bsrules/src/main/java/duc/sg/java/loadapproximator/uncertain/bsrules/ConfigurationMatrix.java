package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

import java.text.DecimalFormat;
import java.util.*;

public class ConfigurationMatrix implements Iterable<Configuration>{
    protected State[] states;
    protected Fuse[] columns;
    protected List<Double> confidences;
    protected int idxLineMaxClosed = -1;
    protected int maxClosedFuses = -1;

    public ConfigurationMatrix() {
        this.confidences = new ArrayList<>();
        this.states = new State[0];
        this.columns = new Fuse[0];
    }

    public ConfigurationMatrix(Fuse[] fuses) {
        this.states = new State[0];
        this.columns = fuses;
        this.confidences = new ArrayList<>();
    }


    @Override
    public Iterator<Configuration> iterator() {
        return new Iterator<>() {
            int idxLine;

            @Override
            public boolean hasNext() {
                return idxLine < nbConfigurations();
            }

            @Override
            public Configuration next() {
                int currIdx = idxLine;
                idxLine++;

                State[] line = new State[columns.length];
                System.arraycopy(states, currIdx*columns.length, line, 0, columns.length);
                return new Configuration(columns, line, confidences.get(currIdx));
            }
        };
    }


    public int nbConfigurations() {
        return confidences.size();
    }

    public void add(Map<Fuse, State> fuseStateMap, double confidence) {
        //create a new line
        State[] newStates = new State[states.length + columns.length];
        System.arraycopy(states, 0, newStates, 0, states.length);
        states = newStates;

        int idxLine = (states.length / columns.length) - 1;
        int nbClosedFuses = 0;


        // copy the values
        for (int idxColumn = 0; idxColumn < columns.length; idxColumn++) {
            State state = fuseStateMap.get(columns[idxColumn]);
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

    public void addConfToMaxOpen(double toAdd) {
        if(idxLineMaxClosed != -1) {
            double current = confidences.get(idxLineMaxClosed);
            confidences.set(idxLineMaxClosed, current + toAdd);
        }
    }

    public void add(ConfigurationMatrix other) {
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

        ConfigurationMatrix that = (ConfigurationMatrix) o;

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

        for(Configuration conf: this) {
            boolean exists = false;
            for(Configuration confThat: that) {
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
