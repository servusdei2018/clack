package sparta.clack.endpoint;

import sparta.clack.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Represents a client that connects to a server for sending and receiving messages.
 * <p>
 * To begin a conversation, a client connects to the server and waits for the server to send the first Message.
 * <p>
 * The conversation ends when the client sends a LogoutMessage.
 * The server replies with a last TextMessage, closes the connection, and waits for a new connection.
 */
public class Client {
    /**
     * The default username for the client when no username is specified.
     */
    public static final String DEFAULT_USERNAME = "client";

    private final String hostname;
    private final int port;
    private final String prompt;
    private final String username;

    /**
     * Creates a client for exchanging Message objects.
     *
     * @param hostname the hostname of the server.
     * @param port     the service's port on the server.
     * @param username username to include in Messages.
     * @throws IllegalArgumentException if port not in range [1-49151]
     */
    public Client(String hostname, int port, String username) {
        if (port < 1 || port > 49151) {
            throw new IllegalArgumentException(
                    "Port " + port + " not in range 1 - 49151.");
        }
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.prompt = hostname + ":" + port + "> ";
    }

    /**
     * Creates a client for exchanging Message objects, using the
     * default username (Client.DEFAULT_USERNAME).
     *
     * @param hostname the hostname of the server.
     * @param port     the service's port on the server.
     * @throws IllegalArgumentException if port not in range [1-49151]
     */
    public Client(String hostname, int port) {
        this(hostname, port, DEFAULT_USERNAME);
    }

    /**
     * Starts this client, connecting to the server and port that it was given when constructed.
     *
     * @throws UnknownHostException   if the hostname cannot be resolved.
     * @throws IOException            if an I/O error occurs while communicating with the server.
     * @throws ClassNotFoundException if a received message's class cannot be found.
     */
    public void start() throws UnknownHostException, IOException, ClassNotFoundException {
        System.out.println("Attempting connection to " + hostname + ":" + port);
        Scanner keyboard = new Scanner(System.in);

        try (
                Socket socket = new Socket(hostname, port);
                ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
        ) {
            String userInput;
            Message inMsg;
            Message outMsg;

            // Login
            boolean loggedIn = false;
            while (!loggedIn) {
                TextMessage response = (TextMessage) inObj.readObject();
                System.out.println(response.getText());
                if (response.getText().equals("Login successful.")) {
                    loggedIn = true;
                }
                System.out.print("Enter login details (username password): ");
                String loginDetails = keyboard.nextLine();
                String[] loginTokens = loginDetails.split("\\s+");
                if (loginTokens.length == 2) {
                    outMsg = new TextMessage(username, "LOGIN " + loginTokens[0] + " " + loginTokens[1]);
                } else {
                    outMsg = new TextMessage(username, "Invalid LOGIN format. Please use: LOGIN <username> <password>");
                }
                outObj.writeObject(outMsg);
                outObj.flush();
            }

            // Logged-in
            do {
                // Get server message and show it to user.
                inMsg = (Message) inObj.readObject();
                switch (inMsg.getMsgType()) {
                    case TEXT:
                        System.out.println(((TextMessage) inMsg).getText());
                        break;
                    default:
                        System.out.println("Unexpected message type: " + inMsg);
                        break;
                }

                // Get user input
                System.out.print(prompt);
                userInput = keyboard.nextLine();
                String[] tokens = userInput.trim().split("\\s+");

                outMsg = switch (tokens[0].toUpperCase()) {
                    case "HELP" -> new HelpMessage(username);
                    case "LOGOUT" -> new LogoutMessage(username);
                    case "LISTUSERS" -> new ListUsersMessage(username);
                    default -> new TextMessage(username, userInput);
                };

                // Send to server
                outObj.writeObject(outMsg);
                outObj.flush();
            } while (outMsg.getMsgType() != MsgType.LOGOUT);

            // Get server's closing reply and show it to user
            inMsg = (Message) inObj.readObject();
            System.out.println(((TextMessage) inMsg).getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connection to " + hostname + ":" + port + " closed, exiting.");
    }
}
