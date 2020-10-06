package duc.sg.java.server.message;

import java.util.Optional;

public class MessageFactory {
    private MessageFactory(){}

    public static Optional<String> buildActionListMessage(String... name) {
        if(name == null || name.length == 0) {
            return Optional.empty();
        }

        return Optional.of(new ActionListMsg(name).toJson());

    }

}
