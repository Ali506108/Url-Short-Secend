package com.bmachine.url_shorter_service.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class UrlEntity {


    @Id
    private String shortCode;

    private String originalUrl;


    public UrlEntity(String shortCode, String originalUrl) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
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

}
