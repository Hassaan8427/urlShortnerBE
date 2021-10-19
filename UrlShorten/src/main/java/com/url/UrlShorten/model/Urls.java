package com.url.UrlShorten.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Urls {
    public Urls() {
    }

    public Urls(String shorturl, String url, String expirydate) {
        this.shorturl = shorturl;
        this.url = url;
        this.expirydate = expirydate;
    }

    @Override
    public String toString() {
        return "Urls{" +
                "id=" + id +
                ", shorturl='" + shorturl + '\'' +
                ", url='" + url + '\'' +
                ", expirydate='" + expirydate + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(unique = true,nullable = false)
    private String shorturl;
    @NotNull
    @Column(nullable = false)
    private String url;
    @NotNull
    private String expirydate;


    public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shorturl) {
        this.shorturl = shorturl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }
}
