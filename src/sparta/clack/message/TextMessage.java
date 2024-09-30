package sparta.clack.message;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents messages containing text
 * entered from the keyboard.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public class TextMessage extends Message {
    private final String text;

    /**
     * Constructs a TextMessage object.
     *
     * @param username name of user sending the message.
     * @param text     text of the message itself.
     */
    public TextMessage(String username, String text) {
        super(username, MSGTYPE_TEXT);
        this.text = text;
    }

    /**
     * Gets the message's data.
     *
     * @return the message text, in a one-element String array.
     */
    @Override
    public String[] getData() {
        return new String[]{ text };
    }

    /**
     * Equality comparison. Returns true iff the other object is of
     * the same class and all fields (including those inherited from
     * superclasses) are equal.
     *
     * @param o the object to test for equality.
     * @return whether o is of the same class as this, and all fields
     * are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextMessage that = (TextMessage) o;
        return Objects.equals(this.getTimestamp(), that.getTimestamp())
                && Objects.equals(this.getUsername(), that.getUsername())
                && Arrays.equals(this.getData(), that.getData());
    }

    /**
     * Return this object's hash. In Message objects, this is simply the
     * hash of the string returned by this.toString().
     *
     * @return hash of this object.
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Returns a string representation of this TextMessage object.
     * <p>
     * The string representation includes the class name, the output
     * of the superclass's {@code toString} method, and the message data.
     * The format of the string is:
     * <pre>
     * {class=TextMessage|super.toString()|data=message-text}
     * </pre>
     * where {@code super.toString()} is the string representation
     * from the superclass and {@code message-text} is the actual text
     * of the message.
     * </p>
     *
     * @return a string representation of this TextMessage object.
     */
    @Override
    public String toString() {
        return "{class=TextMessage|"
                + super.toString()
                + "|data=" + this.getData()[0]
                + '}';
    }
}
