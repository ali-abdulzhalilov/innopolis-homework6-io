package stc;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
    private static final String OUTPUT = "Hello, World!";
    private static final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text\r\n" +
            "Content-Length: ";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    public static void main( String[] args ) throws IOException {
        ServerSocket server = new ServerSocket(8001);
        Socket client = server.accept();
        DataInputStream in = new DataInputStream(client.getInputStream());
        byte[] buffer = new byte[1024]; // request should somewhere here
        in.read(buffer);
        System.out.println(new String(buffer));

        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        String output = OUTPUT_HEADERS + OUTPUT.length() + OUTPUT_END_OF_HEADERS + OUTPUT; // copypasted solution from different sources and stitched together
        out.writeUTF(output);
        out.flush();
        out.close();
        server.close();
    }
}
