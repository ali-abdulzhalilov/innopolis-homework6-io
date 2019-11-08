package stc;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppServer {
    private static final String OUTPUT = "Hello, World!";
    private static final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" +
    //private static final String OUTPUT_HEADERS = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Type: text/plain\r\n" +
            "Content-Length: ";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    private static Server server;

    public static void main( String[] args ) throws IOException, InterruptedException {
        server = new Server();
        server.bind(new InetSocketAddress(8001));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (server.isEnabled) {
                    System.out.println("----------");
                    try {
                        Socket client = server.accept();

                        ArrayList<String> input = server.read(client);
                        for (String s : input)
                            System.out.println(s);

                        String output = OUTPUT_HEADERS + OUTPUT.length() + OUTPUT_END_OF_HEADERS + OUTPUT; // copypasted solution from different sources and stitched together
                        server.send(client, output);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdown();
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t2.start();
    }
}
