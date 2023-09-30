package ru.netology;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    final private int port;

    final private int poolSize;

    public Server(int port, int poolSize) {
        this.port = port;
        this.poolSize = poolSize;
    }

    public void listen() {

        ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);

        System.out.println("server runnig on " + port);

        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new SocketThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
