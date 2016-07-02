package com.ht.fyforandroid.net.threadpoolnet;

public class Request {
    public enum RequestMethod {
        GET, POST, DELETE, PUT
    }

    private RequestMethod method;
    private String url;

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }
}
