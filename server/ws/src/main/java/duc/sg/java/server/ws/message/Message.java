package duc.sg.java.server.ws.message;

import com.alibaba.fastjson.JSON;

public interface Message {


    default String toJson() {
        return JSON.toJSONString(this);
    }

}
