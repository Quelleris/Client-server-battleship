package majafrydrych.Common.Message;

import majafrydrych.Common.Logic.RoomData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    public String type;
    public String stringMessage;

    public Message(String type) {
        this.type = type;
    }

    public Message(String type, String content) {
        this.type = type;
        this.stringMessage = content;
    }
}
