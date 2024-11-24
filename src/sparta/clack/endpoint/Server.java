package sparta.clack.endpoint;

import sparta.clack.message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Represents a server that handles client connections for sending and receiving messages.
 * <p>
 * To begin a conversation, a client connects to the server and waits for the server to send the first Message.
 * <p>
 * The conversation ends when the client sends a LogoutMessage.
 * The server replies with a last TextMessage, closes the connection, and waits for a new connection.
 */
public class Server {
    /**
     * The default name assigned to the server if no specific name is provided.
     */
    public static final String DEFAULT_SERVERNAME = "server";

    private static final String GREETING =
            "[Server listening. 'Logout' (case insensitive) closes connection.]";
    private static final String GOOD_BYE =
            "[Closing connection, good-bye.]";

    // Object variables.
    private final int port;
    private final String serverName;
    private final boolean SHOW_TRAFFIC = true; // FOR DEBUGGING

    /**
     * Creates a server for exchanging Message objects.
     *
     * @param port       the port to listen on.
     * @param serverName the name to use when constructing Message objects.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port, String serverName)
            throws IllegalArgumentException {
        if (port < 1024 || port > 49151) {
            throw new IllegalArgumentException(
                    "Port " + port + " not in range 1024-49151.");
        }
        this.port = port;
        this.serverName = serverName;
    }

    /**
     * Creates a server for exchanging Message objects, using the
     * default servername (Server.DEFAULT_SERVERNAME).
     *
     * @param port the port to listen on.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port) {
        this(port, DEFAULT_SERVERNAME);
    }

    /**
     * Starts this server, listening on the port it was constructed with.
     *
     * @throws IOException            if ServerSocket creation, connection acceptance, wrapping, or IO fails.
     * @throws ClassNotFoundException if a message's class cannot be found during deserialization.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException, ClassNotFoundException {
        File tempDirectory = new File("tempfiles");
        if (!tempDirectory.exists()) {
            boolean success = tempDirectory.mkdirs();  // Creates the directory and any necessary parent directories
            if (success) {
                System.out.println("Directory 'tempfiles' created successfully.");
            } else {
                throw new IOException("Failed to create 'tempfiles' directory.");
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server starting on port " + port + ".");
            System.out.println("Ctrl + C to exit.");
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        ObjectInputStream inObj = new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
                ) {
                    Message inMsg;
                    Message outMsg;

                    // Connection made. Greet client.
                    outMsg = new TextMessage(serverName, GREETING);
                    outObj.writeObject(outMsg);
                    outObj.flush();
                    if (SHOW_TRAFFIC) {
                        System.out.println("=> " + outMsg);
                    }

                    // Wait for login
                    String currentUser = null;
                    boolean loggedIn = false;
                    while (!loggedIn) {
                        inMsg = (Message) inObj.readObject();
                        if (SHOW_TRAFFIC) {
                            System.out.println("<= " + inMsg);
                        }
                        if (inMsg.getMsgType() == MsgType.LOGIN) {
                            String[] loginParts = ((TextMessage) inMsg).getText().split(" ");
                            if (loginParts.length == 2) {
                                String username = loginParts[0];
                                String password = loginParts[1];
                                if (password.contentEquals(new StringBuilder(username).reverse())) {
                                    currentUser = username;
                                    loggedIn = true;
                                    outMsg = new TextMessage(serverName, "Login successful.");
                                } else {
                                    outMsg = new TextMessage(serverName, "Invalid username or password.");
                                }
                            } else {
                                outMsg = new TextMessage(serverName, "Invalid LOGIN command format.");
                            }
                        } else {
                            outMsg = new TextMessage(serverName, GREETING);
                        }
                        outObj.writeObject(outMsg);
                        outObj.flush();
                        if (SHOW_TRAFFIC) {
                            System.out.println("=> " + outMsg);
                        }
                    }

                    String cipher_key = null;
                    boolean cipher_enable = false;
                    String cipher_name = null;

                    // Converse with client.
                    do {
                        inMsg = (Message) inObj.readObject();
                        if (SHOW_TRAFFIC) {
                            System.out.println("<= " + inMsg);
                        }

                        // Process the received message
                        outMsg = switch (inMsg.getMsgType()) {
                            case MsgType.FILE -> {
                                String fileSavePath = "tempfiles/" + ((FileMessage) inMsg).getFileName();
                                File fileToSave = new File(fileSavePath);
                                try {
                                    Files.write(fileToSave.toPath(), ((FileMessage) inMsg).getFileContents().getBytes());
                                } catch (IOException e) {
                                    yield new TextMessage(serverName, "Error saving file: " + e.getMessage());
                                }
                                yield new TextMessage(serverName, "File saved successfully as " + fileToSave.getName());
                            }
                            case MsgType.HELP -> {
                                yield new TextMessage(serverName,
                                        """
                                                Welcome to the server. Here are the commands you can use:
                                                1. 'LOGIN <username> <password>' - Log in with your username.
                                                2. 'TEXT <message>' - Send a text message to the server.
                                                3. 'LISTUSERS' - Request a list of currently logged-in users.
                                                4. 'LOGOUT' - Log out and end the conversation.
                                                
                                                For help, send 'HELP'.
                                                All commands are case insensitive.""");
                            }
                            case MsgType.LISTUSERS -> {
                                yield new TextMessage(serverName, "Users:\n• " + currentUser + "\n");
                            }
                            case MsgType.LOGOUT -> {
                                yield new TextMessage(serverName, GOOD_BYE);
                            }
                            case MsgType.OPTION -> {
                                if (inMsg instanceof OptionMessage optionMessage) {
                                    switch (optionMessage.getOption()) {
                                        case CIPHER_KEY -> cipher_key = optionMessage.getValue();
                                        case CIPHER_ENABLE ->
                                                cipher_enable = Boolean.parseBoolean(optionMessage.getValue());
                                        case CIPHER_NAME -> cipher_name = optionMessage.getValue();
                                    }
                                }
                                yield inMsg;
                            }
                            case MsgType.TEXT -> {
                                yield inMsg;
                            }
                            default -> null;
                        };
                        outObj.writeObject(outMsg);
                        outObj.flush();
                        if (SHOW_TRAFFIC) {
                            System.out.println("=> " + outMsg);
                        }
                    } while (inMsg.getMsgType() != MsgType.LOGOUT);

                    System.out.println("=== Terminating connection. ===");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } // Server socket closed
    }
}