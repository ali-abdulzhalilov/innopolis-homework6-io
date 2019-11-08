package stc;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class App
{
    public static void main( String[] args ) throws IOException {
        Client client = new Client();
        client.connect(new InetSocketAddress("www.google.com", 80));
        client.send("GET / HTTP/1.1\r\n\r\n");
        ArrayList<String> input = client.read();
        for (String s : input)
            System.out.println(s);

        System.out.println("-----");

        client.connect(new InetSocketAddress("www.google.ru", 80));
        client.send("GET / HTTP/1.1\r\n\r\n");
        input = client.read();
        for (String s : input)
            System.out.println(s);


        client.close();
    }


}
