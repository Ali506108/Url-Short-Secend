package com.bmachine.url_shorter_service.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UrlEntity {


    @Id
    private String shortCode;
    private String originalUrl;
    private long accessCount;

    public UrlEntity(String shortCode, String originalUrl, long accessCount) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.accessCount = accessCount;
    }
    public UrlEntity(){}


    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
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
}
