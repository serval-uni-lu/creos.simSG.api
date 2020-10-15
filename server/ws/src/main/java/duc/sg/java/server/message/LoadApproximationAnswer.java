package duc.sg.java.server.message;

/**
 * This message contains information to answer a load approximation request: fuses and cables loads
 */
public class LoadApproximationAnswer extends Message {
    private final Load[] fuseLoads;
    private final Load[] cableLoads;

    LoadApproximationAnswer(Load[] fuseLoads, Load[] cableLoads) {
        super(MessageType.LOAD_APPROX_ANSWER);
        this.fuseLoads = fuseLoads;
        this.cableLoads = cableLoads;
    }

    public Load[] getFuseLoads() {
        return fuseLoads;
    }

    public Load[] getCableLoads() {
        return cableLoads;
    }

    public static class Load {
        final String id;
        final double value;

        public Load(String id, double value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public double getValue() {
            return value;
        }
    }

}
