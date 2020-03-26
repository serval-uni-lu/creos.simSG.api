package duc.sg.java.model;

import duc.sg.java.uncertainty.Bernoulli;

public class Status {
    private State state;
    private Bernoulli confidence;

    public Status(State state, double confidence) {
        this.state = state;
        this.confidence = new Bernoulli();
        this.confidence.setProbability(confidence);
    }

    public void setConfIsClosed(double confidence) {
        double realConf = isClosed()? confidence : Bernoulli.getOpposite(confidence);
        this.confidence.setProbability(realConf);
    }

    public double confIsClosed() {
        return isClosed()? confidence.getProbability(): confidence.getOppProbability();
    }

    public double confIsOpen() {
        return (!isClosed())? confidence.getProbability(): confidence.getOppProbability();
    }

    public boolean isCertain() {
        return confidence.probIsMax();
    }

    public boolean isUncertain() {
        return !confidence.probIsMax();
    }


    public void makeCertain() {
        this.confidence.setMaxProbability();
    }

    @Override
    public String toString() {
        return "State(" +
                "state=" + state +
                ", confidence=" + confidence +
                ')';
    }

    public void close() {
        if (!isClosed()) {
            state = State.CLOSED;
            confidence.inverse();
        }
    }

    public void open() {
        if (isClosed()) {
            state = State.OPEN;
            confidence.inverse();
        }
    }

    public boolean isClosed() {
        return state == State.CLOSED;
    }
}
