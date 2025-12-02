import java.io.PrintWriter;
import java.util.HashSet;

/**
 * A multithreaded chat room server executive.
 * This class just starts the ChatServer in its own thread.
 */
public class ChatServerExec implements ChatServerExecInterface {

    public ChatServerExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    /**
     * The port that the server listens on.
     */
    private static int CHAT_ROOM_PORT;

    // this is the one required by the interface
    @Override
    public void startServer(int port) {
        CHAT_ROOM_PORT = port;
        startServer();
    }

    // this is the one the GUI calls with no parameter
    public void startServer() {
        ChatServer server = new ChatServer(CHAT_ROOM_PORT);

        // run the server in its own thread so the GUI doesn't freeze
        Thread t = new Thread(server);
        t.start();
    }
}
