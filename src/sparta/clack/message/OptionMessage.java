package sparta.clack.message;

/**
 * This class represents a message that contains an option and its corresponding value.
 */
public class OptionMessage extends Message {
    private final OptionEnum option;
    private final String value;

    /**
     * Constructs an OptionMessage object.
     *
     * @param username the name of the user creating this message.
     * @param option the option type (e.g., CIPHER_KEY, CIPHER_NAME, CIPHER_ENABLE).
     * @param value the value associated with the option.
     */
    public OptionMessage(String username, OptionEnum option, String value) {
        super(username, MsgType.OPTION);
        this.option = option;
        this.value = value;
    }

    /**
     * Returns the option associated with this message.
     *
     * @return the {@link OptionEnum} representing the option.
     */
    public OptionEnum getOption() {
        return this.option;
    }

    /**
     * Returns the value associated with this option.
     *
     * @return the value as a {@link String}.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a string representation of this OptionMessage object.
     *
     * @return a string representation of this OptionMessage object.
     */
    @Override
    public String toString() {
        return "OptionMessage{"
                + super.toString()
                + ", option=" + option
                + ", value='" + value + '\'' +
                '}';
    }
}
