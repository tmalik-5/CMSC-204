public class ChatClientExec implements ChatClientExecInterface {

    private int CHAT_ROOM_PORT = 0;

    private static double clientX = 30.0;
    private static double clientY = 10.0;

    public ChatClientExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    @Override
    public void startClient() throws Exception {
        // moving the windows so they don't overlap
        setClientY(getClientY() + 50.0);
        setClientX(getClientX() + 50.0);

        ChatClient client = new ChatClient(CHAT_ROOM_PORT);

        // run the client in its own thread
        Thread t = new Thread(client);
        t.start();
    }

    public static double getClientX() { return clientX; }
    public static void setClientX(double x) { clientX = x; }
    public static double getClientY() { return clientY; }
    public static void setClientY(double y) { clientY = y; }
}
