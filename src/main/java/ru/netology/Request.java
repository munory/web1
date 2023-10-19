package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {

    final private HttpMethod httpMethod;

    final private String path;

    final private List<NameValuePair> queryParams = new ArrayList<>();

    public Request(HttpMethod httpMethod, String path, String query) {
        this.httpMethod = httpMethod;
        this.path = path;
        if (query != null) {
            this.queryParams.addAll(URLEncodedUtils.parse(query, StandardCharsets.UTF_8));
        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public List<NameValuePair> getQueryParams() {
        return queryParams;
    }

    public NameValuePair getQueryParam(String name) {
        for (NameValuePair param : queryParams) {
            if (param.getName().equals(name)) {
                return param;
            }
        }
        return null;
    }

}