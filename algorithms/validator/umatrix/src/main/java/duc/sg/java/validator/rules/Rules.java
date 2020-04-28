package duc.sg.java.validator.rules;

public final class Rules {
    private Rules() {}


    public static IRule[] getRules() {
        return new IRule[] {
                new CableRule(),
                new DeadEndEntities(),
                new SubstationRule(),
                new CircleRule(),
                new MandatoryPowerFLow()
        };
    }

}
