package duc.aintea.tests.sg;

public class Substation extends Entity {
    public Substation(String name) {
        super(name);
    }

    @Override
    public boolean isDeadEnd() {
        return false;
    }
}
