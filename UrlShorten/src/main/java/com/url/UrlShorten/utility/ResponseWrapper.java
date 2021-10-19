package com.url.UrlShorten.utility;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private final T data;
    private final Boolean success;

    public ResponseWrapper( Boolean success,T data, int statusCode) {
        this.data = data;
        this.success = success;
        this.statusCode = statusCode;
    }

    private int statusCode;

    public T getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
