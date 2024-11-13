package sparta.clack.message;

import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract base class for Clack messages.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public abstract class Message implements  Serializable {
    /**
     * The type of message (e.g., TEXT, LISTUSERS, LOGOUT).
     * This defines the kind of message being sent.
     */
    private final MsgType msgType;

    /**
     * The timestamp when the message was created.
     * This represents the exact time the message was sent.
     */
    private final Instant timestamp;

    /**
     * The username of the user who sent the message.
     * This identifies the originator of the message.
     */
    private final String username;

    /**
     * Constructs a Message object with a given username.
     *
     * @param username name of user creating this message.
     * @param msgType the type of the message (e.g., TEXT, LISTUSERS, LOGOUT).
     */
    protected Message(String username, MsgType msgType) {
        this.msgType = msgType;
        this.timestamp = Instant.now();
        this.username = username;
    }

    /**
     * Get the message's msgType.
     *
     * @return the msgType for the object's concrete subclass.
     */
    public MsgType getMsgType() { return this.msgType; }

    /**
     * Gets the message's timestamp (as an Instant).
     *
     * @return the message's timestamp (as an Instant).
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the message's username.
     *
     * @return the message's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Return a string representation of this object.
     *
     * @return String showing fields and field contents
     */
    @Override
    public String toString()
    {
        return "Message{" +
                "msgType=" + msgType +
                ", timestamp=" + timestamp +
                ", username='" + username + '\'' +
                '}';
    }
}