package sparta.clack.message;

/**
 * This class represents messages containing text entered from the keyboard.
 */
public class TextMessage extends Message {    /**
    /*
     * The text content of the message.
     */
    private final String text;

    /**
     * Constructs a TextMessage object.
     *
     * @param username name of user sending the message.
     * @param text     text of the message itself.
     */
    public TextMessage(String username, String text) {
        super(username, MsgType.TEXT);
        this.text = text;
    }

    /**
     * Returns the text of this message.
     *
     * @return the text contained in this {@code TextMessage}.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns a string representation of this TextMessage object.
     *
     * @return a string representation of this TextMessage object.
     */
    @Override
    public String toString() {
        return "TextMessage{"
                + super.toString()
                + ",text='" + text + '\'' +
                '}';
    }
}
