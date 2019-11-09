package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class App
{
    public static void main( String[] args ) throws IOException {
        Client client = new Client();
        //client.connect(new InetSocketAddress("www.google.com", 80));
        client.connect(new InetSocketAddress("localhost", 8001));

        client.sendGet("");
        String input = client.read();
        System.out.println(input);

        client.close();
    }


}
