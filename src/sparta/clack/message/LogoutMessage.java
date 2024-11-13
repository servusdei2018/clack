package sparta.clack.message;

/**
 * This class represents a command to the server, asking for termination of the connection.
 */
public class LogoutMessage extends Message {
    /**
     * Constructs a LogoutMessage with a given username.
     *
     * @param username the user sending this message.
     */
    public LogoutMessage(String username) {
        super(username, MsgType.LOGOUT);
    }

    /**
     * Returns a string representation of this LogoutMessage object.
     *
     * @return a string representation of this LogoutMessage object.
     */
    @Override
    public String toString() {
        return "LogoutMessage{"
                + super.toString()
                + "}";
    }
}
