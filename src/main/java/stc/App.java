package stc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class App
{
    public static void main( String[] args ) throws IOException {
//        File dir = new File(".");
//        String[] files = dir.list();
//        for (String s : files)
//            System.out.println(s);

        Socket client = new Socket(InetAddress.getLocalHost(), 8001);
        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        String output = "GET / HTTP/1.1\r\n\r\n";
        out.write(output.getBytes());
        out.flush();

        byte[] buffer = new byte[1024];
        in.read(buffer);
        System.out.println(new String(buffer));

        client.close();
    }


}
