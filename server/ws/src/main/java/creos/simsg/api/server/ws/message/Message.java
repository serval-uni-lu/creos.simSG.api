package creos.simsg.api.server.ws.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Message exchanged between the client(s) and the server.
 *
 * Messages (this class and those that extend it)  should be Java beans to allow the generation of JSON string
 */
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
