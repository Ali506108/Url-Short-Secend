package com.bmachine.url_shorter_service.application.dto;

public class OrigUrlResponseDTO {


    private String originalUrl;

    public OrigUrlResponseDTO(String originalUrl) {
        this.originalUrl = originalUrl;
    }


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
