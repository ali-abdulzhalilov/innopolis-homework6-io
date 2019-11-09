package stc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppTest {
    private Client client;
    private Server server;

    @Before
    public void setUp() throws Exception {
        client = new Client();
        server = new Server();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
        server.close();
    }

    @Test
    public void canClientReachGoogle() throws IOException { // we're working under an assumption that google.com is not down right now
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        client.connect(address);

        String request = "GET / HTTP/1.1\r\n";
        request += "Accept: */*\r\n";
        request += "\r\n";
        client.send(request);

        String response = client.read();
        assertTrue(response.contains("OK"));
    }

    @Test
    public void clientSaysHelloToServer() throws InterruptedException {
        final String message = "Hello, World!";
        InetSocketAddress address = new InetSocketAddress("localhost", 8001);

        Thread serverThread = new Thread(() -> {
            try {
                server.bind(address);
                assertEquals(message, server.read(server.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread clientThread = new Thread(() -> {
            try {
                client.connect(address);
                client.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();
        clientThread.start();
    }

    @Test
    public void serverSaysHelloToClient() throws InterruptedException {
        final String message = "Hello, World!";
        InetSocketAddress address = new InetSocketAddress("localhost", 8001);

        Thread serverThread = new Thread(() -> {
            try {
               server.bind(address);
               server.send(server.accept(), message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread clientThread = new Thread(() -> {
                try {
                    client.connect(address);
                    assertEquals(message, client.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });

        serverThread.start();
        clientThread.start();
    }
}
