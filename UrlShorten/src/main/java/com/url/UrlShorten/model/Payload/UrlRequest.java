package com.url.UrlShorten.model.Payload;

import com.sun.istack.NotNull;

public class UrlRequest {
    @NotNull
    private String url;
    @NotNull
    private String expiryDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
