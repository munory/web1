package ru.netology;

import ru.netology.handler.GetFileRequestHandler;
import ru.netology.handler.ServerRequestHandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class SocketThread implements Runnable {

    final private Socket socket;

    final private List<ServerRequestHandler> serverRequestHandlers;

    public SocketThread(Socket socket, List<ServerRequestHandler> serverRequestHandlers) {
        this.socket = socket;
        this.serverRequestHandlers = serverRequestHandlers;
    }

    @Override
    public void run() {
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream());
        ) {
            final var requestLine = in.readLine();

            if (requestLine != null) {
                final var parts = requestLine.split(" ");

                if (parts.length == 3) {
                    var method = HttpMethod.valueOf(parts[0]);
                    var path = parts[1];

                    serverRequestHandlers.stream().filter(h -> h.getHttpMethod() == method && h.getPath().equals(path)).findAny()
                            .ifPresentOrElse(serverRequestHandler -> {
                                serverRequestHandler.getRequestHandler().run(new Request(method, path), out);
                            }, () -> {
                                if (method == HttpMethod.GET) {
                                    new GetFileRequestHandler().run(new Request(method, path), out);
                                }
                            });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
