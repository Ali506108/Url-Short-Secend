package com.bmachine.url_shorter_service.domain.model;

import java.util.Objects;

public class ShortUrl {


    private String shortUrl;

    private String originalUrl;

    private long accessCount;

    public ShortUrl(String s, String originalUrl) {}


    public ShortUrl(String shortUrl, String originalUrl, long accessCount) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.accessCount = accessCount;
    }


    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrl shortUrl1 = (ShortUrl) o;
        return accessCount == shortUrl1.accessCount && Objects.equals(shortUrl, shortUrl1.shortUrl) && Objects.equals(originalUrl, shortUrl1.originalUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, originalUrl, accessCount);
    }
}
