package duc.sg.java.model;

public class Meter {
    private String name;
    private double consumption;

    public Meter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
