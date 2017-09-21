package ChatApp;

import java.io.Serializable;

public class ChatMessage implements Serializable
{
    protected static final long serialVersionUID = 1112122200L;

    // Different types of messages sent by Client
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary message
    // LOGOUT to disconnect from the Server
    static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
    private int type;
    private String message;

    // Constructor
    public ChatMessage(int type, String message)
    {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
