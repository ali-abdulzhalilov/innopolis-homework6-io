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
    public static void main( String[] args ) throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress("localhost", 8002);
        MyHttpServer server = new MyHttpServer(address);
        server.run();
    }
}
