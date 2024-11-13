package sparta.clack.message;

/**
 * This class represents a command to the server, asking for
 * a list of all active users of the server.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public class ListUsersMessage extends Message {
    /**
     * Constructs a ListUsersMessage with a given username.
     *
     * @param username the user sending this message.
     */
    public ListUsersMessage(String username) {
        super(username, MsgType.LISTUSERS);
    }

    /**
     * Returns a string representation of this ListUsersMessage object.
     *
     * @return a string representation of this ListUsersMessage object.
     */
    @Override
    public String toString() {
        return "ListUsersMessage{"
                + super.toString()
                + "}";
    }
}
