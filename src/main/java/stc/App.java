package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class App
{
    public static void main( String[] args ) throws IOException {
        Client client = new Client();
        client.connect(new InetSocketAddress("localhost", 8001));

        client.send("GET / HTTP/1.1\r\n\r\n");
        String input = client.read();
        System.out.println(input);

        client.close();
    }


}
