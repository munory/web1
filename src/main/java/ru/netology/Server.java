package ru.netology;

import ru.netology.handler.RequestHandler;
import ru.netology.handler.ServerRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    final private int port;

    final private int poolSize;

    final private List<ServerRequestHandler> serverRequestHandlers = new ArrayList<>();

    public Server(int port, int poolSize) {
        this.port = port;
        this.poolSize = poolSize;
    }

    public void addRequestHandler(HttpMethod httpMethod, String path, RequestHandler requestHandler) {
        serverRequestHandlers.add(new ServerRequestHandler(httpMethod, path, requestHandler));
    }

    public void listen() {

        ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);

        System.out.println("server runnig on " + port);

        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new SocketThread(socket, serverRequestHandlers));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
