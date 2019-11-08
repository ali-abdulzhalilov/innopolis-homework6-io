package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Signature;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public Client() {
        socket = new Socket();
    }

    public Client(InetSocketAddress address) throws IOException {
        this();
        this.connect(address);
    }

    public void connect(InetSocketAddress address) throws IOException {
        if (socket != null) {
            socket.close();
            socket = new Socket();
        }

        socket.connect(address);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String str) throws IOException {
        out.write(str);
        out.flush();
    }

    public ArrayList<String> read() throws IOException {
        ArrayList<String> input = new ArrayList<>();

        String line = in.readLine();
        while (!line.isEmpty()) {
            input.add(line);
            line = in.readLine();
        }

        return input;
    }

    public void close() throws IOException {
        socket.close();
    }
}
