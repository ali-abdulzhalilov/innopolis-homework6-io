package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    public boolean isEnabled = true;

    public Server() throws IOException {
        serverSocket = new ServerSocket();
    }

    public Server(InetSocketAddress address) throws IOException {
        this();
        serverSocket.bind(address);
    }

    public void bind(InetSocketAddress address) throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
            serverSocket = new ServerSocket();
        }

        serverSocket.bind(address);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public void send(Socket client, String str) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        out.write(str);
        out.flush();
    }

    public ArrayList<String> read(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ArrayList<String> input = new ArrayList<>();

        String line = in.readLine();
        while (!line.isEmpty()) {
            input.add(line);
            line = in.readLine();
        }

        return input;
    }

    public void shutdown() {
        this.isEnabled = false;
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
