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
            writeResponse("./src/index.html");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void writeResponse(String filePath) throws Throwable {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketOutputStream));
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: EtryflyServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + file.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response;
        socketOutputStream.write(result.getBytes());
        socketOutputStream.flush();

        while (bufferedReader.ready()) {
            String data = bufferedReader.readLine();
            bufferedWriter.write(data);
        }

        bufferedWriter.flush();
        bufferedReader.close();
    }
}
