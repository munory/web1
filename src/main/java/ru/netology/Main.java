package ru.netology;

import org.apache.http.NameValuePair;
import ru.netology.handler.GetFileRequestHandler;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(9999, 64);

        server.addRequestHandler(HttpMethod.GET, "/queryParser", (request, out) -> {
            try {
                StringBuilder paramsOut = new StringBuilder();
                for (NameValuePair param : request.getQueryParams()) {
                    paramsOut.append(param.getName()).append("=").append(param.getValue()).append("\r\n");
                }

                final var content = paramsOut.toString().getBytes();
                out.write((
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: " + content.length + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.write(content);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        server.addRequestHandler(HttpMethod.GET, "/classic.html",
                new GetFileRequestHandler(s -> s.replace("{time}", LocalDateTime.now().toString())));

        server.addRequestHandler(HttpMethod.GET, "/messages", (request, out) -> {
            try {
                final var content = "message ok".getBytes();
                out.write((
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: " + content.length + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.write(content);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        server.listen();
    }
}


