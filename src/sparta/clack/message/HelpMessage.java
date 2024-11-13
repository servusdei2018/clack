package sparta.clack.message;

/**
 * This class represents a message requesting usage information.
 */
public class HelpMessage extends Message {

    /**
     * Constructs a HelpMessage object.
     *
     * @param username name of the user requesting help.
     */
    public HelpMessage(String username) {
        super(username, MsgType.HELP);
    }

    /**
     * Returns a string representation of this HelpMessage object.
     *
     * @return a string representation of this HelpMessage object.
     */
    @Override
    public String toString() {
        return "HelpMessage{"
                + super.toString()
                + "}";
    }
}
