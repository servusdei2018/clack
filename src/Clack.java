import sparta.clack.endpoint.Client;
import sparta.clack.endpoint.Server;
import sparta.clack.ui.ClientUI;

/**
 * Main program. Based on command-line arguments (see USAGE string), creates and starts either a client or a server.
 * To use, create a single server on a given host and port.
 * <p>
 * Clients (possibly multiple) can then be created, each in their own terminal/command window. The client can accept
 * "localhost" as a server name. If a client cannot connect to a server on a remote host, check firewall settings on
 * the host.
 * <p>
 * The usage message assumes this program has been built as a jar file. IDEs vary in how they do this; check the IDE
 * documentation.
 */
public class Clack {
    private final static String USAGE =
            "Usage: java Clack client <server name> <server port>\n"
                    + "       java Clack server <server port>";

    /**
     * Create new instance of Clack.
     */
    public Clack() {
    }

    /**
     * The entry point of the Clack application. The program expects the following command-line arguments:
     * <p>
     * The application expects specific arguments to either start a client or server:
     * <ul>
     *   <li>
     *     <code>client &lt;server name&gt; &lt;server port&gt;</code> - Starts a client that connects to the specified server.
     *   </li>
     *   <li>
     *     <code>server &lt;server port&gt;</code> - Starts a server that listens on the specified port.
     *   </li>
     * </ul>
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) { //FIXME(Nate): change to args.length < 2 after ui mode removed
            System.err.println(USAGE);
            return;
        }

        String mode = args[0].toLowerCase();
        try {
            if ("server".equals(mode) && args.length == 2) {
                startServer(args[1]);
            } else if ("client".equals(mode) && args.length == 3) {
                startClient(args[1], args[2]);
            } else if ("ui".equals(mode) && args.length == 1) { //FIXME(Nate): remove this after ui transition
                ClientUI clientUI = new ClientUI();
                clientUI.run();
            } else {
                System.err.println(USAGE);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println(USAGE);
        }
    }

    /**
     * Starts the server by accepting the given port string, parsing it into an integer, and initializing
     * a {@link Server} instance to listen for client connections.
     * <p>
     * If the provided port string cannot be parsed as an integer or if any other error occurs while
     * initializing or starting the server, an error message is printed to the console, and usage instructions
     * are displayed.
     * </p>
     *
     * @param portStr the port number as a string, which will be parsed into an integer to start the server.
     */
    private static void startServer(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            Server server = new Server(port, Server.DEFAULT_SERVERNAME);
            server.start();
        } catch (NumberFormatException e) {
            System.err.println(portStr + " cannot be parsed as an int.");
            System.err.println(USAGE);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(USAGE);
        }
    }

    /**
     * Starts the client by accepting the given server name and port string, parsing the port string into an integer,
     * and initializing a {@link Client} instance to connect to the specified server.
     * <p>
     * If the provided port string cannot be parsed as an integer or if any other error occurs while
     * initializing or starting the client, an error message is printed to the console, and usage instructions
     * are displayed.
     * </p>
     *
     * @param serverName the hostname or IP address of the server to which the client should connect.
     * @param portStr    the port number as a string, which will be parsed into an integer to connect to the server.
     */
    private static void startClient(String serverName, String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            Client client =
                    new Client(serverName, port, Client.DEFAULT_USERNAME);
            client.start();
        } catch (NumberFormatException e) {
            System.err.println(portStr + " cannot be parsed as an int.");
            System.err.println(USAGE);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
