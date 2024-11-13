package sparta.clack.message;

/**
 * Represents a message containing login credentials, including the username and password.
 */
public class LoginMessage extends Message {
    /**
     * The password associated with the login attempt.
     */
    private final String password;

    /**
     * Constructs a LoginMessage object containing the specified username and password.
     *
     * @param username the name of the user attempting to log in.
     * @param password the password associated with the login attempt.
     */
    public LoginMessage(String username, String password) {
        super(username, MsgType.LOGIN);
        this.password = password;
    }

    /**
     * Returns the password associated with this login message.
     *
     * @return the password for the login attempt.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns a string representation of this LoginMessage object.
     *
     * @return a string representation of this LoginMessage object.
     */
    @Override
    public String toString() {
        return "LoginMessage{"
                + super.toString()
                + ",password='" + password + '\'' +
                '}';
    }
}
