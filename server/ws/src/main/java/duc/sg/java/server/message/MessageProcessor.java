package duc.sg.java.server.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import duc.sg.java.importer.ImportationException;
import duc.sg.java.importer.json.JsonGridImporter;
import duc.sg.java.model.SmartGrid;

import java.util.Optional;

public class MessageProcessor {
    private MessageProcessor(){}

    public static Optional<Message> process(String msgStr) {
        JSONObject msgObj = JSON.parseObject(msgStr);
        final var msgType = msgObj.getString("type");

        if(msgType.equals(MessageType.LOAD_APPROX.getName())) {
            try {
                Optional<SmartGrid> grid = JsonGridImporter.INSTANCE.from(msgObj.getString("grid"));
                return grid.map(LoadApproxMsg::new).map(LoadApproxMsg::process);
            } catch (ImportationException e) {
                return Optional.empty();
            }
        } else if(msgType.equals(MessageType.ULOAD_APPROX.getName())) {
            try {

                final var type = ULoadApproxMsg.ULoadType.valueOf(
                        msgObj.getString("approximationType").toUpperCase()
                );

                Optional<SmartGrid> optGrid = JsonGridImporter.INSTANCE.from(msgObj.getString("grid"));
                return optGrid.map((SmartGrid grid) -> new ULoadApproxMsg(grid, type))
                        .map(ULoadApproxMsg::process);
            } catch (ImportationException e) {
                return Optional.empty();
            }
        }

        return Optional.empty();

    }
}
