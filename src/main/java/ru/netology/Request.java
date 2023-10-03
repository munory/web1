package ru.netology;

public class Request {

    final private HttpMethod httpMethod;

    final private String path;

    public Request(HttpMethod httpMethod, String content) {
        this.httpMethod = httpMethod;
        this.path = content;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

}
