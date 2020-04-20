package duc.sg.java.server.ws.message;

public class Error implements Message{
    private String reason;

    public Error(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
