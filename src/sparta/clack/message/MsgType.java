package sparta.clack.message;

/**
 * An enumeration representing message types.
 */
public enum MsgType {
    /**
     * Represents a message containing a file.
     */
    FILE,

    /**
     * Represents a message requesting usage information.
     */
    HELP,

    /**
     * Represents a message that requests a list of users.
     */
    LISTUSERS,

    /**
     * Represents a message containing login credentials; i.e. username and password.
     */
    LOGIN,

    /**
     * Represents a message that signals the logout action, ending a session.
     */
    LOGOUT,

    /**
     * Represents a message containing an option and its associated value.
     */
    OPTION,

    /**
     * Represents a regular text message containing user input.
     */
    TEXT
}