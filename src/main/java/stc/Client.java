package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private final String CRLF = "\r\n";
    private final String HEADER_GET = "GET / HTTP/1.1";
    private final String HEADER_POST = "POST / HTTP/1.1";
    private final String HEADER_BODY = "Accept: */*";

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    Client() {
        socket = new Socket();
    }

    public Client(InetSocketAddress address) throws IOException {
        this();
        this.connect(address);
    }

    void connect(InetSocketAddress address) throws IOException {
        if (socket != null) {
            socket.close();
            socket = new Socket();
        }

        socket.connect(address);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    void send(String str) throws IOException {
        System.out.println(str+'\0');
        out.write(str+'\0');
        out.flush();
    }

    void sendRequest(String header, String request) throws IOException {
        String output = header + CRLF +
                HEADER_BODY + CRLF +
                CRLF +
                request;

        send(output);
    }

    void sendGet(String request) throws IOException {
        sendRequest(HEADER_GET, request);
    }

    void sendPost(String request) throws IOException {
        sendRequest(HEADER_POST, request);
    }

    String read() throws IOException {
        StringBuilder s = new StringBuilder();

        int ch = 'x';
        while((ch = in.read()) != '\0' && ch != -1) {
            s.append((char)ch);
        }

        return s.toString();
    }

    void close() throws IOException {
        socket.close();
    }
}
