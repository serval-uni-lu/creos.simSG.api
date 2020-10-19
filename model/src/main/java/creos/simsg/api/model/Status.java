package creos.simsg.api.model;

import creos.simsg.api.uncertainty.Bernoulli;
import creos.simsg.api.uncertainty.Confidence;

/**
 * Abstracts the uncertain status of fuses. The uncertainty is represented by a
 * <a href="https://en.wikipedia.org/wiki/Bernoulli_distribution">Bernoulli distribution</a>. Indeed, this
 * distribution allows us to represent the confidence that is shared between exactly two opposite values: closed
 * and open.
 *
 * The confidence level is between 0 and 1.
 *
 * We assume that the fuse state it's either closed or open. Therefore, knowing that a fuse is closed with
 * 65% confidence is equivalent than knowing that a fuse is open with 35% (100% - 65%) confidence.
 *
 */
public class Status {
    private State state;
    private final Bernoulli confidence;

    public Status(State state) {
       this(state, Confidence.MAX_PROBABILITY);
    }

    public Status(State state, double confidence) {
        this.state = state;
        this.confidence = new Bernoulli(confidence);
    }

    public State getState() {
        return state;
    }

    public void setConfIsClosed(double confidence) {
        double realConf = isClosed()? confidence : Bernoulli.getOpposite(confidence);
        this.confidence.setProbability(realConf);
    }

    public void setConfIsOpen(double confidence) {
        double realConf = isClosed()? Bernoulli.getOpposite(confidence) : confidence;
        this.confidence.setProbability(realConf);
    }

    public double confIsClosed() {
        return isClosed()? confidence.getProbability(): confidence.getOppProbability();
    }

    public double confIsOpen() {
        return (!isClosed())? confidence.getProbability(): confidence.getOppProbability();
    }

    public boolean isCertain() {
        return confidence.probIsMax() || confidence.oppIsMax();
    }

    public boolean isUncertain() {
        return !isCertain();
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
