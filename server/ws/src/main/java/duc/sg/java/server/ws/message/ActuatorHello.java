package duc.sg.java.server.ws.message;

import duc.sg.java.server.ws.ContractLoader;
import duc.sg.java.server.ws.JsonLoaderException;

public class ActuatorHello implements Message {
    String message;

    public ActuatorHello() throws JsonLoaderException {
        this.message = ContractLoader.load();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
