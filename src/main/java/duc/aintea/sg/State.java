package duc.aintea.sg;

public class State {
   private boolean isClosed;
    private double confidence;

    public State(boolean isClosed, double confidence) {
        this.isClosed = isClosed;
        this.confidence = confidence;
    }

    public void setConfAsProb(double confidence) {
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("Confidence should be between 0 and 1.");
        }
        this.confidence = confidence;
    }

    public void setConfAsPercentage(double confidence) {
        if (confidence < 0 || confidence > 100) {
            throw new IllegalArgumentException("Confidence should be between 0 and 1.");
        }
        this.confidence = confidence / 100;
    }

    public double getConfClosedAsProb() {
        if (isClosed) {
            return confidence;
        }
        return 1 - confidence;
    }

    public double getConfOpenAsProb() {
        if (!isClosed) {
            return confidence;
        }
        return 1 - confidence;
    }

    public boolean isCertain() {
        return confidence == 1;
    }

    public boolean isUncertain() {
        return confidence != 1;
    }



    public void makeCertain() {
        this.confidence = 1;
    }

    @Override
    public String toString() {
        return "State(" +
                "isClosed=" + isClosed +
                ", confidence=" + confidence +
                ')';
    }

    public void close() {
        if(!isClosed) {
            isClosed = true;
            confidence = 1 - confidence;
        }
    }

    public void open() {
        if(isClosed) {
            isClosed = false;
            confidence = 1 - confidence;
        }
    }

    public boolean isClosed() {
        return isClosed;
    }
}
