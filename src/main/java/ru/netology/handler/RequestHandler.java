package ru.netology.handler;

import ru.netology.Request;

import java.io.BufferedOutputStream;

@FunctionalInterface
public interface RequestHandler {

    void run(Request request, BufferedOutputStream out);

}
