package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class Request {

    final private HttpMethod httpMethod;

    final private String path;

    final private String query;

    public Request(HttpMethod httpMethod, String path, String query) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.query = query;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public List<NameValuePair> getQueryParams() {
        if (query != null) {
            return URLEncodedUtils.parse(query, StandardCharsets.UTF_8);
        } else {
            return Collections.emptyList();
        }
    }

    public NameValuePair getQueryParam(String name) {
        for (NameValuePair param : getQueryParams()) {
            if (param.getName().equals(name)) {
                return param;
            }
        }
        return null;
    }

}
