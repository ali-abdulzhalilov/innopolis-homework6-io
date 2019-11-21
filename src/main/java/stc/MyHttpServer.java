package stc;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyHttpServer extends Server{
    private final static String CRLF = "\r\n";
    private final static String HEADER_200 = "HTTP/1.1 200 OK";
    private final static String HEADER_404 = "HTTP/1.1 404 Not Found";
    private final static String HEADER_BODY = "Content-Type: text/html" + CRLF +
            "Content-Length: ";

    public MyHttpServer(InetSocketAddress address) throws IOException {
        super(address);
    }

    public void run() {
        while (this.isEnabled) {
            try {
                Socket client = this.accept();

                String request = this.read(client);
                String output;
                if (isGet(request)) {
                    output = make200(listFilesAsPage());
                }
                else output = make404();

                this.send(client, output);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isGet(String request) {
        String firstLine = request.trim().split("\n")[0];
        return firstLine.contains("GET");
    }

    private String make200(String responseBody) {
        return HEADER_200 + CRLF +
                HEADER_BODY + responseBody.length() + CRLF +
                CRLF +
                responseBody;
    }

    private String listFilesAsPage() {
        StringBuilder response = new StringBuilder();
        response.append("<html>\n").append("\t<head></head>\n").append("\t<body>\n");
        response.append("\t\t<h3>Files around me:</h3>\n").append("\t\t<ul>\n");

        File dir = new File(".");
        String[] files = dir.list();
        for (String entry : files)
            response.append("\t\t\t<li>").append(entry).append("</li>\n");

        response.append("\t\t</ul>\n").append("\t</body>\n").append("</html>");

        return response.toString();
    }

    private String make404() {
        String responseBody = "Um, wrong method. Try GET.";
        return HEADER_404 + CRLF +
                HEADER_BODY + responseBody.length() + CRLF +
                CRLF +
                responseBody;
    }
}
