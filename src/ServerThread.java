import java.io.*;
import java.net.Socket;

/**
 * Created by etryfly on 19.06.17.
 */
public class ServerThread extends Thread implements Runnable {
    private InputStream socketInputStream;
    private OutputStream socketOutputStream;
    private Socket socket;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        socketInputStream = socket.getInputStream();
        socketOutputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socketInputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
            while (true) {
                writeResponse("test");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: EtryflyServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        socketOutputStream.write(result.getBytes());
        socketOutputStream.flush();
    }
}
