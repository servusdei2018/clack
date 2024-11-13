package sparta.clack.message;

/**
 * An enumeration representing message types.
 */
public enum MsgType {
    /**
     * Represents a message that requests a list of users.
     */
    LISTUSERS,

    /**
     * Represents a message that signals the logout action, ending a session.
     */
    LOGOUT,

    /**
     * Represents a regular text message containing user input.
     */
    TEXT
}