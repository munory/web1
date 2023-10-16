package ru.netology.handler;

import ru.netology.HttpMethod;

public class ServerRequestHandler {

    final private HttpMethod httpMethod;

    final private String path;

    final private RequestHandler requestHandler;

    public ServerRequestHandler(HttpMethod httpMethod, String path, RequestHandler requestHandler) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.requestHandler = requestHandler;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

}
