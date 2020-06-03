package duc.sg.java.validator.rules;

public final class Rules {
    private Rules() {}


    public static IRule[] getAllRules() {
        return new IRule[] {
                new CableRule(),
                new DeadEndEntities(),
                new SubstationRule(),
                new AllCirclesRule(),
                new MandatoryPowerFLow()
        };
    }

    public static IRule getCircleRules() {
        return new CircleRule();
    }

}
