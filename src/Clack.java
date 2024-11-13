import sparta.clack.endpoint.Client;
import sparta.clack.endpoint.Server;

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
     * The entry point of the Clack application. The program expects the following command-line arguments:
     * <ul>
     *     <li>"client <server name> <server port>" - Starts a client that connects to the specified server.</li>
     *     <li>"server <server port>" - Starts a server that listens on the specified port.</li>
     * </ul>
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println(USAGE);
            return;
        }

        String mode = args[0].toLowerCase();
        try {
            if ("server".equals(mode) && args.length == 2) {
                startServer(args[1]);
            } else if ("client".equals(mode) && args.length == 3) {
                startClient(args[1], args[2]);
            } else {
                System.err.println(USAGE);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println(USAGE);
        }
    }

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
            System.err.println(USAGE);
        }
    }
}
