package duc.sg.java.server.message;

import com.alibaba.fastjson.JSONObject;
import duc.sg.java.exporter.JsonExporter;
import duc.sg.java.loadapproximator.certain.CertainApproximator;
import duc.sg.java.model.SmartGrid;

public class LoadApproxMsg extends Message implements RequestMessage {
    private SmartGrid grid;

    LoadApproxMsg(SmartGrid grid) {
        super(MessageType.LOAD_APPROX);
        this.grid = grid;
    }

    @Override
    public Message process() {
        grid.getSubstations().forEach(CertainApproximator.INSTANCE::approximateAndSave);
        JSONObject res = new JsonExporter<Object>().export(grid);

        return new LoadApproximationAnswer(res);
    }
}
