package duc.sg.java.server.message;

import com.alibaba.fastjson.JSONObject;

public class LoadApproximationAnswer extends Message {
    private final JSONObject grid;

    LoadApproximationAnswer(JSONObject grid) {
        super(MessageType.LOAD_APPROX_ANSWER);
        this.grid = grid;
    }

    public JSONObject getGrid() {
        return grid;
    }
}
