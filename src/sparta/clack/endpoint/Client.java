package sparta.clack.endpoint;

import sparta.clack.message.Message;
import sparta.clack.message.FileMessage;
import sparta.clack.message.TextMessage;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a client that connects to a server for sending and receiving messages.
 * The client can be configured with a username, server name, and server port.
 * It provides functionality to start a REPL (Read-Eval-Print Loop) for user interaction,
 * read user input, and print messages.
 * <p>
 * The client uses a {@code Scanner} object to read user input from the console and
 * creates {@code Message} objects based on that input.
 * </p>
 */
public class Client {
    /**
     * Default port for connecting to server. This should be
     * a port li sted as "unassigned" in
     * <a href="https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.txt">IANA</a>.
     */
    public static final int DEFAULT_SERVER_PORT = 0; //TODO: choose an unassigned port (NOT 0!!)

    /**
     * The default server name to connect to if one is not explicitly provided.
     * The default value is "localhost", which indicates that the client will connect
     * to the local machine.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";

    private final String username;
    private final String serverName;
    private final int serverPort;
    private final String saveDirectory = System.getProperty("user.dir");
    private Message messageToSend;
    private Message messageReceived;

    /**
     * A {@code Scanner} object used to read input from the standard input (stdin).
     * This field facilitates reading user input, such as commands or messages, from the console.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a {@code Client} with the specified username, server name, and server port.
     *
     * @param username   the username of the client
     * @param serverName the name of the server to connect to
     * @param serverPort the port of the server to connect to
     */
    public Client(String username, String serverName, int serverPort) {
        this.username = username;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    /**
     * Constructs a {@code Client} with the specified username and the default server name and port.
     *
     * @param username   the username of the client
     * @param serverName the name of the server to connect to
     */
    public Client(String username, String serverName) {
        this(username, serverName, DEFAULT_SERVER_PORT);
    }

    /**
     * Constructs a {@code Client} with the specified username and server port, and the default server name.
     *
     * @param username   the username of the client
     * @param serverPort the port of the server to connect to
     */
    public Client(String username, int serverPort) {
        this(username, DEFAULT_SERVER_NAME, serverPort);
    }

    /**
     * Constructs a {@code Client} with the specified username, and default server name and port.
     *
     * @param username the username of the client
     */
    public Client(String username) {
        this(username, DEFAULT_SERVER_NAME, DEFAULT_SERVER_PORT);
    }

    /**
     * The client's REPL loop. Prompt for input, build
     * message from it, send message and receive/process
     * the reply, print info for user; repeat until
     * user enters "LOGOUT".
     */
    public void start() {
        while (true) {
            Message message = readUserInput();
            if (message.toString().equals("LOGOUT")) {
                break;
            }

            this.messageReceived = messageToSend;
            this.printMessage();

            if (this.messageReceived instanceof FileMessage) {
                System.out.print("Enter filename to save the file: ");
                String filename = this.scanner.nextLine();
                try {
                    ((FileMessage) this.messageReceived).writeFile();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Parse the line of user input and create the appropriate
     * message.
     *
     * @return an object of the appropriate Message subclass.
     */
    public Message readUserInput() {
        return new TextMessage(this.username, this.scanner.nextLine());
    }

    /**
     * Print the current messageReceived object to System.out.
     * What is printed is the result of calling toString()
     * on the messageReceived object.
     */
    public void printMessage() {
        System.out.println(this.messageReceived.toString());
    }

    /**
     * Returns the username of this client.
     *
     * @return the username of the client
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the server name that this client is connected to.
     *
     * @return the server name of the client
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Returns a string representation of this {@code Client} object.
     * The string representation includes the class name, default server name and port,
     * and the values of the username, server name, server port, the message to send, and
     * the message received.
     * <p>
     * The format of the returned string is:
     * <pre>
     * {class=Client|DEFAULT_SERVER_NAME=<defaultServerName>|DEFAULT_SERVER_PORT=<defaultServerPort>|username=<username>|serverName=<serverName>|serverPort=<serverPort>|messageToSend={<messageToSend>}|messageReceived={<messageReceived>}}
     * </pre>
     *
     * @return a string representation of this {@code Client} object
     */
    public String toString() {
        return "{class=Client|"
                + "|DEFAULT_SERVER_NAME=" + DEFAULT_SERVER_NAME
                + "|DEFAULT_SERVER_PORT=" + DEFAULT_SERVER_PORT
                + "|username=" + this.username
                + "|serverName=" + this.serverName
                + "|serverPort=" + this.serverPort
                + "|messageToSend={" + this.messageToSend.toString() + "}"
                + "|messageReceived={" + this.messageReceived.toString() + "}"
                + "}";
    }
}
