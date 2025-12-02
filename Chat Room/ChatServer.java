import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import javax.swing.JOptionPane;

// making this runnable so we can run it in a thread
public class ChatServer implements Runnable {

    private String name;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    ChatServer(int port) {
        CHAT_ROOM_PORT = port;
    }

    /**
     * The port that the server listens on.
     */
    private static int CHAT_ROOM_PORT;

    /**
     * The set of all names of clients in the chat room.
     */
    private static HashSet<String> names = new HashSet<String>();

    /**
     * The set of all the print writers for all the clients.
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    @Override
    public void run() {
        ServerSocket listener = null;

        try {
            // starting the actual server socket
            listener = new ServerSocket(CHAT_ROOM_PORT);

            // just to see that the server actually started
            System.out.println("The chat server is running.");

            while (true) {

                // wait for a client to join
                clientSocket = listener.accept();

                // setting up input/output for this client
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // get a valid screen name
                name = null;
                while (name == null) {
                    out.println("SUBMITNAME");
                    name = in.readLine();

                    if (name.equals("") || name.equals("null")) {
                        name = null;
                    } else if (names.contains(name)) {
                        out.println("WRONGNAME");
                        Thread.sleep(100);
                        name = null;
                    } else {
                        names.add(name);
                    }
                }

                out.println("NAMEACCEPTED");
                writers.add(out);

                // make a thread to handle this client's messages
                ServerThreadForClient clientThread =
                        new ServerThreadForClient(in, out, name, clientSocket);

                Thread t = new Thread(clientThread);
                t.start();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    // inner class that handles one client
    private class ServerThreadForClient implements Runnable {

        BufferedReader in;
        PrintWriter out;
        String name;
        Socket socket;

        ServerThreadForClient(BufferedReader in, PrintWriter out, String name, Socket socket) {
            this.in = in;
            this.out = out;
            this.name = name;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String msg = in.readLine();
                    if (msg == null) {
                        return;
                    }

                    // send the message to everybody
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + msg);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // clean up this client
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();  // close THIS client's socket only
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
