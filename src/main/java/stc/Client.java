package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
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
        out.write(str+'\0');
        out.flush();
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
