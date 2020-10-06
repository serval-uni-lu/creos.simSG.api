package duc.sg.java.server.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public abstract class Message {
    private MessageType type;

    Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public final String toJson() {
        return JSON.toJSONString(this, SerializerFeature.WriteEnumUsingToString);
    }
}
