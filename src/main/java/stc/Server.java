package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    protected boolean isEnabled = true;

    Server() throws IOException {
        serverSocket = new ServerSocket();
    }

    public Server(InetSocketAddress address) throws IOException {
        this();
        serverSocket.bind(address);
    }

    void bind(InetSocketAddress address) throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
            serverSocket = new ServerSocket();
        }

        serverSocket.bind(address);
    }

    Socket accept() throws IOException {
        return serverSocket.accept();
    }

    void send(Socket client, String str) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        out.write(str+'\0');
        out.flush();
    }

    String read(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder s = new StringBuilder();

        int ch = 'x';
        while((ch = in.read()) != '\0' && ch != -1) {
            s.append((char)ch);
        }

        return s.toString();
    }

    void shutdown() throws IOException {
        this.isEnabled = false;
        serverSocket.close();
    }

    void close() throws IOException {
        serverSocket.close();
    }
}
